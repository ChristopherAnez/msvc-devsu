package com.canez.msvcagregador.dto.movimientodto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class CuentaMovimientoDto {

    private Integer idMovimiento;
    private LocalDate fecha;
    private TipoMovimiento tipoMovimiento;
    private Integer valor;
    private Integer saldo;
    private String numeroCuenta;

}
