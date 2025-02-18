package com.luisdbb.tarea3AD2024base.config;

import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@Component
public class ObjectDBConnection {
	private static ObjectDBConnection instance;
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;

	@Value("${objectdb.path}")
	private String dbPath;

	private ObjectDBConnection() {
	}

	public static ObjectDBConnection getInstance() {
		if (instance == null) {
			instance = new ObjectDBConnection();
		}
		return instance;
	}

	public void open() {
		if (entityManagerFactory == null || !entityManagerFactory.isOpen()) {
			Map<String, String> properties = new HashMap<>();
			properties.put("javax.persistence.jdbc.url", "objectdb:" + dbPath);
			properties.put("javax.persistence.jdbc.driver", "com.objectdb.jpa.Driver");
			properties.put("javax.persistence.schema-generation.database.action", "create");

			entityManagerFactory = Persistence.createEntityManagerFactory("objectdb:" + dbPath, properties);
		}
		if (entityManager == null || !entityManager.isOpen()) {
			entityManager = entityManagerFactory.createEntityManager();
		}
	}

	public EntityManager getEntityManager() {
		if (entityManager == null || !entityManager.isOpen()) {
			open();
		}
		return entityManager;
	}

	public void close() {
		if (entityManager != null && entityManager.isOpen()) {
			entityManager.close();
		}
		if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
			entityManagerFactory.close();
		}
	}
}