package com.canez.msvcagregador.controller;

import com.canez.msvcagregador.dto.clientedto.ClientesResponse;
import com.canez.msvcagregador.dto.CrearClienteCuentaRequest;
import com.canez.msvcagregador.dto.CrearCuentaRequest;
import com.canez.msvcagregador.dto.CuentaClienteResponse;
import com.canez.msvcagregador.service.ClienteCuentaOrquestadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/cuentas-clientes/")
public class CuentaClienteController {


    @Autowired
    private ClienteCuentaOrquestadorService service;

    @GetMapping("listar")
    public Flux<ClientesResponse> listarClientes() {
        return this.service.listarClientes();
    }

    @PostMapping("crear/cliente")
    public Mono<CuentaClienteResponse> crearClienteCuenta(@RequestBody Mono<CrearClienteCuentaRequest> requestMono) {
        return this.service.creaCuentaCliente(requestMono);
    }

    @PostMapping("crear/cuenta")
    public Mono<CuentaClienteResponse> crearCuentaByIdCliente(@RequestBody Mono<CrearCuentaRequest> requestMono) {
        return this.service.crearCuentaClienteByIdCliente(requestMono);
    }

}


