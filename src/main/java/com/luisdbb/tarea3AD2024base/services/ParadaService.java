package com.luisdbb.tarea3AD2024base.services;

import com.luisdbb.tarea3AD2024base.modelo.Credenciales;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.ParadasPeregrinos;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Perfil;
import com.luisdbb.tarea3AD2024base.repositorios.CredencialesRepository;
import com.luisdbb.tarea3AD2024base.repositorios.EstanciaRepository;
import com.luisdbb.tarea3AD2024base.repositorios.ParadaRepository;
import com.luisdbb.tarea3AD2024base.repositorios.ParadasPeregrinosRepository;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParadaService {

	private final ParadaRepository paradaRepository;
	private final CredencialesRepository credencialesRepository;
	private final ParadasPeregrinosRepository paradasPeregrinosRepository;
	private final EstanciaRepository estanciaRepository;

	public ParadaService(ParadaRepository paradaRepository, CredencialesRepository credencialesRepository,
			ParadasPeregrinosRepository paradasPeregrinosRepository, EstanciaRepository estanciaRepository) {
		this.paradaRepository = paradaRepository;
		this.credencialesRepository = credencialesRepository;
		this.paradasPeregrinosRepository = paradasPeregrinosRepository;
		this.estanciaRepository = estanciaRepository;
	}

	public List<Parada> obtenerTodasLasParadas() {
		return paradaRepository.findAll();
	}

	@Transactional
	public void registrarParada(String nombreParada, char region, String responsable, String usuario, String correo,
			String contrasena) {
		// CREAR PARADA
		Parada parada = new Parada(null, nombreParada, region, responsable);
		parada = paradaRepository.save(parada);

		// CREAR CREDENCIALES
		Credenciales credenciales = new Credenciales(usuario, contrasena, correo, Perfil.PARADA);
		credenciales.setParada(parada);
		credencialesRepository.save(credenciales);
	}

	public List<Estancia> obtenerEstancias(Long idParada) {
		return estanciaRepository.findByParadaId(idParada);
	}

	public void guardarParadasPeregrinos(ParadasPeregrinos paradasPeregrinos) {
		paradasPeregrinosRepository.save(paradasPeregrinos);
	}
}
