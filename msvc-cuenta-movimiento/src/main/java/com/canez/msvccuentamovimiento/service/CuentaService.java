package com.canez.msvccuentamovimiento.service;

import com.canez.msvccuentamovimiento.dto.CuentaUsuarioDto;
import com.canez.msvccuentamovimiento.dto.CuentaUsuarioRequest;
import com.canez.msvccuentamovimiento.dto.CuentaUsuarioResponse;
import com.canez.msvccuentamovimiento.repository.CuentaRepository;
import com.canez.msvccuentamovimiento.util.EntityCuentaDtoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CuentaService {

    private static final Logger log = LoggerFactory.getLogger(CuentaService.class);
    @Autowired
    private CuentaRepository repository;

    public Mono<CuentaUsuarioDto> crearCuenta(Mono<CuentaUsuarioDto> mono) {
        return mono
                .map(EntityCuentaDtoUtil::toCuenta)
                .flatMap(repository::save)
                .map(EntityCuentaDtoUtil::toResponseCuenta)
                .onErrorResume(ex -> {
                    log.info("Error al crear la persona ", ex);
                    return Mono.empty();
                });
    }

    public Flux<CuentaUsuarioDto> listarCuentas() {
        return this.repository.findAll()
                .map(EntityCuentaDtoUtil::toResponseCuenta)
                .onErrorResume(ex -> {
                    log.info("Error al listar cuentas ", ex);
                    return Flux.empty();
                });
    }

    public Flux<CuentaUsuarioDto> listarCuentaByClienteId(Integer id) {
        return this.repository.findAllByIdCliente(id)
                .map(EntityCuentaDtoUtil::toResponseCuenta)
                .onErrorResume(ex -> {
                    log.info("Error al listar las cuentas del cliente ", ex);
                    return Flux.empty();
                });
    }

    public Mono<CuentaUsuarioDto> listarCuentaByNumeroCuenta(String numeroCuenta) {
        return this.repository.findByNumeroCuenta(numeroCuenta)
                .map(EntityCuentaDtoUtil::toResponseCuenta)
                .onErrorResume(ex -> {
                    log.info("Error al listar cuentas ", ex);
                    return Mono.empty();
                });
    }

    public Mono<Void> borrarCuentaByIdCuenta(Integer id) {
        return this.repository.findById(id)
                .flatMap(this.repository::delete)
                .onErrorResume(ex -> {
                    log.info("Error al eliminar la cuenta ", ex);
                    return Mono.empty();
                });
    }

    public Mono<CuentaUsuarioResponse> actualizarCuentaByIdCuenta(Integer id, Mono<CuentaUsuarioRequest> mono) {
        return this.repository.findById(id)
                .flatMap(cuenta -> mono.map(EntityCuentaDtoUtil::toCuentaRequest)
                        .doOnNext(uptCuenta -> {
                            uptCuenta.setId(id);
                            uptCuenta.setIdCliente(cuenta.getIdCliente());
                            uptCuenta.setSaldoInicial(cuenta.getSaldoInicial());
                        }))
                .flatMap(repository::save)
                .map(EntityCuentaDtoUtil::toCuentaResponse)
                .onErrorResume(ex -> {
                    log.info("Error al actualizar la cuenta ", ex);
                    return Mono.empty();
                });
    }


}