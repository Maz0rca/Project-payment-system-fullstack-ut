package com.systempaymentut.proyect_fullstack_backend_ut.entities;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder  //permitir construir objetos de esta clase con el patron builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estudiante {

    //Modificadores de acceso, existen: public, private, protected
    //Metodos accesores - get (obtener) - set (establecer)

    @Id
    private String id;

    private String nombre;
    private String apellido;

    @Column(unique=true)
    private String codigo;

    private String programaId;

    private String foto;




}
