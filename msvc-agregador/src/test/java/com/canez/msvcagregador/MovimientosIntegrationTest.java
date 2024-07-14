package com.canez.msvcagregador;

import com.canez.msvcagregador.client.ClienteWebClient;
import com.canez.msvcagregador.client.CuentaWebClient;
import com.canez.msvcagregador.client.MovimientoWebClient;
import com.canez.msvcagregador.dto.movimientodto.EstadoCuentaReporteDto;
import com.canez.msvcagregador.service.ReportesOrquestadorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDate;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MovimientosIntegrationTest {

    @Value("${msvc.cliente.service}")
    private String clienteBaseUrl;

    @Value("${msvc.cuentas.service}")
    private String cuentasBaseUrl;

    @Value("${msvc.movimientos.service}")
    private String movimientosBaseUrl;

    private WebClient client;
    private ClienteWebClient clienteWebClient;
    private CuentaWebClient cuentaWebClient;
    private MovimientoWebClient movimientoWebClient;
    private ReportesOrquestadorService reportesOrquestadorService;

    @BeforeAll
    public void setClient() {
        this.client = WebClient.builder()
                .baseUrl(clienteBaseUrl)
                .build();
        this.clienteWebClient = new ClienteWebClient(clienteBaseUrl);
        this.cuentaWebClient = new CuentaWebClient(cuentasBaseUrl);
        this.movimientoWebClient = new MovimientoWebClient(movimientosBaseUrl);
        this.reportesOrquestadorService = new ReportesOrquestadorService(clienteWebClient, cuentaWebClient, movimientoWebClient);
    }

    @Test
    public void generarReporteEstadoCuentaTest() {
        LocalDate fechaInicio = LocalDate.of(2024, 1, 1);
        LocalDate fechaFin = LocalDate.of(2024, 12, 31);
        Integer clienteId = 2;

        Flux<EstadoCuentaReporteDto> reporteFlux = this.reportesOrquestadorService.generarReporteEstadoCuenta(fechaInicio, fechaFin, clienteId);

        StepVerifier.create(reporteFlux)
                .expectNextMatches(reporte -> {
                    return reporte.getClienteId() == clienteId && !reporte.getCuentas().isEmpty();
                })
                .verifyComplete();
    }

}
