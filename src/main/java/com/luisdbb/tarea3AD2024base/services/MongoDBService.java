package com.luisdbb.tarea3AD2024base.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.repositorios.MongoDBRepository;

@Service
public class MongoDBService {

	@Autowired
	private MongoDBRepository mongoDBRepository;

	@Autowired
	private PeregrinoService peregrinoService;

	public void exportarCarnets() {
		List<Peregrino> peregrinos = peregrinoService.obtenerPeregrinos();
		String contenidoXML = "";

		for (Peregrino peregrino : peregrinos) {
			contenidoXML += peregrinoService.generarXMLCarnet(peregrino);
		}

		String nombreFichero = generarNombreDocumento();

		Document documento = new Document("_id", nombreFichero).append("Carnets", contenidoXML.toString());

		mongoDBRepository.guardarBackup("carnetsPeregrinos", documento);
	}

	private String generarNombreDocumento() {
		LocalDateTime hoy = LocalDateTime.now();
		DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		return "backupcarnets_" + hoy.format(formateador);
	}
}