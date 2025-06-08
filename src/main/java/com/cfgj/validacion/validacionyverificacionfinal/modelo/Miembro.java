package com.cfgj.validacion.validacionyverificacionfinal.modelo;

import javax.persistence.*;
import javax.validation.constraints.*;

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
    @Pattern(regexp = "\\d{7,15}")
    private String telefono;

    @Column(length = 100, unique = true)
    @Required
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    private String correo;

    public enum Tipo { ESTUDIANTE, DOCENTE, INVESTIGADOR, ADMINISTRADOR }
    @Enumerated(EnumType.STRING)
    @Required
    private Tipo tipoMiembro;

    public enum Estado { ACTIVO, INACTIVO }
    @Enumerated(EnumType.STRING)
    @Required
    private Estado estado;
}

