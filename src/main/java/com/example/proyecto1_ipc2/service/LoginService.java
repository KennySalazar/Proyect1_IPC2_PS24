package com.example.proyecto1_ipc2.service;

import com.example.proyecto1_ipc2.conexion.Conexion;
import com.example.proyecto1_ipc2.dataBase.LoginDB;
import com.example.proyecto1_ipc2.modelo.usuario.Administrador;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoginService {
    private final LoginDB loginData;

    public LoginService(){
        loginData = new LoginDB();
    }
    public Administrador leerAdmins(String username, Conexion conexion, String password) throws SQLException {

        return  validar(conexion,username,password).orElse(null);
    }
    public Optional<Administrador> validar(Conexion conexion, String username, String password) throws SQLException {
        List<Administrador> listaAdministrador = new ArrayList<>();
        loginData.traerUsuarios("usuarios", conexion);
        while (conexion.getResultSet().next()){
            var admin = new Administrador(conexion.getResultSet().getInt(1),conexion.getResultSet().getString(2),conexion.getResultSet().getInt(3),
                    conexion.getResultSet().getString(4),conexion.getResultSet().getBoolean(5),conexion.getResultSet().getString(6), conexion.getResultSet().getDouble(7));
            listaAdministrador.add(admin);
        }
        //return estudiantes.stream().filter(s -> s.getId() == id).findFirst();

        return  listaAdministrador.stream().filter(s -> s.getNombreUsuario().equals(username) && s.getPassword().equals(password)).findFirst();
    }


}
