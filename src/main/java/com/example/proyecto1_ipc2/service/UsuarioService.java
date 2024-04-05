package com.example.proyecto1_ipc2.service;

import com.example.proyecto1_ipc2.conexion.Conexion;
import com.example.proyecto1_ipc2.controller.LoginController;
import com.example.proyecto1_ipc2.dataBase.AdminDB;
import com.example.proyecto1_ipc2.modelo.Punto_Control;
import com.example.proyecto1_ipc2.modelo.Usuario;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioService {
    private AdminDB usuarioDB;

    public UsuarioService(){
        usuarioDB = new AdminDB();
    }


    public void guardarUsuarios(Usuario user, Conexion conexion) {
        usuarioDB.guardarUsuarios(user, conexion);
    }
    public void actualizarUsuarios(Usuario user, Conexion conexion, int id){
        usuarioDB.actualizarUsuarios(user, conexion, id);
    }
    public List<Usuario> traerTodosUsuarios(int id) throws SQLException {
        List<Usuario> list=new ArrayList<>();
        usuarioDB.mostrarUsuarios(LoginController.conexion,id);
        while(LoginController.conexion.resultSet.next()){
            Usuario nuevo= new Usuario(LoginController.conexion.getResultSet().getInt(1),LoginController.conexion.getResultSet().getString(2),LoginController.conexion.getResultSet().getInt(3),
                    LoginController.conexion.getResultSet().getString(4),LoginController.conexion.getResultSet().getBoolean(5),LoginController.conexion.getResultSet().getString(6), LoginController.conexion.getResultSet().getDouble(7));
            list.add(nuevo);
        }
        return list;
    }

    public void eliminarUsuario(Conexion conexion, int id){
        usuarioDB.eliminarusuarios(id, conexion);
    }


}
