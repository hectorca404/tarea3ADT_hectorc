package com.luisdbb.tarea3AD2024base.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Credenciales;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Perfil;
import com.luisdbb.tarea3AD2024base.repositorios.CredencialesRepository;

@Service
public class CredencialesService {

	private CredencialesRepository credencialesRepository;

	public CredencialesService(CredencialesRepository credencialesRepository) {
		this.credencialesRepository = credencialesRepository;
	}

	public Credenciales obtenerCredencialesPeregrino(Peregrino peregrino) {
		return credencialesRepository.findByPeregrino(peregrino)
				.orElseThrow(() -> new IllegalArgumentException("No se encontraron credenciales parada el peregrino"));
	}

	public List<Credenciales> obtenerUsuarios(Perfil perfil) {
		return credencialesRepository.findByPerfil(perfil);
	}

	public void guardarCredenciales(Credenciales credenciales) {
		credencialesRepository.save(credenciales);
	}

	public Optional<Credenciales> obtenerCredencialPorUsuario(String nombreUsuario) {
		return credencialesRepository.findByNombreUsuario(nombreUsuario);
	}

}
