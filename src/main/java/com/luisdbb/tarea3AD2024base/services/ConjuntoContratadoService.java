package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.ConjuntoContratado;
import com.luisdbb.tarea3AD2024base.repositorios.ConjuntoContratadoRepository;

@Service
public class ConjuntoContratadoService {
	@Autowired
	private ConjuntoContratadoRepository conjuntoContratadoRepository;

	public void guardarConjunto(ConjuntoContratado conjunto) {
		conjuntoContratadoRepository.guardarConjunto(conjunto);
	}

	public List<ConjuntoContratado> obtenerTodosConjuntos() {
		return conjuntoContratadoRepository.obtenerTodosConjuntos();

	}

	public Long obtenerSiguienteId() {
		List<ConjuntoContratado> conjuntos = obtenerTodosConjuntos();
		return (long) (conjuntos.size() + 1);
	}

}
