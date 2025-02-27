package com.luisdbb.tarea3AD2024base.repositorios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;

@Repository
public interface EstanciaRepository extends JpaRepository<Estancia, Long> {
	List<Estancia> findByPeregrinoId(Long peregrinoId);

	List<Estancia> findByParadaId(Long paradaId);

	boolean existsByPeregrinoAndParadaAndFecha(Peregrino peregrino, Parada parada, LocalDate fecha);
	
}
