package com.canez.msvcclientepersona.repository;

import com.canez.msvcclientepersona.entity.Cliente;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ClienteRepository extends ReactiveCrudRepository<Cliente, Integer> {

    Mono<Cliente> findByPersonaId(Integer personaId);
}
