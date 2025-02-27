package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.repositorios.ServicioRepository;

import jakarta.transaction.Transactional;

@Service
public class ServicioService {

	@Autowired
	private ServicioRepository servicioRepository;

	public void guardarServicio(Servicio servicio) {
		servicioRepository.guardarServicio(servicio);
	}

	public List<Servicio> obtenerTodosServicios() {
		return servicioRepository.obtenerTodosServicios();

	}

	@Transactional
	public void eliminarAsociacionesParadas(Servicio servicio) {
		servicio.getParadaIds().clear();
		servicioRepository.guardarServicio(servicio);
	}

	public Long obtenerSiguienteIdServicio() {
		List<Servicio> servicios = obtenerTodosServicios();
		return servicios.stream().mapToLong(Servicio::getId).max().orElse(0) + 1;
	}

}
