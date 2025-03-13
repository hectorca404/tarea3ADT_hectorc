package com.luisdbb.tarea3AD2024base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;

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

			if (coleccion == null) {
				Collection raiz = DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc/db", usuario,
						contrasena);
				CollectionManagementService mgtService = (CollectionManagementService) raiz
						.getService("CollectionManagementService", "1.0");
				mgtService.createCollection(url.substring(url.lastIndexOf("/") + 1));
				coleccion = DatabaseManager.getCollection(url, usuario, contrasena);
			}

		} catch (Exception e) {
			System.out.println("Error al establecer la conexion eXist-db: " + e.getMessage());
			e.printStackTrace();
		}
		return coleccion;
	}

	public void cerrarConexion(Collection coleccion) {
		if (coleccion != null) {
			try {
				coleccion.close();
			} catch (XMLDBException e) {
				System.out.println("Error al cerrar la conexion eXist-db: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public String getUrl() {
		return url;
	}

	public String getUsuario() {
		return usuario;
	}

	public String getContrasena() {
		return contrasena;
	}
}
