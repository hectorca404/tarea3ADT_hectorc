package com.luisdbb.tarea3AD2024base.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;

import com.luisdbb.tarea3AD2024base.repositorios.ColeccionParadaRepository;

@Service
public class ColeccionParadaService {

	@Autowired
	private ColeccionParadaRepository coleccionParadaRepository;

	public boolean crearColeccionParada(String nombreColeccion) {
		try {
			Collection nuevaColeccion = coleccionParadaRepository.crearColeccion(nombreColeccion);
			if (nuevaColeccion != null) {
				nuevaColeccion.close();
				return true;
			}
		} catch (XMLDBException e) {
			e.printStackTrace();
		}
		return false;
	}
}
