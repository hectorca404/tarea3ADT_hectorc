package com.luisdbb.tarea3AD2024base.repositorios;

import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.Parada;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository
public interface EstanciaRepository extends JpaRepository<Estancia, Long> {
	List<Estancia> findByPeregrinoId(Long peregrinoId);

	List<Estancia> findByParadaId(Long paradaId);
}
