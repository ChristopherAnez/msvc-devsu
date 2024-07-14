package com.canez.msvccuentamovimiento.entity;

import com.canez.msvccuentamovimiento.dto.TipoCuenta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@Table("cuenta")
public class Cuenta {

    @Id
    @Column("id_cuenta")
    private Integer id;
    @Column("id_cliente")
    private Integer idCliente;
    @Column("numerocuenta")
    private String numeroCuenta;
    @Column("tipo")
    private TipoCuenta tipoCuenta;
    @Column("saldoinicial")
    private Integer saldoInicial;
    @Column("saldodisponible")
    private Integer saldoDisponible;
    @Column("estado")
    private Boolean estado;

}
