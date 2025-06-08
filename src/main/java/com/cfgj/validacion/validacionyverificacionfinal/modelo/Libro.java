package com.cfgj.validacion.validacionyverificacionfinal.modelo;


import javax.persistence.*;
import javax.validation.constraints.*;

import org.openxava.annotations.*;

import lombok.*;

@Entity @Getter @Setter
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    @Required
    private String titulo;

    @Column(length = 100)
    @Required
    private String autor;

    @Column(length = 20, unique = true)
    @Required
    @Pattern(regexp="\\d{3}-\\d{10}")
    private String isbn;

    @Column(length = 50)
    private String editorial;

    public enum Genero { FICCION, NO_FICCION, TECNICO, INFANTIL }
    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private Genero genero;

    @Column
    @Required 
    @Min(0) 
    @Max(1000)
    private int numeroCopias;
}