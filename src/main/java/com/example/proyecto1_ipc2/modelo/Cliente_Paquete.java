package com.example.proyecto1_ipc2.modelo;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente_Paquete {
    private Cliente NitCliente;
    private Paquete codigoPaquete;
}
