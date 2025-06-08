package com.cfgj.validacion.validacionyverificacionfinal.modelo;

import java.math.*;
import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.openxava.annotations.*;
import org.openxava.calculators.*;
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
    @DefaultValueCalculator(CurrentDateCalculator.class)
    @FutureOrPresent(message="La fecha de préstamo no puede ser anterior a hoy")
    private Date fechaPrestamo;

    @Required
    private Date fechaDevolucionEsperada;

    @OnChange(OnChangeCalculoMulta.class)
    private Date fechaDevolucionReal;

    @Stereotype("MONEY")
    @ReadOnly
    private BigDecimal multa;

    @AssertTrue(message="La fecha de devolución esperada debe ser igual o posterior a la fecha de préstamo")
    public boolean isFechaEsperadaValida() {
        if (fechaPrestamo == null || fechaDevolucionEsperada == null) return true;
        return !fechaDevolucionEsperada.before(fechaPrestamo);
    }

    @AssertTrue(message="La fecha de devolución real debe ser igual o posterior a la fecha de préstamo")
    public boolean isFechaRealValida() {
        if (fechaPrestamo == null || fechaDevolucionReal == null) return true;
        return !fechaDevolucionReal.before(fechaPrestamo);
    }
}
