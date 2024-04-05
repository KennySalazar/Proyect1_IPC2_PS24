package com.example.proyecto1_ipc2.modelo;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ruta {
    private int id;
    private boolean disponible;
    private boolean estado;
    private int idUsuario; // A QUE PUNTO DE CONTROL SE DIRIGE (DESTINO)
    private int idPuntoControl; //Que usuario creo ese punto de control
    private int limiteCola;

}

