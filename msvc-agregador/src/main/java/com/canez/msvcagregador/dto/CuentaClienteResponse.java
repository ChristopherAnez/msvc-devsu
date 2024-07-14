package com.canez.msvcagregador.dto;

import com.canez.msvcagregador.dto.cuentadto.TipoCuenta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class CuentaClienteResponse {

    private String numeroCuenta;
    private TipoCuenta tipoCuenta;
    private Integer saldoInicial;
    private Boolean estado;
    private String nombreCliente;

}
