package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.db4o.ObjectContainer;
import com.luisdbb.tarea3AD2024base.config.DB4OConnection;
import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;

@Repository
public class ServicioRepository {

	private ObjectContainer db = DB4OConnection.obtenerInstancia();

	public void guardarServicio(Servicio servicio) {
		db.store(servicio);
		db.commit();
	}

	public List<Servicio> obtenerTodosServicios() {
		List<Servicio> servicios = db.query(Servicio.class);
		List<Servicio> serviciosFiltrados = new ArrayList<>();

		for (Servicio servicio : servicios) {
			if (!(servicio instanceof EnvioACasa)) {
				serviciosFiltrados.add(servicio);
			}
		}

		return serviciosFiltrados;
	}

	public List<Servicio> obtenerServiciosPorParada(Long paradaId) {
		List<Servicio> todosServicios = obtenerTodosServicios();
		List<Servicio> serviciosFiltrados = new ArrayList<>();

		for (Servicio servicio : todosServicios) {
			if (servicio.getParadaIds().contains(paradaId)) {
				serviciosFiltrados.add(servicio);
			}
		}

		return serviciosFiltrados;
	}

	public Servicio buscarPorNombre(String nombre) {
		for (Servicio servicio : db.query(Servicio.class)) {
			if (servicio.getNombre().equals(nombre)) {
				return servicio;
			}
		}
		return null;
	}

	public boolean existeServicio(String nombre) {
		return buscarPorNombre(nombre) != null;
	}

	public void eliminarServicio(Servicio servicio) {
		db.delete(servicio);
		db.commit();
	}

}
