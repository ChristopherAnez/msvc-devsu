package com.canez.msvcagregador.service;

import com.canez.msvcagregador.client.ClienteWebClient;
import com.canez.msvcagregador.client.CuentaWebClient;
import com.canez.msvcagregador.client.MovimientoWebClient;
import com.canez.msvcagregador.dto.CuentaReporteDto;
import com.canez.msvcagregador.dto.movimientodto.EstadoCuentaReporteDto;
import com.canez.msvcagregador.util.EntityDtoUtil;
import com.canez.msvcagregador.util.EntityReportesDtoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Service
public class ReportesOrquestadorService {

    private static final Logger log = LoggerFactory.getLogger(ClienteCuentaOrquestadorService.class);
    private final ClienteWebClient clienteWebClient;
    private final CuentaWebClient cuentaWebClient;
    private final MovimientoWebClient movimientoWebClient;

    @Autowired
    public ReportesOrquestadorService(ClienteWebClient clienteWebClient, CuentaWebClient cuentaWebClient, MovimientoWebClient movimientoWebClient) {
        this.clienteWebClient = clienteWebClient;
        this.cuentaWebClient = cuentaWebClient;
        this.movimientoWebClient = movimientoWebClient;
    }

    public Flux<EstadoCuentaReporteDto> generarReporteEstadoCuenta(LocalDate fechaInicio, LocalDate fechaFin, Integer clienteId) {
        return clienteWebClient.listarById(clienteId)
                .flatMapMany(cliente -> cuentaWebClient.listarCuentasByClienteId(clienteId)
                        .flatMap(cuenta -> movimientoWebClient.listarMovimientosByFechaCuenta(fechaInicio, fechaFin, cuenta.getNumeroCuenta())
                                .collectList()
                                .map(movimientos -> EntityReportesDtoUtil.toCuentaReporteDto(cuenta, movimientos))
                        )
                        .collectList()
                        .map(cuentas -> EntityReportesDtoUtil.toEstadoCuentaReporteDto(cliente, cuentas))
                );
    }

}
