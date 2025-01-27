package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;

public interface PeregrinoRepository extends JpaRepository<Peregrino, Long> {
	Optional<Peregrino> findByCarnetId(Long carnetId);
}
