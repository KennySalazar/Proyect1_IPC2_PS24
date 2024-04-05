package com.example.proyecto1_ipc2.dataBase;

import com.example.proyecto1_ipc2.conexion.Conexion;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoginDB {
    public void traerUsuarios(String usuarios, Conexion conexion) {

        String query = "select * from "+usuarios+";";
        try {
            PreparedStatement stmt = conexion.getConnection().prepareStatement(query);

            conexion.setResultSet(stmt.executeQuery());

        } catch (SQLException e) {
            System.out.println("Error al consultar " + usuarios + ": " + e);
        }
    }
}
