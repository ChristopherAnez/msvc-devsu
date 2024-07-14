package com.canez.msvcclientepersona.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class Cliente {

    @Id
    private Integer clienteId;
    private Integer personaId;
    private String contrasena;
    private Boolean estado;
}
