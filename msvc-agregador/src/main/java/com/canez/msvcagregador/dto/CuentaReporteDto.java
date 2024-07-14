package com.canez.msvcagregador.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class CuentaReporteDto {

    private String numeroCuenta;
    private Integer saldoInicial;
    private Integer saldoDisponible;
    private List<MovimientoReporteDto> movimientos;

}
