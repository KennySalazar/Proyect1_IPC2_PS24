package com.example.proyecto1_ipc2.conexion;

import lombok.*;

import  java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.DriverManager;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public  class Conexion {
    public    Connection connection;
    public  Statement statement;
    public  ResultSet resultSet;

    private static  Conexion instance;
   public static Conexion getInstance(){
       if (instance == null){
        instance = new Conexion();
       }
       return  instance;
   }
    public void conectar(){
        String driver = "com.mysql.cj.jdbc.Driver";
        String user = "proyecto1IPC2_2024";
        String password = "kenny123";
        String url = "jdbc:mysql://localhost:3306/sistema_paquetes";
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            System.out.println("Conexion establecida");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error de conexion" + e);
        }

    }
    public void cerrarConexion(){
        try {
            connection.close();
            System.out.println("Conexion cerrada");
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

}
