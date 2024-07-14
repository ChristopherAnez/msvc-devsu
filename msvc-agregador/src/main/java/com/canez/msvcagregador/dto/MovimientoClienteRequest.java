package com.canez.msvcagregador.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MovimientoClienteRequest {

    private String numeroCuenta;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

}
