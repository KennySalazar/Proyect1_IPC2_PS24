package com.example.proyecto1_ipc2.controller;

import com.example.proyecto1_ipc2.modelo.Paquete;
import com.example.proyecto1_ipc2.modelo.Punto_Control;
import com.example.proyecto1_ipc2.modelo.Usuario;
import com.example.proyecto1_ipc2.service.PaqueteService;
import com.example.proyecto1_ipc2.service.Punto_Control_Service;
import com.example.proyecto1_ipc2.service.RutaService;
import com.example.proyecto1_ipc2.service.UsuarioService;
import com.example.proyecto1_ipc2.utils.JsonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet(name = "paquete", urlPatterns = {"/paquete/*"})
public class PaqueteController extends HttpServlet {
    private PaqueteService paqueteService = new PaqueteService();
    private JsonUtils jsonUtils = new JsonUtils<>();
    private RutaService rutaService = new RutaService();
    private UsuarioService usuarioService = new UsuarioService();
    private Punto_Control_Service puntoControlService = new Punto_Control_Service();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {

            List<Paquete> paquete = null;
            try {
                paquete = paqueteService.traerTodosPaquetes(-2);//trae todos los campos
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            resp.setStatus(HttpServletResponse.SC_OK);
            jsonUtils.enviarComoJSON(resp, paquete);
        }

        String [] splits = pathInfo.split("/");
        if(splits.length == 3){
            int idPuntoControlLocalizacion = Integer.parseInt(splits[2]);
            try {
                List<Paquete> listaPaqueteXLocalizacion=paqueteService.traerLocalizacionPaquetesXPuntoControl(idPuntoControlLocalizacion);
                if(listaPaqueteXLocalizacion.size() ==0){
                    //paquete/idPuntoControl/Id
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                resp.setStatus(HttpServletResponse.SC_OK);
                jsonUtils.enviarComoJSON(resp,listaPaqueteXLocalizacion);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if(splits.length == 4){
            String destinoPaquete = splits[3];
            try {
                List<Paquete> listaPaqueteDestino=paqueteService.traerDestinoPaquete(destinoPaquete);
                if(listaPaqueteDestino.size() ==0){
                    //paquete/destino/a/Huehue
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                resp.setStatus(HttpServletResponse.SC_OK);
                jsonUtils.enviarComoJSON(resp,listaPaqueteDestino);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


        int codigoPaquete = Integer.parseInt(splits[1]);
        try {
            List<Paquete> listaPaquete=paqueteService.traerTodosPaquetes(codigoPaquete);
            if(listaPaquete.size()== 0){
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

            resp.setStatus(HttpServletResponse.SC_OK);
            jsonUtils.enviarComoJSON(resp,listaPaquete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        if ( pathInfo == null || pathInfo.equals("/")){
            var paquete = jsonUtils.leerDeJSON(request, Paquete.class);
            Usuario user = null;
            try {
                user = usuarioService.traerTodosUsuarios(((Paquete)paquete).getIdUsuarioCreador()).get(0);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ((Paquete)paquete).setDetalle(((Paquete)paquete).getDetalle()+"\n Pago en bodega:\n cuota = "+
                    ((Paquete)paquete).getCuota() + "\n Precio por libras= " + ((Paquete)paquete).getPeso() +
                    "* " + user.getPrecioXLibra() + "= " + ((Paquete)paquete).getPeso()* user.getPrecioXLibra()+
                    "\n total = " + (((Paquete)paquete).getPeso()* user.getPrecioXLibra()+((Paquete)paquete).getCuota()) + " ") ;
            guardarPaquete((Paquete) paquete);
            System.out.println(((Paquete)paquete).getDetalle());
            try { //despues de guardar el paquete, verfica si la cola de la ruta no esta llena
                paqueteService.verificarEnvioPaquetesARuta(LoginController.conexion);
                //cuando el paquete entra al punto de control, verifica si la cola no esta llena
                paqueteService.verificarSiElPaqueteYaLlegoAlPuntoControl(LoginController.conexion);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            response.setStatus(HttpServletResponse.SC_CREATED);
            jsonUtils.enviarComoJSON(response, paquete);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public void guardarPaquete(Paquete paquete) {
        paqueteService.guardarPaquete(paquete, LoginController.conexion);



    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if ( pathInfo == null || pathInfo.equals("/")){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] splits = pathInfo.split("/");

        String paqueteID = splits[1];

        var paquete = jsonUtils.leerDeJSON(req, Paquete.class);

        if(((Paquete)paquete).getEstado()==3){
            horaSalidaPaqueteDelPuntoControl(((Paquete)paquete));
            paqueteService.actualizarPaquete((Paquete) paquete, LoginController.conexion, Integer.parseInt(paqueteID));
            Punto_Control puntoControl = null;
            try {
                puntoControl = puntoControlService.traerTodosPuntosControl(((Paquete)paquete).getIdPuntoControlPaquete()).get(0);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            int cantidadHoras = 0;
            try {
                cantidadHoras = paqueteService.traerDiferenciaHoras(Integer.parseInt(paqueteID), LoginController.conexion);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ((Paquete) paquete).setDetalle(((Paquete) paquete).getDetalle() + "\n" +
                        "Tarifa Por horas: \n" + "horas:" + cantidadHoras + "\n Tarifa: "
                + puntoControl.getTarifa() + "\n Total = " + (cantidadHoras*puntoControl.getTarifa()));


        }
        paqueteService.actualizarPaquete((Paquete) paquete, LoginController.conexion, Integer.parseInt(paqueteID));
        try { //Verifica si el paquete ya esta en ruta, lo cambia al estado 1
            paqueteService.verificarEnvioPaquetesARuta(LoginController.conexion);
            //Verifica cuando el paquete ya entro al punto de control, lo cambia al estado 2
            paqueteService.verificarSiElPaqueteYaLlegoAlPuntoControl(LoginController.conexion);
           // paqueteService.verificarSiElPaqueteYaFueEntregado(LoginController.conexion);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.setStatus(HttpServletResponse.SC_OK);
        jsonUtils.enviarComoJSON(resp, paquete);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if ( pathInfo == null || pathInfo.equals("/")){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] splits = pathInfo.split("/");
        if (splits.length > 2){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String paqueteID = splits[1];

        paqueteService.eliminarPaquete(LoginController.conexion,Integer.parseInt(paqueteID));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    //MOSTRAR DETALLE DE PAGO CUANDO EL ESTADO DEL PAAQUETE CAMBIE A ESTADO 3
    // MODIFICAR FEHCA DE ENTRADA CUANDO CAMBIE AL ESTADO 2
    // MODIFICAR FECHA DE SALIDA CUANDO CAMBIE AL ESTADO 3
    public void horaSalidaPaqueteDelPuntoControl(Paquete paquete){
        // Obtener la fecha y hora actual
        LocalDateTime now = LocalDateTime.now();

        // Definir un formato personalizado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Formatear la fecha y hora actual
        String formattedDateTime = now.format(formatter);

        // Imprimir la fecha y hora actual formateada
        System.out.println("Fecha y hora actual: " + formattedDateTime);
        paquete.setHoraSalida(formattedDateTime);

    }
}


