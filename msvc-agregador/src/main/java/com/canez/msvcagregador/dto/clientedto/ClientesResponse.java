package com.canez.msvcagregador.dto.clientedto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class ClientesResponse {

    private Integer idCliente;
    private String nombreCliente;
    private String direccion;
    private String telefono;
    private String contrasena;
    private Boolean estado;

}
