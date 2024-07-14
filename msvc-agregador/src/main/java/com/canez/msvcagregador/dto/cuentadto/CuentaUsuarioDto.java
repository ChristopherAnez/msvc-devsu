package com.canez.msvcagregador.dto.cuentadto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class CuentaUsuarioDto {

    private Integer id_cuenta;
    private Integer id_cliente;
    private String numeroCuenta;
    private TipoCuenta tipoCuenta;
    private Integer saldoInicial;
    private Integer saldoDisponible;
    private Boolean estado;

}
