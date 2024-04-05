package com.example.proyecto1_ipc2.modelo.usuario;

import com.example.proyecto1_ipc2.modelo.Usuario;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Recepcionista extends Usuario {

    public Recepcionista(int ID, String nombre, int rol, String password, boolean estado, String nombreUsuario, double precioXLibra) {
        super(ID, nombre, rol, password, estado, nombreUsuario, precioXLibra);

    }
}
