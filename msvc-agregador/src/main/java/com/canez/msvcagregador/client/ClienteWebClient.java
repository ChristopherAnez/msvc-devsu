package com.canez.msvcagregador.client;

import com.canez.msvcagregador.dto.clientedto.ClientePersonaDto;
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
public class ClienteWebClient {

    private static final Logger log = LoggerFactory.getLogger(ClienteWebClient.class);

    private final WebClient client;


    public ClienteWebClient(@Value("${msvc.cliente.service}") String baseUrl) {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @CircuitBreaker(name = "breaker-generico-service")
    public Flux<ClientePersonaDto> listarClientes() {
        return this.client
                .get()
                .uri("/listar")
                .retrieve()
                .bodyToFlux(ClientePersonaDto.class)
                .retry(5)
                .timeout(Duration.ofMillis(500))
                .onErrorResume(ex -> Mono.empty());
    }

    @CircuitBreaker(name = "breaker-generico-service")
    public Mono<ClientePersonaDto> listarById(Integer id) {
        return this.client
                .get()
                .uri("/listar/{id}", id)
                .retrieve()
                .bodyToMono(ClientePersonaDto.class)
                .doOnNext(cliente -> log.info("Cliente obtenido: {}", cliente))
                .retry(5)
                .timeout(Duration.ofMillis(500))

                .onErrorResume(ex -> {
                    log.error("Error obteniendo cliente", ex);
                    return Mono.empty();
                });
    }

    @CircuitBreaker(name = "breaker-generico-service")
    public Mono<ClientePersonaDto> crearCliente(Mono<ClientePersonaDto> clienteDtoMono) {
        return this.client
                .post()
                .uri("/crear")
                .body(clienteDtoMono, ClienteWebClient.class)
                .retrieve()
                .bodyToMono(ClientePersonaDto.class)
                .retry(5)
                .timeout(Duration.ofMillis(500))
                .onErrorResume(ex -> Mono.empty());
    }

}
