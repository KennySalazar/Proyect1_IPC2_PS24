package com.example.proyecto1_ipc2.service;

import com.example.proyecto1_ipc2.conexion.Conexion;
import com.example.proyecto1_ipc2.controller.LoginController;
import com.example.proyecto1_ipc2.dataBase.AdminDB;
import com.example.proyecto1_ipc2.dataBase.RecepcionistaDB;
import com.example.proyecto1_ipc2.dataBase.RutaDB;
import com.example.proyecto1_ipc2.modelo.Ruta;
import com.example.proyecto1_ipc2.modelo.Usuario;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RutaService {
    private AdminDB usuarioDB;
    private RecepcionistaDB recepcionistaDB;

    public RutaService(){
        usuarioDB = new AdminDB();
        recepcionistaDB = new RecepcionistaDB();
    }


    public void guardarRuta(Ruta ruta, Conexion conexion) {
        usuarioDB.guardarRuta(ruta, conexion);
    }
    public void actualizarRuta(Ruta ruta, Conexion conexion, int id){
        usuarioDB.actualizarRuta(conexion, ruta, id);
    }
    public List<Ruta> traerRutas(int id) throws SQLException {
        List<Ruta> list=new ArrayList<>();
        usuarioDB.mostrarRuta(LoginController.conexion, id);
        while(LoginController.conexion.resultSet.next()){
            Ruta nuevo= new Ruta(LoginController.conexion.getResultSet().getInt(1),LoginController.conexion.getResultSet().getBoolean(2),LoginController.conexion.getResultSet().getBoolean(3),
                    LoginController.conexion.getResultSet().getInt(4), LoginController.conexion.getResultSet().getInt(5), LoginController.conexion.resultSet.getInt(6));

            list.add(nuevo);
        }
        return list;
    }
    public List<Ruta> traerRutasTodas() throws SQLException {
        List<Ruta> list=new ArrayList<>();
        usuarioDB.mostrarTodasRutas(LoginController.conexion);
        while(LoginController.conexion.resultSet.next()){
            Ruta nuevo= new Ruta(LoginController.conexion.getResultSet().getInt(1),LoginController.conexion.getResultSet().getBoolean(2),LoginController.conexion.getResultSet().getBoolean(3),
                    LoginController.conexion.getResultSet().getInt(4), LoginController.conexion.getResultSet().getInt(5), LoginController.conexion.resultSet.getInt(6));

            list.add(nuevo);
        }
        return list;
    }

    public void eliminarRuta(Conexion conexion, int id){
        usuarioDB.eliminarRuta(id, conexion);
    }

    public int devolverCantidadPaquetesEnRuta(int idRuta, Conexion conexion) throws SQLException {
        recepcionistaDB.traerCantiadPaqueteEnColaRuta(idRuta, conexion);
        int cantidad = 0;
        while (conexion.resultSet.next()){
            cantidad = conexion.resultSet.getInt(1);
        }
        return cantidad;
    }
}
