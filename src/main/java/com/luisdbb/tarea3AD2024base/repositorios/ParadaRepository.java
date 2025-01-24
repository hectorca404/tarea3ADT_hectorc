package com.luisdbb.tarea3AD2024base.repositorios;

import com.luisdbb.tarea3AD2024base.modelo.Parada;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//ME FLATA IMPLEMENTARLO, LO HARÉ CUANDO VALIDE LAS ENTRADAS DE USUARIO Y CONTORLE EXCEPCIONES
@Repository
public interface ParadaRepository extends JpaRepository<Parada, Long> {
    boolean existsByNombreAndRegion(String nombre, char region);
}
