package com.canez.msvccuentamovimiento.util;

import com.canez.msvccuentamovimiento.dto.*;
import com.canez.msvccuentamovimiento.entity.Cuenta;


public class EntityCuentaDtoUtil {

    public static Cuenta toCuentaRequest(CuentaUsuarioRequest request) {
        Cuenta cuenta = new Cuenta();
        cuenta.setId(request.getId_cuenta());
        cuenta.setNumeroCuenta(request.getNumeroCuenta());
        cuenta.setIdCliente(request.getId_cliente());
        cuenta.setEstado(request.getEstado());
        cuenta.setTipoCuenta(request.getTipoCuenta());
        cuenta.setSaldoDisponible(request.getSaldoDisponible());
        return cuenta;
    }

    public static Cuenta toCuenta(CuentaUsuarioDto request) {
        Cuenta cuenta = new Cuenta();
        cuenta.setId(request.getId_cuenta());
        cuenta.setNumeroCuenta(request.getNumeroCuenta());
        cuenta.setIdCliente(request.getId_cliente());
        cuenta.setEstado(request.getEstado());
        cuenta.setTipoCuenta(request.getTipoCuenta());
        cuenta.setSaldoInicial(request.getSaldoInicial());
        cuenta.setSaldoDisponible(request.getSaldoDisponible());
        return cuenta;
    }

    public static CuentaUsuarioResponse toCuentaResponse(Cuenta cuenta) {
        CuentaUsuarioResponse response = new CuentaUsuarioResponse();
        response.setId_cuenta(cuenta.getId());
        response.setEstado(cuenta.getEstado());
        response.setTipoCuenta(cuenta.getTipoCuenta());
        response.setSaldoDisponible(cuenta.getSaldoDisponible());
        response.setId_cliente(cuenta.getIdCliente());
        response.setNumeroCuenta(cuenta.getNumeroCuenta());
        return response;
    }

    public static CuentaUsuarioDto toResponseCuenta(Cuenta cuenta) {
        CuentaUsuarioDto response = new CuentaUsuarioDto();
        response.setId_cuenta(cuenta.getId());
        response.setEstado(cuenta.getEstado());
        response.setTipoCuenta(cuenta.getTipoCuenta());
        response.setSaldoInicial(cuenta.getSaldoInicial());
        response.setSaldoDisponible(cuenta.getSaldoDisponible());
        response.setId_cliente(cuenta.getIdCliente());
        response.setNumeroCuenta(cuenta.getNumeroCuenta());
        return response;
    }

}
