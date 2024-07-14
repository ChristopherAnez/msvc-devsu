package com.canez.msvccuentamovimiento.controller;

import com.canez.msvccuentamovimiento.dto.CuentaMovimientoDto;
import com.canez.msvccuentamovimiento.dto.CuentaMovimientoRequest;
import com.canez.msvccuentamovimiento.dto.ErrorResponse;
import com.canez.msvccuentamovimiento.dto.OperacionDto;
import com.canez.msvccuentamovimiento.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.time.LocalDate;

@RestController
@RequestMapping("api/movimientos")
public class MovimientosController {

    @Autowired
    private MovimientoService service;

    @GetMapping("listar")
    public Flux<CuentaMovimientoDto> listarMovimientos() {
        return this.service.listarMovimientos();
    }

    @GetMapping("listar/{id}")
    public Mono<ResponseEntity<CuentaMovimientoDto>> listarMovimientosById(@PathVariable Integer id) {
        return this.service.listarMovimientoById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("listar/mov-cuenta/{id}")
    public Flux<CuentaMovimientoDto> listarMovimientosByCuentaId(@PathVariable String id) {
        return this.service.listarMovimientosByCuentaId(id);
    }

    @GetMapping("listar/mov-cuenta")
    public Flux<CuentaMovimientoDto> listarMovimientosByFechaCuenta(
            @RequestParam LocalDate fechaIni,
            @RequestParam LocalDate fechaFin,
            @RequestParam String numeroCuenta) {
        return this.service.listarMovimientosByFechaCuenta(fechaIni, fechaFin, numeroCuenta);
    }

    @PostMapping("crear-movimiento")
    public Mono<ResponseEntity<?>> crearMovimiento(@RequestBody Mono<OperacionDto> mono) {
        return this.service.crearMovimiento(mono)
                .<ResponseEntity<?>>map(dto -> ResponseEntity.status(HttpStatus.CREATED).body(dto))
                .onErrorResume(RuntimeException.class, ex -> {
                    if ("SALDO_NO_DISPONIBLE".equals(ex.getMessage())) {
                        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("El saldo no es suficiente para soportar la operación")));
                    }
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error interno del servidor")));
                });
    }


    @DeleteMapping("eliminar/{id}")
    public Mono<ResponseEntity<Void>> eliminarMovimiento(@PathVariable Integer id) {
        return this.service.eliminarMovimiento(id)
                .then(Mono.defer(() -> Mono.just(ResponseEntity.noContent().<Void>build())))
                .defaultIfEmpty(ResponseEntity.badRequest().build())
                .onErrorResume(ex -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }

    @PutMapping("actualizar/{id}")
    public Mono<ResponseEntity<CuentaMovimientoDto>> actualizarMovimiento(@PathVariable Integer id, @RequestBody Mono<CuentaMovimientoRequest> mono) {
        return this.service.actualizarMovimiento(id, mono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorResume(ex -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }

}
