package com.example.proyecto1_ipc2.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Login {
    private String rol;
    private String userName;
    private String password;
}

