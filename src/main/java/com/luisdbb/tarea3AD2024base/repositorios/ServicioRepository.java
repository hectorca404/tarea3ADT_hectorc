package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;
import com.luisdbb.tarea3AD2024base.config.DB4OConnection;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;

@Repository
public class ServicioRepository {

	private ObjectContainer db = DB4OConnection.obtenerInstancia();

	public void guardarServicio(Servicio servicio) {
		db.store(servicio);
		db.commit();
	}

	public List<Servicio> obtenerTodosServicios() {
	    return (List<Servicio>) db.query(Servicio.class);
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

	public void cerrarConexion() {
		DB4OConnection.cerrarBD();
	}
}
