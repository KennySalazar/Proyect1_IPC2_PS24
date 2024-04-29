package com.example.proyecto1_ipc2.service;

import com.example.proyecto1_ipc2.conexion.Conexion;
import com.example.proyecto1_ipc2.controller.LoginController;
import com.example.proyecto1_ipc2.dataBase.ReportesDB;
import com.example.proyecto1_ipc2.modelo.reportes.PaquetesRecorridoRuta;
import com.example.proyecto1_ipc2.modelo.reportes.Top3Rutas;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportesService {
    private final ReportesDB reportesDB = new ReportesDB();
    public ArrayList<Top3Rutas> traerReporteTop3Rutas(Conexion conexion) throws SQLException, SQLException {
        ArrayList<Top3Rutas> lista = new ArrayList<>();
        reportesDB.traerTop3Rutas(conexion);
        while(conexion.getResultSet().next()){
            Top3Rutas ruta = new Top3Rutas(conexion.getResultSet().getInt(1),
                                    conexion.getResultSet().getBoolean(2),
                                    conexion.getResultSet().getBoolean(3),
                                    conexion.getResultSet().getInt(4),
                                    conexion.getResultSet().getInt(5),
                                    conexion.getResultSet().getInt(6),
                                    conexion.getResultSet().getInt(7));
            lista.add(ruta);

        }

        return lista;


    }

    public ArrayList<PaquetesRecorridoRuta> traerPaquetesEnRuta(Conexion conexion, boolean estado) throws SQLException, SQLException {
        ArrayList<PaquetesRecorridoRuta> lista = new ArrayList<>();
        reportesDB.traerPaquetesEnRuta(conexion, estado);
        while(conexion.getResultSet().next()){
            PaquetesRecorridoRuta ruta = new PaquetesRecorridoRuta(conexion.getResultSet().getInt(1),
                    conexion.getResultSet().getBoolean(2),
                    conexion.getResultSet().getBoolean(3),
                    conexion.getResultSet().getInt(4),
                    conexion.getResultSet().getInt(5),
                    conexion.getResultSet().getInt(6),
                    conexion.getResultSet().getInt(7));
            lista.add(ruta);

        }

        return lista;


    }

    public ArrayList<PaquetesRecorridoRuta> traerPaquetesFueraDeRuta(Conexion conexion, boolean estado) throws SQLException, SQLException {
        ArrayList<PaquetesRecorridoRuta> lista = new ArrayList<>();
        reportesDB.traerPaquetesFueraDeRuta(conexion, estado);
        while(conexion.getResultSet().next()){
            PaquetesRecorridoRuta ruta = new PaquetesRecorridoRuta(conexion.getResultSet().getInt(1),
                    conexion.getResultSet().getBoolean(2),
                    conexion.getResultSet().getBoolean(3),
                    conexion.getResultSet().getInt(4),
                    conexion.getResultSet().getInt(5),
                    conexion.getResultSet().getInt(6),
                    conexion.getResultSet().getInt(7));
            lista.add(ruta);

        }

        return lista;


    }






}
