package com.luisdbb.tarea3AD2024base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@Component
public class ObjectDBConnection {

	@Value("${objectdb.datasource.url}")
	private String db_url;

	private static ObjectDBConnection INSTANCE;
	private EntityManagerFactory emf;
	private EntityManager em;

	private ObjectDBConnection() {

	}

	@PostConstruct
	private void init() {
		open();
	}

	public static synchronized ObjectDBConnection getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ObjectDBConnection();
		}
		return INSTANCE;
	}

	private void open() {
		if (emf == null || !emf.isOpen()) {
			emf = Persistence.createEntityManagerFactory(db_url);
		}
		if (em == null || !em.isOpen()) {
			em = emf.createEntityManager();
		}
	}

	public EntityManager getEntityManager() {
		if (em == null || !em.isOpen()) {
			open();
		}
		return em;
	}

	@PreDestroy

	public void cerrarBD() {
		if (em != null && em.isOpen()) {
			em.close();
		}
		if (emf != null && emf.isOpen()) {
			emf.close();
		}
	}
}