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

	public Collection crearColeccion(String nombreColeccion) throws XMLDBException {
		Collection coleccionPadre = conexionExistDB.obtenerConexion();

		CollectionManagementService servicioGestion = (CollectionManagementService) coleccionPadre
				.getService("CollectionManagementService", "1.0");

		Collection nuevaColeccion = servicioGestion.createCollection(nombreColeccion);

		conexionExistDB.cerrarConexion(coleccionPadre);

		return nuevaColeccion;
	}
}
