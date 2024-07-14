package com.canez.msvccuentamovimiento.entity;

import com.canez.msvccuentamovimiento.dto.TipoMovimiento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class Movimientos {

    @Id
    private Integer id;
    private LocalDate fecha;
    @Column("tipomovimiento")
    private TipoMovimiento tipoMovimiento;
    private Integer valor;
    private Integer saldo;
    private String numerocuenta;

}
