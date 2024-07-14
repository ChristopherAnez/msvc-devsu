package com.canez.msvcagregador.dto;

import com.canez.msvcagregador.dto.movimientodto.TipoMovimiento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoReporteDto {

    private LocalDate fecha;
    private TipoMovimiento tipoMovimiento;
    private Integer valor;
    private Integer saldo;

}
