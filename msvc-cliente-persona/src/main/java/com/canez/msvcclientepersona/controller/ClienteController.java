package com.canez.msvcclientepersona.controller;

import com.canez.msvcclientepersona.dto.ClientePersonaDto;
import com.canez.msvcclientepersona.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/cliente")

public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping("listar")
    public Flux<ClientePersonaDto> listar() {
        return this.service.getAllClients();
    }

    @GetMapping("listar/{id}")
    public Mono<ResponseEntity<ClientePersonaDto>> listarById(@PathVariable Integer id) {
        return this.service.getClientByPersonaId(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("crear")
    public Mono<ResponseEntity<ClientePersonaDto>> crearUsuario(@RequestBody Mono<ClientePersonaDto> mono) {
        return this.service.create(mono)
                .map(dto -> ResponseEntity.status(HttpStatus.CREATED).body(dto))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.BAD_REQUEST).build())
                .onErrorResume(ex -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }

    @DeleteMapping("eliminar/{id}")
    public Mono<ResponseEntity<Object>> eliminarCliente(@PathVariable Integer id) {
        return this.service.deleteClientByPersonaId(id)
                .then(Mono.just(ResponseEntity.noContent().build()))
                .defaultIfEmpty(ResponseEntity.badRequest().build())
                .onErrorResume(ex -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }

    @PutMapping("actualizar/{id}")
    public Mono<ResponseEntity<ClientePersonaDto>> actualizarCliente(@PathVariable Integer id, @RequestBody Mono<ClientePersonaDto> mono) {
        return this.service.updateClientByPersonId(id, mono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorResume(ex -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }


}
