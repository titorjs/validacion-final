package com.cfgj.validacion.validacionyverificacionfinal.modelo;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;

@Entity @Getter @Setter
public class Miembro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    @Required
    private String nombre;

    @Column(length = 20)
    @Required
    private String identificacion;

    @Column(length = 150)
    private String direccion;

    @Column(length = 15)
    private String telefono;

    @Column(length = 100, unique = true)
    @Required
    private String correo;

    @Column(length = 20)
    @Required
    private String tipoMiembro;

    @Column(length = 10)
    @Required
    private String estado; // Ej: Activo, Inactivo
}

