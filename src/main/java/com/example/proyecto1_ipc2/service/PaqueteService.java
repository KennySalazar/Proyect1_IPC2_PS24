package com.example.proyecto1_ipc2.service;

import com.example.proyecto1_ipc2.conexion.Conexion;
import com.example.proyecto1_ipc2.controller.LoginController;
import com.example.proyecto1_ipc2.dataBase.AdminDB;
import com.example.proyecto1_ipc2.dataBase.RecepcionistaDB;
import com.example.proyecto1_ipc2.modelo.Paquete;
import com.example.proyecto1_ipc2.modelo.Punto_Control;
import com.example.proyecto1_ipc2.modelo.Ruta;
import com.example.proyecto1_ipc2.modelo.Usuario;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PaqueteService {
    private RecepcionistaDB recepcionistaDB;
    private RutaService rutaService;
    private Punto_Control_Service puntoControlService;

    public PaqueteService(){
        recepcionistaDB = new RecepcionistaDB();
        rutaService = new RutaService();
        puntoControlService = new Punto_Control_Service();
    }


    public void guardarPaquete(Paquete paquete, Conexion conexion) {
        recepcionistaDB.guardarPaquete(paquete, conexion);
    }
    public void actualizarPaquete(Paquete paquete, Conexion conexion, int codigo){
        recepcionistaDB.actualizarPaquetes(paquete, conexion, codigo);
    }
    public List<Paquete> traerTodosPaquetes(int codigo) throws SQLException {
        List<Paquete> list=new ArrayList<>();
        recepcionistaDB.mostrarPaquete(LoginController.conexion,codigo);
        while(LoginController.conexion.resultSet.next()){
            Paquete nuevo = new Paquete(LoginController.conexion.getResultSet().getInt(1),LoginController.conexion.getResultSet().getString(2),LoginController.conexion.getResultSet().getInt(3),
                    LoginController.conexion.getResultSet().getDouble(4),LoginController.conexion.getResultSet().getString(5),LoginController.conexion.getResultSet().getInt(6), LoginController.conexion.getResultSet().getInt(7),
                    LoginController.conexion.getResultSet().getInt(8),LoginController.conexion.resultSet.getString(9), LoginController.conexion.resultSet.getString(10), LoginController.conexion.resultSet.getInt(11));
            list.add(nuevo);
        }
        return list;
    }

    public List<Paquete> traerLocalizacionPaquetesXPuntoControl(int idPuntoControl) throws SQLException {
        List<Paquete> list=new ArrayList<>();
        recepcionistaDB.mostrarLocalizacionPaqueteXIdPuntoControl(LoginController.conexion,idPuntoControl);
        while(LoginController.conexion.resultSet.next()){
            Paquete nuevo = new Paquete(LoginController.conexion.getResultSet().getInt(1),LoginController.conexion.getResultSet().getString(2),LoginController.conexion.getResultSet().getInt(3),
                    LoginController.conexion.getResultSet().getDouble(4),LoginController.conexion.getResultSet().getString(5),LoginController.conexion.getResultSet().getInt(6), LoginController.conexion.getResultSet().getInt(7),
                    LoginController.conexion.getResultSet().getInt(8),LoginController.conexion.resultSet.getString(9), LoginController.conexion.resultSet.getString(10), LoginController.conexion.resultSet.getInt(11));
            list.add(nuevo);
        }
        return list;
    }
    public List<Paquete> traerDestinoPaquete(String destino) throws SQLException {
        List<Paquete> list=new ArrayList<>();
        recepcionistaDB.mostrarDestinoPaquete(LoginController.conexion,destino);
        while(LoginController.conexion.resultSet.next()){
            Paquete nuevo = new Paquete(LoginController.conexion.getResultSet().getInt(1),LoginController.conexion.getResultSet().getString(2),LoginController.conexion.getResultSet().getInt(3),
                    LoginController.conexion.getResultSet().getDouble(4),LoginController.conexion.getResultSet().getString(5),LoginController.conexion.getResultSet().getInt(6), LoginController.conexion.getResultSet().getInt(7),
                    LoginController.conexion.getResultSet().getInt(8),LoginController.conexion.resultSet.getString(9), LoginController.conexion.resultSet.getString(10), LoginController.conexion.resultSet.getInt(11));
            list.add(nuevo);
        }
        return list;
    }

    public List<Paquete> traerTodosPaquetesPorEstado(int estado) throws SQLException {
        List<Paquete> list=new ArrayList<>();
        recepcionistaDB.mostrarPaquetePorEstado(LoginController.conexion,estado);
        while(LoginController.conexion.resultSet.next()){
            Paquete nuevo = new Paquete(LoginController.conexion.getResultSet().getInt(1),LoginController.conexion.getResultSet().getString(2),LoginController.conexion.getResultSet().getInt(3),
                    LoginController.conexion.getResultSet().getDouble(4),LoginController.conexion.getResultSet().getString(5),LoginController.conexion.getResultSet().getInt(6), LoginController.conexion.getResultSet().getInt(7),
                    LoginController.conexion.getResultSet().getInt(8),LoginController.conexion.resultSet.getString(9), LoginController.conexion.resultSet.getString(10), LoginController.conexion.resultSet.getInt(11));
            list.add(nuevo);
        }
        return list;
    }

    public void eliminarPaquete(Conexion conexion, int codigo){
        recepcionistaDB.eliminarPaquete(codigo, conexion);
    }

    public void verificarEnvioPaquetesARuta(Conexion conexion) throws SQLException {
        for(Paquete paquete: traerTodosPaquetesPorEstado(0)){
            int cantidadPaquetesEnColaRuta = rutaService.devolverCantidadPaquetesEnRuta(paquete.getIdRutaPaquete(), conexion);
            Ruta ruta = rutaService.traerTodosRuta(paquete.getIdRutaPaquete()).get(0);
            if(cantidadPaquetesEnColaRuta < ruta.getLimiteCola()){
                paquete.setEstado(1);
                actualizarPaquete(paquete, conexion, paquete.getCodigo());
                System.out.println("SE HA ENVIADO EL PAQUETE A COLA DE LA RUTA");
            } else{
                System.out.println("RUTA LLENA, DEBE ESPERAR A QUE SE LIBERE O BUSCAR OTRO RUTA");
            }
        }
    }

    public void verificarSiElPaqueteYaLlegoAlPuntoControl(Conexion conexion) throws SQLException {
        for(Paquete paquete: traerTodosPaquetesPorEstado(1)){
            int cantidadPaquetesEnColaPuntoControl = puntoControlService.devolverCantidadPaquetesEnPuntoControl(paquete.getIdPuntoControlPaquete(), conexion);
            Punto_Control puntoControl = puntoControlService.traerTodosPuntosControl(paquete.getIdPuntoControlPaquete()).get(0);

            if(cantidadPaquetesEnColaPuntoControl < puntoControl.getLimiteCola()){
                paquete.setEstado(2);
                horaEntradaPaqueteAPuntoControl(paquete); //METODO PARA AGREGAR LA HORA ACTUAL
                actualizarPaquete(paquete, conexion, paquete.getCodigo());
                System.out.println("SE HA ENVIADO EL PAQUETE A COLA DEL PUNTO DE CONTROL");
            } else{
                System.out.println("PUNTO DE CONTROL LLENO, DEBE ESPERAR A QUE SE LIBERE O BUSCAR OTRO PUNTO CONTROL");
            }
        }
    }


    public void horaEntradaPaqueteAPuntoControl(Paquete paquete){
        // Obtener la fecha y hora actual
        LocalDateTime now = LocalDateTime.now();

        // Definir un formato personalizado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Formatear la fecha y hora actual
        String formattedDateTime = now.format(formatter);

        // Imprimir la fecha y hora actual formateada
        System.out.println("Fecha y hora actual: " + formattedDateTime);
        paquete.setHoraIngreso(formattedDateTime);

    }

    public int traerDiferenciaHoras(int codigo, Conexion conexion) throws SQLException {
        recepcionistaDB.traerDifenciaHoras(codigo, conexion);
        if (conexion.resultSet.next()) {
            return conexion.resultSet.getInt(1);
        } else {
            throw new RuntimeException("No se encontraron resultados para el código: " + codigo);
        }
    }



}
