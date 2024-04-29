package com.example.proyecto1_ipc2.service;

import com.example.proyecto1_ipc2.conexion.Conexion;
import com.example.proyecto1_ipc2.dataBase.LoginDB;
import com.example.proyecto1_ipc2.modelo.usuario.Administrador;
import com.example.proyecto1_ipc2.modelo.usuario.Operador;
import com.example.proyecto1_ipc2.modelo.usuario.Recepcionista;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoginService {
    private final LoginDB loginData;

    public LoginService(){
        loginData = new LoginDB();
    }
    public Administrador leerAdmins(String username, Conexion conexion, String password, int area) throws SQLException {

        return  validar(conexion,username,area,password).orElse(null);
    }
    public Optional<Administrador> validar(Conexion conexion, String username, int rol, String password) throws SQLException {
        List<Administrador> listaAdministrador = new ArrayList<>();
        loginData.traerUsuarios("usuarios", conexion);
        while (conexion.getResultSet().next()){
            var admin = new Administrador(conexion.getResultSet().getInt(1),conexion.getResultSet().getString(2),conexion.getResultSet().getInt(3),
                    conexion.getResultSet().getString(4),conexion.getResultSet().getBoolean(5),conexion.getResultSet().getString(6), conexion.getResultSet().getDouble(7));
            listaAdministrador.add(admin);
        }


        return  listaAdministrador.stream().filter(s -> s.getNombreUsuario().equals(username) && s.getPassword().equals(password)&& s.getRol()==rol).findFirst();
    }

    public Operador leerOperadores(String username, Conexion conexion, String password, int area) throws SQLException {

        return  validarOperador(conexion,username,area,password).orElse(null);
    }
    public Optional<Operador> validarOperador(Conexion conexion, String username, int area, String password) throws SQLException {
        List<Operador> listaOperadores = new ArrayList<>();
        loginData.traerUsuarios("usuarios", conexion);
        while (conexion.getResultSet().next()){
            var operador = new Operador(conexion.getResultSet().getInt(1),conexion.getResultSet().getString(2),conexion.getResultSet().getInt(3),
                    conexion.getResultSet().getString(4),conexion.getResultSet().getBoolean(5),conexion.getResultSet().getString(6), conexion.getResultSet().getDouble(7));
            listaOperadores.add(operador);
        }


        return  listaOperadores.stream().filter(s -> s.getNombreUsuario().equals(username) && s.getPassword().equals(password)&&s.getRol()==area).findFirst();
    }

    public Recepcionista leerRecepcionista(String username, Conexion conexion, String password, int area) throws SQLException {

        return  validarRecepcionista(conexion,username,area,password).orElse(null);
    }
    public Optional<Recepcionista> validarRecepcionista(Conexion conexion, String username, int area, String password) throws SQLException {
        List<Recepcionista> listaRecepcionistas = new ArrayList<>();
        loginData.traerUsuarios("usuarios", conexion);
        while (conexion.getResultSet().next()){
            var recepcionista = new Recepcionista(conexion.getResultSet().getInt(1),conexion.getResultSet().getString(2),conexion.getResultSet().getInt(3),
                    conexion.getResultSet().getString(4),conexion.getResultSet().getBoolean(5),conexion.getResultSet().getString(6), conexion.getResultSet().getDouble(7));
            listaRecepcionistas.add(recepcionista);
        }


        return  listaRecepcionistas.stream().filter(s -> s.getNombreUsuario().equals(username) && s.getPassword().equals(password)&&s.getRol()==area).findFirst();
    }







}
