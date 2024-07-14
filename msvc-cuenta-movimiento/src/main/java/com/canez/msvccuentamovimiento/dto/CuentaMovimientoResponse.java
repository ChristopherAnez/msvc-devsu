package com.canez.msvccuentamovimiento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class CuentaMovimientoResponse {

    private Integer idMovimiento;
    private LocalDate fecha;
    private TipoMovimiento tipoMovimiento;
    private String valor;
    private Integer saldo;
    private String numeroCuenta;

}
