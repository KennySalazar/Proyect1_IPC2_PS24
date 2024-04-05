package com.example.proyecto1_ipc2.controller;

import com.example.proyecto1_ipc2.conexion.Conexion;
import com.example.proyecto1_ipc2.modelo.Login;
import com.example.proyecto1_ipc2.modelo.usuario.Administrador;
import com.example.proyecto1_ipc2.modelo.usuario.Operador;
import com.example.proyecto1_ipc2.modelo.usuario.Recepcionista;
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
    private final JsonUtils<Recepcionista> jsonRecepcionista;
    private final JsonUtils<Operador> jsonOperador;
    private final JsonUtils<Login> jsonLogin;
    private final LoginService  loginService;
    public static Conexion conexion = new Conexion();

    public LoginController(){
        jsonAdmin = new JsonUtils<>();
        jsonRecepcionista = new JsonUtils<>();
        jsonOperador = new JsonUtils<>();
        jsonLogin = new JsonUtils<>();
        loginService = new LoginService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        conexion.conectar();
        String pathInfo = request.getPathInfo();
        if(pathInfo==null || pathInfo.equals("/")){
            var usuario = jsonLogin.leerDeJSON(request, Login.class);
            System.out.println(usuario.getUserName());
            System.out.println(usuario.getRol());
            System.out.println(usuario.getPassword());
            try {
                verificarUsuario(response,usuario.getUserName(), usuario.getPassword(), Integer.parseInt(usuario.getRol()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void verificarUsuario(HttpServletResponse response,String userName, String password, int rol) throws IOException, SQLException {
        switch (rol){
            case 1:
                if (loginService.leerAdmins(userName, conexion, password) == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                jsonAdmin.enviarComoJSON(response, loginService.leerAdmins(userName, conexion, password));
                break;

        }

    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        conexion.cerrarConexion();
        jsonLogin.enviarComoJSON(resp,"Conexion cerrada");
    }
}




