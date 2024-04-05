package com.example.proyecto1_ipc2.controller;

import com.example.proyecto1_ipc2.conexion.Conexion;
import com.example.proyecto1_ipc2.modelo.Punto_Control;
import com.example.proyecto1_ipc2.modelo.Usuario;
import com.example.proyecto1_ipc2.modelo.usuario.Administrador;
import com.example.proyecto1_ipc2.modelo.usuario.Operador;
import com.example.proyecto1_ipc2.service.UsuarioService;
import com.example.proyecto1_ipc2.utils.JsonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "usuarios", urlPatterns = {"/usuarios/*"})
public class UsuarioController extends HttpServlet {
    public static Conexion conexion = new Conexion();
    private UsuarioService usuarioService = new UsuarioService();
    private JsonUtils jsonUtils = new JsonUtils<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        if ( pathInfo == null || pathInfo.equals("/")){
            var operador = jsonUtils.leerDeJSON(request, Usuario.class);

            guardarOperador((Usuario) operador);

            response.setStatus(HttpServletResponse.SC_CREATED);
            jsonUtils.enviarComoJSON(response, operador);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public void guardarOperador(Usuario operador) {

        usuarioService.guardarUsuarios(operador, LoginController.conexion);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {

            List<Usuario> user = null;
            try {
                user = usuarioService.traerTodosUsuarios(-2);//trae todos los campos
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            resp.setStatus(HttpServletResponse.SC_OK);
            jsonUtils.enviarComoJSON(resp, user);
        }

        String [] splits = pathInfo.split("/");
        if(splits.length > 2){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        int idUser = Integer.parseInt(splits[1]);
        try {
            List<Usuario> listaUsers=usuarioService.traerTodosUsuarios(idUser);
            if(listaUsers.size()==0){
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

            resp.setStatus(HttpServletResponse.SC_OK);
            jsonUtils.enviarComoJSON(resp,listaUsers);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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


        String userID = splits[1];


        var user = jsonUtils.leerDeJSON(req, Usuario.class);

        usuarioService.actualizarUsuarios((Usuario) user,LoginController.conexion,Integer.parseInt(userID));
        resp.setStatus(HttpServletResponse.SC_OK);
        jsonUtils.enviarComoJSON(resp, user);
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

        String userID = splits[1];

        usuarioService.eliminarUsuario(LoginController.conexion,Integer.parseInt(userID));
        resp.setStatus(HttpServletResponse.SC_OK);

    }
}



