package com.luisdbb.tarea3AD2024base.repositorios;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.config.MongoDBConnection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Repository
public class MongoDBRepository {

	@Autowired
	private MongoDBConnection mongoDBConnection;

	public void guardarBackup(String nombreColeccion, Document documento) {
		MongoDatabase baseDatos = mongoDBConnection.obtenerBaseDatos();
		MongoCollection<Document> coleccion = baseDatos.getCollection(nombreColeccion);
		coleccion.insertOne(documento);
	}
}