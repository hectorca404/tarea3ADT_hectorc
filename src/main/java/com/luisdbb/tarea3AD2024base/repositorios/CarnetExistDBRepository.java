package com.luisdbb.tarea3AD2024base.repositorios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.luisdbb.tarea3AD2024base.config.EXistDBConnection;

@Repository
public class CarnetExistDBRepository {

	@Autowired
	private EXistDBConnection conexionExistDB;

	public void almacenarCarnet(String nombreSubcoleccion, String nombreRecurso, String contenidoXML) {
		Collection coleccionPadre = conexionExistDB.obtenerConexion();

		try {
			Collection subcoleccion;
			subcoleccion = coleccionPadre.getChildCollection(nombreSubcoleccion);
			Resource recurso = subcoleccion.createResource(nombreRecurso, XMLResource.RESOURCE_TYPE);
			recurso.setContent(contenidoXML);
			subcoleccion.storeResource(recurso);
		} catch (XMLDBException e) {
			System.out.println("Error al guardar el recurso carnet en exist-db");
			e.printStackTrace();
		}

		conexionExistDB.cerrarConexion(coleccionPadre);
	}
}