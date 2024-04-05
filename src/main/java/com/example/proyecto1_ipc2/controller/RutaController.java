package com.example.proyecto1_ipc2.controller;

import com.example.proyecto1_ipc2.modelo.Ruta;
import com.example.proyecto1_ipc2.service.PaqueteService;
import com.example.proyecto1_ipc2.service.RutaService;
import com.example.proyecto1_ipc2.utils.JsonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ruta", urlPatterns = {"/ruta/*"})
public class RutaController extends HttpServlet {
    private RutaService rutaService = new RutaService();
    private PaqueteService paqueteService = new PaqueteService();
    private JsonUtils jsonUtils = new JsonUtils<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {

            List<Ruta> ruta = null;
            try {
                ruta = rutaService.traerTodosRuta(-2);//trae todos los campos
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            resp.setStatus(HttpServletResponse.SC_OK);
            jsonUtils.enviarComoJSON(resp, ruta);
        }

        String [] splits = pathInfo.split("/");
        if(splits.length > 2){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        int idRuta = Integer.parseInt(splits[1]);
        try {
            List<Ruta> listaRuta=rutaService.traerTodosRuta(idRuta);
            if(listaRuta.size()== 0){
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

            resp.setStatus(HttpServletResponse.SC_OK);
            jsonUtils.enviarComoJSON(resp,listaRuta);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        if ( pathInfo == null || pathInfo.equals("/")){
            var ruta = jsonUtils.leerDeJSON(request, Ruta.class);
            guardarRuta((Ruta) ruta);

            response.setStatus(HttpServletResponse.SC_CREATED);
            jsonUtils.enviarComoJSON(response, ruta);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public void guardarRuta(Ruta ruta) {
        rutaService.guardarRuta(ruta, LoginController.conexion);


    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if ( pathInfo == null || pathInfo.equals("/")){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] splits = pathInfo.split("/");


        String rutaID = splits[1];


        var ruta = jsonUtils.leerDeJSON(req, Ruta.class);


        rutaService.actualizarRuta((Ruta) ruta,LoginController.conexion,Integer.parseInt(rutaID));
        try { // cada vez que se actualice el limite de cola, añade los paquetes que estaban en espera
            paqueteService.verificarEnvioPaquetesARuta(LoginController.conexion);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.setStatus(HttpServletResponse.SC_OK);
        jsonUtils.enviarComoJSON(resp, ruta);
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

        String rutaID = splits[1];

        rutaService.eliminarRuta(LoginController.conexion,Integer.parseInt(rutaID));
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
