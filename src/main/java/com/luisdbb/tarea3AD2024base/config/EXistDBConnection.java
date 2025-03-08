package com.luisdbb.tarea3AD2024base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;

@Component
public class EXistDBConnection {

	@Value("${existdb.uri}")
	private String url;

	@Value("${existdb.usuario}")
	private String usuario;

	@Value("${existdb.contrasena}")
	private String contrasena;

	public Collection obtenerConexion() {
		Collection coleccion = null;
		try {
			Class<?> clase = Class.forName("org.exist.xmldb.DatabaseImpl");
			Database baseDeDatos = (Database) clase.getDeclaredConstructor().newInstance();
			baseDeDatos.setProperty("create-database", "true");
			DatabaseManager.registerDatabase(baseDeDatos);

			coleccion = DatabaseManager.getCollection(url, usuario, contrasena);
		} catch (ClassNotFoundException | XMLDBException | InstantiationException | IllegalAccessException
				| NoSuchMethodException | java.lang.reflect.InvocationTargetException e) {
			System.out.println("Error al establecer la coenxion con eXistDB");
			e.printStackTrace();
		}
		return coleccion;
	}

	public void cerrarConexion(Collection coleccion) {
		if (coleccion != null) {
			try {
				coleccion.close();
			} catch (XMLDBException e) {
				System.out.print("Error al cerrar la conexion: ");
				e.printStackTrace();
			}
		}
	}
}
