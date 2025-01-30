package com.luisdbb.tarea3AD2024base.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Scanner;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luisdbb.tarea3AD2024base.modelo.Credenciales;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Perfil;
import com.luisdbb.tarea3AD2024base.repositorios.CredencialesRepository;
import com.luisdbb.tarea3AD2024base.view.AlertsView;

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
