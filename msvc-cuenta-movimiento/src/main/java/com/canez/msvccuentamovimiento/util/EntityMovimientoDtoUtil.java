package com.canez.msvccuentamovimiento.util;

import com.canez.msvccuentamovimiento.dto.*;
import com.canez.msvccuentamovimiento.entity.Cuenta;
import com.canez.msvccuentamovimiento.entity.Movimientos;

import java.time.LocalDate;

public class EntityMovimientoDtoUtil {

    public static Movimientos toMovimientos(CuentaMovimientoDto request) {
        Movimientos movimientos = new Movimientos();
        movimientos.setId(request.getIdMovimiento());
        movimientos.setNumerocuenta(request.getNumeroCuenta());
        movimientos.setTipoMovimiento(request.getTipoMovimiento());
        movimientos.setValor(request.getValor());
        movimientos.setSaldo(request.getSaldo());
        movimientos.setFecha(LocalDate.now());
        return movimientos;
    }

    public static Movimientos toMovimientosRequest(CuentaMovimientoRequest request) {
        Movimientos movimientos = new Movimientos();
        movimientos.setId(request.getIdMovimiento());
        movimientos.setNumerocuenta(request.getNumeroCuenta());
        movimientos.setTipoMovimiento(request.getTipoMovimiento());
        movimientos.setValor(request.getValor());
        movimientos.setSaldo(request.getSaldo());
        movimientos.setFecha(request.getFecha());
        return movimientos;
    }

    public static Movimientos toMovimientosOperacion(OperacionDto request) {
        Movimientos movimientos = new Movimientos();
        movimientos.setNumerocuenta(request.getNumeroCuenta());
        movimientos.setTipoMovimiento(request.getTipoMovimiento());
        movimientos.setValor(request.getValor());
        movimientos.setFecha(LocalDate.now());
        return movimientos;
    }

    public static CuentaMovimientoDto toDto(Movimientos movimientos) {
        CuentaMovimientoDto dto = new CuentaMovimientoDto();
        dto.setIdMovimiento(movimientos.getId());
        dto.setFecha(movimientos.getFecha());
        dto.setTipoMovimiento(movimientos.getTipoMovimiento());
        dto.setValor(movimientos.getValor());
        dto.setSaldo(movimientos.getSaldo());
        dto.setNumeroCuenta(movimientos.getNumerocuenta());
        return dto;
    }

}
