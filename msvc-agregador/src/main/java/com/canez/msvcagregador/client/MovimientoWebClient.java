package com.canez.msvcagregador.client;

import com.canez.msvcagregador.dto.MovimientoReporteDto;
import com.canez.msvcagregador.dto.cuentadto.CuentaUsuarioDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDate;

@Service
public class MovimientoWebClient {

    private static final Logger log = LoggerFactory.getLogger(CuentaWebClient.class);
    private final WebClient client;

    public MovimientoWebClient(@Value("${msvc.movimientos.service}") String baseUrl) {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @CircuitBreaker(name = "breaker-movimiento-service")
    public Mono<CuentaUsuarioDto> crearMovimiento(Mono<CuentaUsuarioDto> cuentaDtoMono) {
        return this.client
                .post()
                .uri("/crear-movimiento")
                .body(cuentaDtoMono, CuentaUsuarioDto.class)
                .retrieve()
                .bodyToMono(CuentaUsuarioDto.class)
                .retry(5)
                .timeout(Duration.ofMillis(500))
                .onErrorResume(ex -> Mono.empty());
    }

    @CircuitBreaker(name = "breaker-movimiento-service")
    public Flux<MovimientoReporteDto> listarMovimientosByFechaCuenta(LocalDate fechaInicio, LocalDate fechaFin, String numeroCuenta) {
        return this.client
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/listar/mov-cuenta")
                        .queryParam("fechaIni", fechaInicio)
                        .queryParam("fechaFin", fechaFin)
                        .queryParam("numeroCuenta", numeroCuenta)
                        .build())
                .retrieve()
                .bodyToFlux(MovimientoReporteDto.class)
                .doOnNext(movimiento -> log.info("Movimiento obtenido: {}", movimiento))
                .retry(5)
                .timeout(Duration.ofMillis(500))
                .onErrorResume(ex -> {
                    log.error("Error obteniendo movimientos", ex);
                    return Flux.empty();
                });
    }

}
