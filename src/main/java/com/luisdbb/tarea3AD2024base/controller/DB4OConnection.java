package com.luisdbb.tarea3AD2024base.controller;

import org.springframework.stereotype.Component;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;

import jakarta.annotation.PreDestroy;

@Component
public class DB4OConnection {

	private static DB4OConnection INSTANCE = null;
	private final String PATH = "databases/db.db4o";
	private static ObjectContainer db;

	private DB4OConnection() {

	}

	private synchronized static void crearInstancia() {
		if (INSTANCE == null) {
			INSTANCE = new DB4OConnection();
			INSTANCE.performConnection();
		}
	}

	public void performConnection() {
		EmbeddedConfiguration configuracion = Db4oEmbedded.newConfiguration();
		db = Db4oEmbedded.openFile(configuracion, PATH);
	}

	public static ObjectContainer obtenerInstancia() {
		if (INSTANCE == null)
			crearInstancia();
		return db;
	}

	@PreDestroy
	public static void cerrarBD() {
		try {
			if (db != null) {
				db.close();
				db = null;
			}

		} catch (Exception e) {
			System.out.println("Error al cerrar la BD");
		}
	}
}