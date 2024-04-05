package com.example.proyecto1_ipc2.controller;

import com.example.proyecto1_ipc2.modelo.Punto_Control;
import com.example.proyecto1_ipc2.modelo.Usuario;
import com.example.proyecto1_ipc2.service.PaqueteService;
import com.example.proyecto1_ipc2.service.Punto_Control_Service;
import com.example.proyecto1_ipc2.utils.JsonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "puntoControl", urlPatterns = {"/puntoControl/*"})
public class Punto_Control_Controller extends HttpServlet {
    private Punto_Control_Service puntoControlService = new Punto_Control_Service();
    private JsonUtils jsonUtils = new JsonUtils<>();
    private PaqueteService paqueteService = new PaqueteService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {

            List<Punto_Control> pcontrol = null;
            try {
                pcontrol = puntoControlService.traerTodosPuntosControl(-2);//trae todos los campos
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            resp.setStatus(HttpServletResponse.SC_OK);
            jsonUtils.enviarComoJSON(resp, pcontrol);
        }

        String [] splits = pathInfo.split("/");
        if(splits.length > 2){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        int idPControl = Integer.parseInt(splits[1]);
        try {
            List<Punto_Control> listaPC=puntoControlService.traerTodosPuntosControl(idPControl);
            if(listaPC.size()==0){
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

            resp.setStatus(HttpServletResponse.SC_OK);
            jsonUtils.enviarComoJSON(resp,listaPC);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if ( pathInfo == null || pathInfo.equals("/")){
            var puntoControl = jsonUtils.leerDeJSON(req, Punto_Control.class);
           // puntoControlService.create((Punto_Control) puntoControl);
            guardarPC((Punto_Control) puntoControl);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            jsonUtils.enviarComoJSON(resp, puntoControl);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if ( pathInfo == null || pathInfo.equals("/")){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] splits = pathInfo.split("/");


        String puntoControlID = splits[1];

        var puntoControl = jsonUtils.leerDeJSON(req, Punto_Control.class);

        puntoControlService.actualizarPuntoControl((Punto_Control) puntoControl,LoginController.conexion,Integer.parseInt(puntoControlID));
        try { //CADA VEZ QUE SE MODIFICA LA COLA DE UN PUNTO DE CONTROL, SE AÑADEN LOS PAQUETES QUE ESTABAN EN ESPERA
            paqueteService.verificarSiElPaqueteYaLlegoAlPuntoControl(LoginController.conexion);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.setStatus(HttpServletResponse.SC_OK);
        jsonUtils.enviarComoJSON(resp, puntoControl);
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

        String PuntoControlID = splits[1];

        /*if (puntoControlService.read(Integer.parseInt(PuntoControlID)) == null){
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }*/

        //Punto_Control punto_control =  puntoControlService.read(Integer.parseInt(PuntoControlID));
        //puntoControlService.delete(Integer.parseInt(PuntoControlID));
        puntoControlService.eliminarPuntoControl(LoginController.conexion,Integer.parseInt(PuntoControlID));
        resp.setStatus(HttpServletResponse.SC_OK);
        //jsonUtils.enviarComoJSON(resp, punto_control);
    }

    public void guardarPC(Punto_Control puntoControl) {
        puntoControlService.guardarPuntosControl(puntoControl, LoginController.conexion);

    }
}
