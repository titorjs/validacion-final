package com.cfgj.validacion.validacionyverificacionfinal.modelo;

import java.math.*;
import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.model.*;

import com.cfgj.validacion.validacionyverificacionfinal.calculadores.*;

import lombok.*;

@Entity
@Getter
@Setter
public class Prestamo extends Identifiable {

    @ManyToOne
    @Required
    private Miembro miembro;

    @ManyToOne
    @Required
    private Libro libro;

    @Required
    private Date fechaPrestamo;

    @Required
    private Date fechaDevolucionEsperada;

    @OnChange(OnChangeCalculoMulta.class)
    private Date fechaDevolucionReal;

    @Stereotype("MONEY")
    @ReadOnly
    private BigDecimal multa;

}
