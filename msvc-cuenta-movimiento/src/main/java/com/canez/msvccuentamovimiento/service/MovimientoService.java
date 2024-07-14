package com.canez.msvccuentamovimiento.service;

import com.canez.msvccuentamovimiento.dto.CuentaMovimientoDto;
import com.canez.msvccuentamovimiento.dto.CuentaMovimientoRequest;
import com.canez.msvccuentamovimiento.dto.OperacionDto;
import com.canez.msvccuentamovimiento.repository.CuentaRepository;
import com.canez.msvccuentamovimiento.repository.MovimientoRepository;
import com.canez.msvccuentamovimiento.util.EntityMovimientoDtoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
public class MovimientoService {

    private static final Logger log = LoggerFactory.getLogger(CuentaService.class);

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    public Flux<CuentaMovimientoDto> listarMovimientos() {
        return movimientoRepository.findAll()
                .map(EntityMovimientoDtoUtil::toDto)
                .onErrorResume(ex -> {
                    log.info("Error al listar los movimientos", ex);
                    return Flux.empty();
                });
    }

    public Mono<CuentaMovimientoDto> listarMovimientoById(Integer id) {
        return this.movimientoRepository.findById(id)
                .map(EntityMovimientoDtoUtil::toDto)
                .onErrorResume(ex -> {
                    log.info("Error al listar los movimientos por ID", ex);
                    return Mono.empty();
                });
    }

    public Flux<CuentaMovimientoDto> listarMovimientosByCuentaId(String numeroCuenta) {
        return this.movimientoRepository.findBynumerocuenta(numeroCuenta)
                .map(EntityMovimientoDtoUtil::toDto)
                .onErrorResume(ex -> {
                    log.info("Error al listar los movimientos por Cuenta", ex);
                    return Flux.empty();
                });
    }

    public Flux<CuentaMovimientoDto> listarMovimientosByFechaCuenta(LocalDate fechaInicial, LocalDate fechaFinal, String numeroCuenta) {
        return this.movimientoRepository.findByFechaAndCuenta(fechaInicial, fechaFinal, numeroCuenta)
                .map(EntityMovimientoDtoUtil::toDto)
                .onErrorResume(ex -> {
                    log.info("Error al listar los movimientos por Fecha/Cuenta", ex);
                    return Flux.empty();
                });
    }

    public Mono<CuentaMovimientoDto> crearMovimiento(Mono<OperacionDto> request) {
        return request
                .map(EntityMovimientoDtoUtil::toMovimientosOperacion)
                .flatMap(movimiento -> cuentaRepository.findByNumeroCuenta(movimiento.getNumerocuenta())
                        .flatMap(cuenta -> {
                            SaldoValidor.validarSaldo(cuenta.getSaldoDisponible(), movimiento.getValor(), movimiento.getTipoMovimiento());
                            var nuevoSaldo = SaldoValidor.calcularNuevoSaldo(cuenta.getSaldoDisponible(), movimiento.getValor(), movimiento.getTipoMovimiento());
                            cuenta.setSaldoDisponible(nuevoSaldo);
                            movimiento.setSaldo(nuevoSaldo);
                            return cuentaRepository.save(cuenta)
                                    .then(movimientoRepository.save(movimiento))
                                    .map(EntityMovimientoDtoUtil::toDto);
                        }))
                .onErrorResume(RuntimeException.class, e -> {
                    if ("SALDO_NO_DISPONIBLE".equals(e.getMessage())) {
                        return Mono.error(new RuntimeException("SALDO_NO_DISPONIBLE"));
                    }
                    return Mono.error(e);
                });
    }

    public Mono<Void> eliminarMovimiento(Integer id) {
        return this.movimientoRepository.findById(id)
                .flatMap(this.movimientoRepository::delete)
                .onErrorResume(ex -> {
                    log.info("Error al eliminar movimiento ", ex);
                    return Mono.empty();
                });
    }

    public Mono<CuentaMovimientoDto> actualizarMovimiento(Integer id, Mono<CuentaMovimientoRequest> mono) {
        return this.movimientoRepository.findById(id)
                .flatMap(movimientos -> mono.map(EntityMovimientoDtoUtil::toMovimientosRequest)
                        .doOnNext(updMovimiento -> {
                            updMovimiento.setId(id);
                        }))
                .flatMap(this.movimientoRepository::save)
                .map(EntityMovimientoDtoUtil::toDto)
                .onErrorResume(ex -> {
                    log.info("Error al actualizar el movimiento ", ex);
                    return Mono.empty();
                });
    }


}
