package com.example.proyecto1_ipc2.dataBase;

import com.example.proyecto1_ipc2.conexion.Conexion;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReportesDB {
    public void traerTop3Rutas(Conexion conexion) {

        String query = "select r.id_ruta, r.disponible, r.estado,r.id_usuario,r.id_punto_control, r.limite_cola, count(p.id_ruta) from ruta r join paquete p on (p.id_ruta=r.id_ruta) where p.estado=4 group by(r.id_ruta) having count(*)>=1 order by(sum(p.id_ruta)) desc limit 3 ;";
        try {
            PreparedStatement stmt = conexion.getConnection().prepareStatement(query);
            conexion.setResultSet(stmt.executeQuery());
        } catch (SQLException e) {
            System.out.println("Error al consultar el reporte:" + e);
        }
    }

    public void traerPaquetesEnRuta(Conexion conexion, boolean estado) {

        String query = "select r.id_ruta, r.disponible, r.estado,r.id_usuario,r.id_punto_control, r.limite_cola, count(p.id_ruta) from ruta r join paquete p on (p.id_ruta=r.id_ruta) where p.estado=1 and r.estado = ? group by(r.id_ruta) having count(*)>=1 order by(sum(p.id_ruta)) desc  ;";
        try {
            PreparedStatement stmt = conexion.getConnection().prepareStatement(query);
            stmt.setBoolean(1, estado);
            conexion.setResultSet(stmt.executeQuery());
        } catch (SQLException e) {
            System.out.println("Error al consultar el reporte:" + e);
        }
    }

    public void traerPaquetesFueraDeRuta(Conexion conexion, boolean estado) {

        String query = "select r.id_ruta, r.disponible, r.estado,r.id_usuario,r.id_punto_control, r.limite_cola, count(p.id_ruta) from ruta r join paquete p on (p.id_ruta=r.id_ruta) where p.estado=2 and r.estado = ? group by(r.id_ruta) having count(*)>=1 order by(sum(p.id_ruta)) desc  ;";
        try {
            PreparedStatement stmt = conexion.getConnection().prepareStatement(query);
            stmt.setBoolean(1, estado);
            conexion.setResultSet(stmt.executeQuery());
        } catch (SQLException e) {
            System.out.println("Error al consultar el reporte:" + e);
        }
    }
}
