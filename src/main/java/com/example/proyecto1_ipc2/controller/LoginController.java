package com.example.proyecto1_ipc2.controller;

import com.example.proyecto1_ipc2.conexion.Conexion;
import com.example.proyecto1_ipc2.modelo.Login;
import com.example.proyecto1_ipc2.modelo.usuario.Administrador;
import com.example.proyecto1_ipc2.service.LoginService;
import com.example.proyecto1_ipc2.utils.JsonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "servletLogin", urlPatterns = "/servletLogin/*")

public class LoginController  extends HttpServlet {

    private final JsonUtils<Administrador> jsonAdmin;
    private final JsonUtils<Administrador> jsonOp;
    private final JsonUtils<Administrador> jsonRecep;

    private final JsonUtils<Login> jsonLogin;
    private final LoginService  loginService;
    public static Conexion conexion = new Conexion();

    public LoginController(){
        jsonAdmin = new JsonUtils<>();
        jsonOp = new JsonUtils<>();
        jsonRecep = new JsonUtils<>();
        //personaJsonUtils = new JsonUtils<>();
        jsonLogin = new JsonUtils<>();
        loginService = new LoginService();
    }

    private void verificarUsuario(HttpServletResponse response,String userName, String password, int area) throws IOException, SQLException {
        switch (area){
            case 1:
                if (loginService.leerAdmins(userName, conexion, password, area) == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                jsonAdmin.enviarComoJSON(response, loginService.leerAdmins(userName, conexion, password,area));
                break;
            case 2: //OPERADOR
                if (loginService.leerOperadores(userName, conexion, password, area) == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                jsonOp.enviarComoJSON(response, loginService.leerOperadores(userName, conexion, password,area));
            break;

            case 3: //RECEPCIONISTA
                if (loginService.leerRecepcionista(userName, conexion, password, area) == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                jsonRecep.enviarComoJSON(response, loginService.leerRecepcionista(userName, conexion, password,area));
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        conexion.conectar();
        String pathInfo = request.getPathInfo();
        if(pathInfo==null || pathInfo.equals("/")){
            var usuario = jsonLogin.leerDeJSON(request, Login.class);
            try {
                verificarUsuario(response,usuario.getUserName(),usuario.getPassword(),usuario.getRol());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }



    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        conexion.cerrarConexion();
        jsonLogin.enviarComoJSON(resp,"Conexion cerrada");
    }
}




