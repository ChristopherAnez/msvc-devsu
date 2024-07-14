package com.canez.msvccuentamovimiento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class CuentaUsuarioRequest {

    private Integer id_cuenta;
    private Integer id_cliente;
    private String numeroCuenta;
    private TipoCuenta tipoCuenta;
    private Integer saldoDisponible;
    private Boolean estado;

}
