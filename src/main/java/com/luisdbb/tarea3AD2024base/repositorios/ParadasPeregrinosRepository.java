package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luisdbb.tarea3AD2024base.modelo.ParadasPeregrinos;

public interface ParadasPeregrinosRepository extends JpaRepository<ParadasPeregrinos, Long> {
    List<ParadasPeregrinos> findAllByPeregrino_Id(Long peregrinoId);
}
