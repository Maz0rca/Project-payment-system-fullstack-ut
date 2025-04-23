package com.systempaymentut.proyect_fullstack_backend_ut.entities;

import java.time.LocalDate;

import com.systempaymentut.proyect_fullstack_backend_ut.enums.PagoStatus;
import com.systempaymentut.proyect_fullstack_backend_ut.enums.TypePago;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder  //permitir construir objetos de esta clase con el patron builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDate fecha;
    private double cantidad;
    private TypePago type;
    private PagoStatus status;

    private String file;


    //relacion en la bd (base de datos)
    @ManyToOne
    private Estudiante estudiante;


}
