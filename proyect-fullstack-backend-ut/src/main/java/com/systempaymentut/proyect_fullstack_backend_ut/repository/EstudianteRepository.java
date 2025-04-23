package com.systempaymentut.proyect_fullstack_backend_ut.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.systempaymentut.proyect_fullstack_backend_ut.entities.Estudiante;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, String> {

    // metodo personalizado para buscar un estudiante en especifico
    Estudiante findByCodigo(String codigo);

    // lista de los estudiantes pertenecientes a un programa en especifico
    List<Estudiante> findByProgramaId(String programaId);

}
