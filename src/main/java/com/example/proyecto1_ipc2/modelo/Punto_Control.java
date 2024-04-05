package com.example.proyecto1_ipc2.modelo;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Punto_Control {

    private int id;
    private int tarifa;
    private int idUsuarioOperador;
    private int limiteCola;
}
