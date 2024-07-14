package com.canez.msvccuentamovimiento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class OperacionDto {

    private TipoMovimiento tipoMovimiento;
    private String numeroCuenta;
    private Integer valor;

}
