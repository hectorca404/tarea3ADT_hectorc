package com.luisdbb.tarea3AD2024base.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ObjectDBConnection {
    private static ObjectDBConnection INSTANCE;
    private EntityManagerFactory emf;
    private EntityManager em;

    private static final String DB_PATH = "databases/database.odb";

    private ObjectDBConnection() {
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
            emf = Persistence.createEntityManagerFactory("objectdb:" + DB_PATH);
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

    public void cerrarBD() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
