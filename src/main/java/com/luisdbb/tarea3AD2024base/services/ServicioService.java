package com.luisdbb.tarea3AD2024base.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.repositorios.ServicioRepository;

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

	public List<Servicio> obtenerServiciosPorParada(Long paradaId) {
		List<Servicio> todosLosServicios = obtenerTodosServicios();
		List<Servicio> serviciosFiltrados = new ArrayList<>();

		for (Servicio servicio : todosLosServicios) {
			if (servicio.getParadaIds().contains(paradaId)) {
				serviciosFiltrados.add(servicio);
			}
		}
		return serviciosFiltrados;
	}

	public Long obtenerSiguienteIdServicio() {
		List<Servicio> servicios = obtenerTodosServicios();
		return servicios.stream().mapToLong(Servicio::getId).max().orElse(0) + 1;
	}

	public boolean existeServicio(String nombre) {
		List<Servicio> servicios = servicioRepository.obtenerTodosServicios();
		for (Servicio s : servicios) {
			if (nombre.equalsIgnoreCase(s.getNombre())) {
				return true;
			}
		}
		return false;
	}

}
