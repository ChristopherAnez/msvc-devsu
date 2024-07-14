package com.canez.msvcagregador.dto;

import com.canez.msvcagregador.dto.cuentadto.TipoCuenta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class CrearClienteCuentaRequest {

    private String nombre;
    private String genero;
    private Integer edad;
    private String identificacion;
    private String direccion;
    private String telefono;
    private String contrasena;
    private Boolean estado;
    private String numeroCuenta;
    private TipoCuenta tipoCuenta;
    private Integer saldoInicial;
    private Integer saldoDisponible;

}
