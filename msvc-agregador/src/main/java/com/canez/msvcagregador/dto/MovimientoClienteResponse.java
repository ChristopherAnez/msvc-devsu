package com.canez.msvcagregador.dto;

import com.canez.msvcagregador.dto.cuentadto.TipoCuenta;
import com.canez.msvcagregador.dto.movimientodto.TipoMovimiento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class MovimientoClienteResponse {

    private Integer idMovimiento;
    private LocalDate fecha;
    private String nombreCliente;
    private String numeroCuenta;
    private TipoCuenta tipoCuenta;
    private Integer saldoInicial;
    private Boolean estado;
    private Integer valor;
    private TipoMovimiento tipoMovimiento;
    private Integer saldoDisponible;

}
