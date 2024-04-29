package com.example.proyecto1_ipc2.modelo.reportes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Top3Rutas {
    private int id;
    private boolean disponible;
    private boolean estado;
    private int idUsuario; // A QUE PUNTO DE CONTROL SE DIRIGE (DESTINO)
    private int idPuntoControl; //Que usuario creo ese punto de control
    private int limiteCola;
    private int cantidadPaquetes;
}
