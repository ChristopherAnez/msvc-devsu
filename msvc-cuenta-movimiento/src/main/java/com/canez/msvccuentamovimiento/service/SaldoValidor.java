package com.canez.msvccuentamovimiento.service;

import com.canez.msvccuentamovimiento.dto.TipoMovimiento;

public class SaldoValidor {

    public static void validarSaldo(Integer saldoActual, Integer valorMovimiento, TipoMovimiento tipoMovimiento) {
        if (tipoMovimiento == TipoMovimiento.DEBITO || tipoMovimiento == TipoMovimiento.RETIRO) {
            Integer nuevoSaldo = saldoActual - valorMovimiento;
            if (nuevoSaldo < 0) {
                throw new RuntimeException("SALDO_NO_DISPONIBLE");
            }
        }
    }

    public static Integer calcularNuevoSaldo(Integer saldoActual, Integer valorMovimiento, TipoMovimiento tipoMovimiento) {
        if (tipoMovimiento == TipoMovimiento.DEBITO || tipoMovimiento == TipoMovimiento.RETIRO) {
            return saldoActual - valorMovimiento;
        } else {
            return saldoActual + valorMovimiento;
        }
    }


}
