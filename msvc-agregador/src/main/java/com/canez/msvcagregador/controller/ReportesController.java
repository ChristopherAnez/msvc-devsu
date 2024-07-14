package com.canez.msvcagregador.controller;

import com.canez.msvcagregador.client.CuentaWebClient;
import com.canez.msvcagregador.dto.movimientodto.EstadoCuentaReporteDto;
import com.canez.msvcagregador.service.ReportesOrquestadorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@RestController
@RequestMapping("api/reportes")
public class ReportesController {


    private static final Logger log = LoggerFactory.getLogger(CuentaWebClient.class);

    @Autowired
    private ReportesOrquestadorService reporteService;

    @GetMapping
    public Flux<EstadoCuentaReporteDto> obtenerReporteEstadoCuenta(@RequestParam("fechaInicio") LocalDate fechaInicio,
                                                                   @RequestParam("fechaFin") LocalDate fechaFin,
                                                                   @RequestParam("clienteId") Integer clienteId) {
        return reporteService.generarReporteEstadoCuenta(fechaInicio, fechaFin, clienteId);
    }

}
