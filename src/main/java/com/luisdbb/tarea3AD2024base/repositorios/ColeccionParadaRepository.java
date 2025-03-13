package com.luisdbb.tarea3AD2024base.repositorios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;

import com.luisdbb.tarea3AD2024base.config.EXistDBConnection;

@Repository
public class ColeccionParadaRepository {

	@Autowired
	private EXistDBConnection conexionExistDB;

	public Collection crearColeccion(String nombreColeccion) {
		Collection coleccionPadre = conexionExistDB.obtenerConexion();
		Collection nuevaColeccion = null;

		try {
			CollectionManagementService mgtService = (CollectionManagementService) coleccionPadre
					.getService("CollectionManagementService", "1.0");

			nuevaColeccion = mgtService.createCollection(nombreColeccion);
		} catch (XMLDBException e) {
			System.out.println("Error al crear la coleccion de la parada");
			e.printStackTrace();
		} finally {
			conexionExistDB.cerrarConexion(coleccionPadre);
		}

		return nuevaColeccion;
	}
}
