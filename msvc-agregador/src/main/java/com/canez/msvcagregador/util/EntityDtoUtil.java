package com.canez.msvcagregador.util;

import com.canez.msvcagregador.dto.*;
import com.canez.msvcagregador.dto.clientedto.ClientePersonaDto;
import com.canez.msvcagregador.dto.clientedto.ClientesResponse;
import com.canez.msvcagregador.dto.cuentadto.CuentaUsuarioDto;
import com.canez.msvcagregador.dto.movimientodto.EstadoCuentaReporteDto;

import java.util.List;

public class EntityDtoUtil {

    public static CuentaClienteResponse toResponse(CuentaUsuarioDto cuenta, ClientePersonaDto personaDto) {
        return CuentaClienteResponse.create(
                cuenta.getNumeroCuenta(),
                cuenta.getTipoCuenta(),
                cuenta.getSaldoInicial(),
                cuenta.getEstado(),
                personaDto.getNombre()
        );
    }

    public static ClientePersonaDto toClientePersonaDto(CrearClienteCuentaRequest request) {
        return ClientePersonaDto.create(
                null,
                null,
                request.getNombre(),
                request.getGenero(),
                request.getEdad(),
                request.getIdentificacion(),
                request.getDireccion(),
                request.getTelefono(),
                request.getContrasena(),
                request.getEstado()
        );
    }

    public static CuentaUsuarioDto toCuentaUsuarioDto(CrearClienteCuentaRequest request) {
        return CuentaUsuarioDto.create(
                null,
                null,
                request.getNumeroCuenta(),
                request.getTipoCuenta(),
                request.getSaldoInicial(),
                request.getSaldoDisponible(),
                request.getEstado()
        );
    }

    public static CuentaUsuarioDto toCuentaNueva(CrearCuentaRequest request) {
        return CuentaUsuarioDto.create(
                null,
                request.getIdCliente(),
                request.getNumeroCuenta(),
                request.getTipoCuenta(),
                request.getSaldoInicial(),
                request.getSaldoInicial(),
                true
        );
    }

    public static ClientesResponse toClienteResponse(ClientePersonaDto dto) {
        return ClientesResponse.create(
                dto.getClienteId(),
                dto.getNombre(),
                dto.getDireccion(),
                dto.getTelefono(),
                dto.getContrasena(),
                dto.getEstado()
        );
    }



}
