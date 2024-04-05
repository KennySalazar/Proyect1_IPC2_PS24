package com.example.proyecto1_ipc2.modelo;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    private int nit;
    private String nombre;
    private String direccion;
    private String telefono;

}
