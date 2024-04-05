package com.example.proyecto1_ipc2.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Usuario{

    private int ID;
    private String nombre;
    private int rol;
    private String password;
    private boolean estado;
    private String nombreUsuario;
    private double precioXLibra;


}
