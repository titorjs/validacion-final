package com.cfgj.validacion.validacionyverificacionfinal.modelo;


import javax.persistence.*;

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
    private String isbn;

    @Column(length = 50)
    private String editorial;

    @Column(length = 30)
    private String genero;

    @Column
    @Required
    private int numeroCopias;
}