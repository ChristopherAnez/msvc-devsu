package com.canez.msvccuentamovimiento.controller;

import com.canez.msvccuentamovimiento.dto.CuentaUsuarioDto;
import com.canez.msvccuentamovimiento.dto.CuentaUsuarioRequest;
import com.canez.msvccuentamovimiento.dto.CuentaUsuarioResponse;
import com.canez.msvccuentamovimiento.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("api/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService service;

    @GetMapping("listar")
    public Flux<CuentaUsuarioDto> listarCuentas() {
        return this.service.listarCuentas();
    }

    @GetMapping("listar/{id}")
    public Flux<CuentaUsuarioDto> listarCuentaByClienteId(@PathVariable Integer id) {
        return this.service.listarCuentaByClienteId(id);
    }

    @GetMapping("listar/cuenta/{numeroCuenta}")
    public Mono<ResponseEntity<CuentaUsuarioDto>> listarCuentaByClienteId(@PathVariable String numeroCuenta) {
        return this.service.listarCuentaByNumeroCuenta(numeroCuenta)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("crear-cuenta")
    public Mono<ResponseEntity<CuentaUsuarioDto>> crearCuenta(@RequestBody Mono<CuentaUsuarioDto> mono) {
        return this.service.crearCuenta(mono)
                .map(dto -> ResponseEntity.status(HttpStatus.CREATED).body(dto))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.BAD_REQUEST).build())
                .onErrorResume(ex -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }

    @DeleteMapping("eliminar/{id}")
    public Mono<ResponseEntity<Object>> eliminarCuenta(@PathVariable Integer id) {
        return this.service.borrarCuentaByIdCuenta(id)
                .then(Mono.defer(() -> Mono.just(ResponseEntity.noContent().build())))
                .defaultIfEmpty(ResponseEntity.badRequest().build())
                .onErrorResume(ex -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }

    @PutMapping("actualizar/{id}")
    public Mono<ResponseEntity<CuentaUsuarioResponse>> actualizarCuenta(@PathVariable Integer id, @RequestBody Mono<CuentaUsuarioRequest> mono) {
        return this.service.actualizarCuentaByIdCuenta(id, mono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorResume(ex -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }


}
