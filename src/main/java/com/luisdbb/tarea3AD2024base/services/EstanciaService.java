package com.luisdbb.tarea3AD2024base.services;

import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.repositorios.EstanciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EstanciaService {

	@Autowired
	private EstanciaRepository estanciaRepository;

	public void guardarEstancia(Estancia estancia) {
		estanciaRepository.save(estancia);
	}
}
