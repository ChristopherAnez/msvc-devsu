package com.canez.msvcagregador.client;

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

@Service
public class CuentaWebClient {

    private static final Logger log = LoggerFactory.getLogger(CuentaWebClient.class);
    private final WebClient client;


    public CuentaWebClient(@Value("${msvc.cuentas.service}") String baseUrl) {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @CircuitBreaker(name = "breaker-cuenta-service")
    public Mono<CuentaUsuarioDto> crearCuenta(Mono<CuentaUsuarioDto> cuentaDtoMono) {
        return this.client
                .post()
                .uri("/crear-cuenta")
                .body(cuentaDtoMono, CuentaUsuarioDto.class)
                .retrieve()
                .bodyToMono(CuentaUsuarioDto.class)
                .retry(5)
                .timeout(Duration.ofMillis(500))
                .onErrorResume(ex -> Mono.empty());
    }

    @CircuitBreaker(name = "breaker-cuenta-service")
    public Flux<CuentaUsuarioDto> listarCuentasByClienteId(Integer idCliente) {
        return this.client
                .get()
                .uri("listar/{idCliente}", idCliente)
                .retrieve()
                .bodyToFlux(CuentaUsuarioDto.class)
                .doOnNext(cuenta -> log.info("Cuenta obtenida: {}", cuenta))
                .retry(5)
                .timeout(Duration.ofMillis(500))
                .onErrorResume(ex -> {
                    log.error("Error obteniendo cuentas", ex);
                    return Flux.empty();
                });
    }


}
