package com.cfgj.validacion.validacionyverificacionfinal.modelo;

import java.math.*;
import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;

@Entity @Getter @Setter
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    private Date fechaDevolucionReal;

    private BigDecimal multa; // se calcula si hay retraso
}

