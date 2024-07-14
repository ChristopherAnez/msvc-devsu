package com.canez.msvcagregador.util;

import com.canez.msvcagregador.dto.CuentaReporteDto;
import com.canez.msvcagregador.dto.MovimientoReporteDto;
import com.canez.msvcagregador.dto.clientedto.ClientePersonaDto;
import com.canez.msvcagregador.dto.cuentadto.CuentaUsuarioDto;
import com.canez.msvcagregador.dto.movimientodto.EstadoCuentaReporteDto;

import java.util.List;

public class EntityReportesDtoUtil {

    public static CuentaReporteDto toCuentaReporteDto(CuentaUsuarioDto cuenta, List<MovimientoReporteDto> movimientos) {
        return CuentaReporteDto.create(
                cuenta.getNumeroCuenta(),
                cuenta.getSaldoInicial(),
                cuenta.getSaldoDisponible(),
                movimientos
        );
    }

    public static EstadoCuentaReporteDto toEstadoCuentaReporteDto(ClientePersonaDto cliente, List<CuentaReporteDto> cuentas) {
        return EstadoCuentaReporteDto.create(
                cliente.getPersonaId(),
                cliente.getNombre(),
                cuentas
        );
    }

}
