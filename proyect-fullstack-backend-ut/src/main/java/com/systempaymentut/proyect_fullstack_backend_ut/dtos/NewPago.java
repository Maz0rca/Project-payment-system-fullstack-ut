package com.systempaymentut.proyect_fullstack_backend_ut.dtos;

import java.time.LocalDate;

import com.systempaymentut.proyect_fullstack_backend_ut.enums.TypePago;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewPago {

    private double cantidad;
    private TypePago typePago;
    private LocalDate fecha;

    private String codigoEstudiante;

}
