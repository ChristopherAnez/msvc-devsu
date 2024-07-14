package com.canez.msvccuentamovimiento.repository;

import com.canez.msvccuentamovimiento.entity.Cuenta;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CuentaRepository extends ReactiveCrudRepository<Cuenta, Integer> {

    Flux<Cuenta> findAllByIdCliente(Integer id_cliente);

    Mono<Cuenta> findByNumeroCuenta(String numeroCuenta);

}
