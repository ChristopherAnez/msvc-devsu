package com.canez.msvcclientepersona.repository;

import com.canez.msvcclientepersona.entity.Persona;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends ReactiveCrudRepository<Persona, Integer> {
}
