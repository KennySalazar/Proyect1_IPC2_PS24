package com.example.proyecto1_ipc2.controller;

import com.example.proyecto1_ipc2.conexion.Conexion;
import com.example.proyecto1_ipc2.modelo.reportes.PaquetesRecorridoRuta;
import com.example.proyecto1_ipc2.modelo.reportes.Top3Rutas;
import com.example.proyecto1_ipc2.service.ReportesService;
import com.example.proyecto1_ipc2.service.RutaService;
import com.example.proyecto1_ipc2.utils.JsonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "ServletReportes", urlPatterns = {"/ServletReportes/*"})
public class ReportesController extends HttpServlet {

    private ReportesService reportesService = new ReportesService();
    private JsonUtils jsonUtils = new JsonUtils<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] splits = pathInfo.split("/");
        //servlet../1 --> el reporte 1   para top 3 rutas más populares, 2 cantidad de paquetes que ya salieron de ruta
        String reporte = splits[1];


        if(reporte.equalsIgnoreCase("1")){


            try {
                ArrayList<Top3Rutas> lista = reportesService.traerReporteTop3Rutas(LoginController.conexion);
                if(lista.size()==0){
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                System.out.println(lista.size());
                jsonUtils.enviarComoJSON(response,lista);
                response.setStatus(HttpServletResponse.SC_OK);
                return;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        String reporte2 = splits[1];

        if(reporte.equalsIgnoreCase("2")){
            boolean estado = Boolean.parseBoolean(splits[2]);
            try {
                ArrayList<PaquetesRecorridoRuta> lista = reportesService.traerPaquetesEnRuta(LoginController.conexion,estado);
                if(lista.size()==0){
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                System.out.println(lista.size());
                jsonUtils.enviarComoJSON(response,lista);
                response.setStatus(HttpServletResponse.SC_OK);
                return;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

        String reporte3 = splits[1];

        if(reporte.equalsIgnoreCase("3")){
            boolean estado = Boolean.parseBoolean(splits[2]);
            try {
                ArrayList<PaquetesRecorridoRuta> lista = reportesService.traerPaquetesFueraDeRuta(LoginController.conexion, estado);
                if(lista.size()==0){
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                System.out.println(lista.size());
                jsonUtils.enviarComoJSON(response,lista);
                response.setStatus(HttpServletResponse.SC_OK);
                return;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }
}




