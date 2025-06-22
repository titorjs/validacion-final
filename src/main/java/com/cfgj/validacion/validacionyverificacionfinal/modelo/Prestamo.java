package com.cfgj.validacion.validacionyverificacionfinal.modelo;

import java.math.*;
import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.calculators.*;

import com.cfgj.validacion.validacionyverificacionfinal.calculadores.*;

import lombok.*;

@View(
  members = "libro, miembro; fechaPrestamo; fechaDevolucionEsperada; fechaDevolucionReal; multa; devuelto; registrar; cobrarMulta"
)
@Tab(properties = "libro.titulo, miembro.nombre, fechaPrestamo, fechaDevolucionEsperada, fechaDevolucionReal, multa, devuelto")
@Entity @Getter @Setter
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @Required
    private Libro libro;

    @ManyToOne @Required
    private Miembro miembro;

    @Required
    @DefaultValueCalculator(CurrentDateCalculator.class)
    private Date fechaPrestamo;

    @Required
    private Date fechaDevolucionEsperada;

    @OnChange(OnChangeCalculoMulta.class)
    private Date fechaDevolucionReal;

    private BigDecimal multa;

    @Column
    private boolean devuelto;
}
