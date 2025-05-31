package com.cfgj.validacion.validacionyverificacionfinal.modelo;

import java.math.*;

import javax.persistence.*;

import lombok.*;

@Entity @Getter @Setter
public class ParametroSistema {

    @Id
    @Column(length = 50)
    private String clave; // Ejemplo: "VALOR_MULTA_DIA"

    @Column(length = 200)
    private String descripcion;

    @Column
    private BigDecimal valorNumerico;

    @Column(length = 200)
    private String valorTexto;
}

