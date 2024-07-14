package com.canez.msvcagregador.dto.movimientodto;

import com.canez.msvcagregador.dto.CuentaReporteDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class EstadoCuentaReporteDto {

    private Integer clienteId;
    private String nombreCliente;
    private List<CuentaReporteDto> cuentas;

}
