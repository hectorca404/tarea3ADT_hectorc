package com.luisdbb.tarea3AD2024base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

@Component
public class MongoDBConnection {

	@Value("${spring.data.mongodb.uri}")
	private String mongoUri;

	@Value("${spring.data.mongodb.database}")
	private String nombreBaseDatos;

	private MongoClient clienteMongo;

	public MongoClient obtenerConexion() {
		if (clienteMongo == null) {
			clienteMongo = MongoClients.create(mongoUri);
		}
		return clienteMongo;
	}

	public MongoDatabase obtenerBaseDatos() {
		return obtenerConexion().getDatabase(nombreBaseDatos);
	}

	public void cerrarConexion() {
		if (clienteMongo != null) {
			clienteMongo.close();
			clienteMongo = null;
		}
	}

}
