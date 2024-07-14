package com.canez.msvccuentamovimiento.repository;

import com.canez.msvccuentamovimiento.entity.Movimientos;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Repository
public interface MovimientoRepository extends R2dbcRepository<Movimientos, Integer> {

    Flux<Movimientos> findBynumerocuenta(String numeroCuenta);

    @Query("SELECT * FROM Movimientos WHERE fecha BETWEEN :fechaInicial AND :fechaFinal AND numerocuenta = :numerocuenta")
    Flux<Movimientos> findByFechaAndCuenta(
            @Param("fechaInicial") LocalDate fechaInicial,
            @Param("fechaFinal") LocalDate fechaFinal,
            @Param("numerocuenta") String numerocuenta);

}
