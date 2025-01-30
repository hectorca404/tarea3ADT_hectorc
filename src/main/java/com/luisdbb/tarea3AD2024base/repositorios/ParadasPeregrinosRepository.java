package com.luisdbb.tarea3AD2024base.repositorios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.ParadasPeregrinos;
import com.luisdbb.tarea3AD2024base.modelo.ParadasPeregrinosId;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;

@Repository
public interface ParadasPeregrinosRepository extends JpaRepository<ParadasPeregrinos, Long> {
    List<ParadasPeregrinos> findByPeregrinoId(Long peregrinoId);
    
    boolean existsById(ParadasPeregrinosId id);
   
    
}
