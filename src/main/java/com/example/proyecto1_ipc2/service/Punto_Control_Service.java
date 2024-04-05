package com.example.proyecto1_ipc2.service;

import com.example.proyecto1_ipc2.conexion.Conexion;
import com.example.proyecto1_ipc2.controller.LoginController;
import com.example.proyecto1_ipc2.dataBase.AdminDB;
import com.example.proyecto1_ipc2.dataBase.Punto_ControlDB;
import com.example.proyecto1_ipc2.modelo.Punto_Control;
import com.example.proyecto1_ipc2.modelo.Usuario;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Punto_Control_Service {
    private AdminDB usuarioDB;
    private final Punto_ControlDB puntoControlDB;


    public Punto_Control_Service(){
        usuarioDB = new AdminDB();
        puntoControlDB = new Punto_ControlDB();

    }


    public void guardarPuntosControl(Punto_Control puntoControl, Conexion conexion) {
        usuarioDB.guardarPuntoControl(puntoControl, conexion);
    }
    public void actualizarPuntoControl(Punto_Control puntoControl, Conexion conexion, int id){
        usuarioDB.actualizarPuntoControl(conexion, puntoControl, id);
    }
    public List<Punto_Control> traerTodosPuntosControl(int id) throws SQLException {
        List<Punto_Control> list=new ArrayList<>();
        usuarioDB.mostrarPuntosControl(LoginController.conexion,id);
        while(LoginController.conexion.resultSet.next()){
            Punto_Control nuevo= new Punto_Control(LoginController.conexion.resultSet.getInt(1),LoginController.conexion.resultSet.getInt(2),
                    LoginController.conexion.resultSet.getInt(3), LoginController.conexion.resultSet.getInt(4));
            list.add(nuevo);
        }
        return list;
    }

    public void eliminarPuntoControl(Conexion conexion, int id){
        usuarioDB.eliminarPuntoControl(id,conexion);
    }


    public int devolverCantidadPaquetesEnPuntoControl(int idPuntoControl, Conexion conexion) throws SQLException {
        usuarioDB.traerCantiadPaqueteEnColaPuntoControl(idPuntoControl, conexion);
        int cantidad = 0;
        while (conexion.resultSet.next()){
            cantidad = conexion.resultSet.getInt(1);
        }
        return cantidad;
    }



}
