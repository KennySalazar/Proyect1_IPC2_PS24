package com.example.proyecto1_ipc2.dataBase;

import com.example.proyecto1_ipc2.conexion.Conexion;
import com.example.proyecto1_ipc2.modelo.Paquete;
import com.example.proyecto1_ipc2.modelo.Usuario;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RecepcionistaDB {
    public void guardarPaquete(Paquete paquete, Conexion conexion){
        String query = "INSERT INTO paquete(detalle, estado,cuota, destino, id_ruta, id_punto_control, id_usuario_creador, peso) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
        try (PreparedStatement stmt = conexion.connection.prepareStatement(query)) {
            stmt.setString(1, paquete.getDetalle());
            stmt.setInt(2, paquete.getEstado());
            stmt.setDouble(3, paquete.getCuota());
            stmt.setString(4, paquete.getDestino());
            stmt.setInt(5, paquete.getIdRutaPaquete());
            stmt.setDouble(6, paquete.getIdPuntoControlPaquete());
            stmt.setInt(7, paquete.getIdUsuarioCreador());
            stmt.setDouble(8, paquete.getPeso());
            stmt.executeUpdate();
            stmt.close();
            System.out.println("hecho");
        } catch (SQLException e) {
            System.out.println("Error al guardar paquete: " + e);
        }
    }

    public void actualizarPaquetes(Paquete paquete, Conexion conexion, int codigo){
        String query = "update paquete set detalle =?, estado=?,  cuota=?, " +
                "destino=?, id_ruta=?, id_punto_control=?, id_usuario_creador = ?," +
                "hora_ingreso = ?, hora_salida=?, peso=? WHERE codigo = ?;";
        try (PreparedStatement stmt = conexion.connection.prepareStatement(query)) {
            stmt.setString(1, paquete.getDetalle());
            stmt.setInt(2, paquete.getEstado());
            stmt.setDouble(3, paquete.getCuota());
            stmt.setString(4, paquete.getDestino());
            stmt.setInt(5, paquete.getIdRutaPaquete());
            stmt.setDouble(6, paquete.getIdPuntoControlPaquete());
            stmt.setInt(7, paquete.getIdUsuarioCreador());
            stmt.setString(8, paquete.getHoraIngreso());
            stmt.setString(9, paquete.getHoraSalida());
            stmt.setDouble(10, paquete.getPeso());
            stmt.setInt(11, codigo);
            stmt.executeUpdate();
            stmt.close();
            System.out.println("hecho");
        } catch (SQLException e) {
            System.out.println("Error al actualizar paquete: " + e);
        }
    }
    public void mostrarPaquete(Conexion conexion, int codigo) {
        String query;
        if(codigo<0){
            query = "select * from paquete";
        }else{
            query = "select * from paquete where codigo="+codigo;
        }

        try {
            PreparedStatement stmt = conexion.getConnection().prepareStatement(query);
            conexion.setResultSet(stmt.executeQuery());
        } catch (SQLException e) {
            System.out.println("Error al consultar los paquete:" + e);
        }
    }
    public void mostrarLocalizacionPaqueteXIdPuntoControl(Conexion conexion, int idPuntoControl) {
        String query;
        if(idPuntoControl<0){
            query = "select * from paquete";
        }else{
            query = "select * from paquete where id_punto_control="+idPuntoControl + " and estado = 2;";
        }

        try {
            PreparedStatement stmt = conexion.getConnection().prepareStatement(query);
            conexion.setResultSet(stmt.executeQuery());
        } catch (SQLException e) {
            System.out.println("Error al consultar los paquete:" + e);
        }
    }
    public void mostrarDestinoPaquete(Conexion conexion, String destino) {
        String query = "SELECT * FROM paquete WHERE destino = ? AND estado = 3";

        try {
            PreparedStatement stmt = conexion.getConnection().prepareStatement(query);
            stmt.setString(1, destino); // Establecer el valor del parámetro
            conexion.setResultSet(stmt.executeQuery());
        } catch (SQLException e) {
            System.out.println("Error al consultar el destino del paquete:" + e);
        }
    }
    public void mostrarPaquetePorEstado(Conexion conexion, int estado) {
        String query = "select * from paquete where estado="+estado;

        try {
            PreparedStatement stmt = conexion.getConnection().prepareStatement(query);
            conexion.setResultSet(stmt.executeQuery());
        } catch (SQLException e) {
            System.out.println("Error al consultar los paquete por estado:" + e);
        }
    }

    public void traerCantiadPaqueteEnColaRuta(int IDruta, Conexion conexion){
        String query = "SELECT COUNT(*) AS total_tuplas FROM paquete WHERE estado = 1 AND id_ruta = ?;";
        try {
            PreparedStatement stmt = conexion.getConnection().prepareStatement(query);
            stmt.setInt(1, IDruta);
            conexion.setResultSet(stmt.executeQuery());
        } catch (SQLException e) {
            System.out.println("Error al consultar los cantidad de paquetes en ruta:" + e);
        }
    }

    public void eliminarPaquete(int codigo, Conexion conexion){
        String query = "delete from paquete WHERE codigo = ?;";
        try {
            PreparedStatement stmt = conexion.getConnection().prepareStatement(query);

            stmt.setInt(1, codigo);
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error al eliminar paquete: " +e);
        }
    }

    public void traerDifenciaHoras(int codigo, Conexion conexion){
        String query = "SELECT TIMESTAMPDIFF(HOUR, hora_ingreso, hora_salida) AS diferencia_horas FROM paquete where codigo = ?;";
        try {
            PreparedStatement stmt = conexion.getConnection().prepareStatement(query);
            stmt.setInt(1, codigo);
            conexion.setResultSet(stmt.executeQuery());
        } catch (SQLException e) {
            System.out.println("Error al consultar la diferencia de horas:" + e);
        }
    }

}
