package com.canez.msvcagregador.service;

import com.canez.msvcagregador.client.ClienteWebClient;
import com.canez.msvcagregador.client.CuentaWebClient;
import com.canez.msvcagregador.dto.*;
import com.canez.msvcagregador.dto.clientedto.ClientePersonaDto;
import com.canez.msvcagregador.dto.clientedto.ClientesResponse;
import com.canez.msvcagregador.dto.cuentadto.CuentaUsuarioDto;
import com.canez.msvcagregador.util.EntityDtoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClienteCuentaOrquestadorService {

    private static final Logger log = LoggerFactory.getLogger(ClienteCuentaOrquestadorService.class);
    private final ClienteWebClient clienteWebClient;
    private final CuentaWebClient cuentaWebClient;

    @Autowired
    public ClienteCuentaOrquestadorService(ClienteWebClient clienteWebClient, CuentaWebClient cuentaWebClient) {
        this.clienteWebClient = clienteWebClient;
        this.cuentaWebClient = cuentaWebClient;
    }

    public Flux<ClientesResponse> listarClientes() {
        return this.clienteWebClient.listarClientes()
                .map(EntityDtoUtil::toClienteResponse);
    }

    public Mono<CuentaClienteResponse> crearCuentaClienteByIdCliente(Mono<CrearCuentaRequest> mono) {
        return mono
                .flatMap(crearCuentaRequest ->
                        clienteWebClient.listarById(crearCuentaRequest.getIdCliente())
                                .switchIfEmpty(Mono.error(new RuntimeException("Cliente no encontrado")))
                                .flatMap(cliente -> {
                                    CuentaUsuarioDto cuentaUsuarioDto = EntityDtoUtil.toCuentaNueva(crearCuentaRequest);
                                    cuentaUsuarioDto.setId_cliente(cliente.getClienteId());
                                    return cuentaWebClient.crearCuenta(Mono.just(cuentaUsuarioDto))
                                            .map(response -> EntityDtoUtil.toResponse(cuentaUsuarioDto, cliente));
                                })
                )
                .onErrorResume(ex -> {
                    log.info("Error generando la cuenta: " + ex.getMessage());
                    return Mono.empty();
                });
    }

    public Mono<CuentaClienteResponse> creaCuentaCliente(Mono<CrearClienteCuentaRequest> mono) {
        return mono
                .flatMap(request -> {
                    ClientePersonaDto clientePersonaDto = EntityDtoUtil.toClientePersonaDto(request);
                    CuentaUsuarioDto cuentaUsuarioDto = EntityDtoUtil.toCuentaUsuarioDto(request);
                    return clienteWebClient.crearCliente(Mono.just(clientePersonaDto))
                            .switchIfEmpty(Mono.error(new RuntimeException("Error creando el cliente")))
                            .flatMap(cliente -> {
                                cuentaUsuarioDto.setId_cliente(cliente.getClienteId());
                                return cuentaWebClient.crearCuenta(Mono.just(cuentaUsuarioDto))
                                        .map(response -> EntityDtoUtil.toResponse(cuentaUsuarioDto, clientePersonaDto));
                            });
                })
                .onErrorResume(ex -> {
                    log.info("Error generando la cuenta: " + ex.getMessage());
                    return Mono.empty();
                });
    }

}
