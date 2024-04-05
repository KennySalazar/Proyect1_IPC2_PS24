package com.example.proyecto1_ipc2.modelo;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paquete {

    private int codigo;
    private String detalle;
    private int estado;
    private double cuota;
    private String destino;
    private int idRutaPaquete; //que ruta lleva el paquete a su destino
    private int idPuntoControlPaquete;
    private int idUsuarioCreador;
    private String horaIngreso;
    private String horaSalida;
    private double peso;


}
