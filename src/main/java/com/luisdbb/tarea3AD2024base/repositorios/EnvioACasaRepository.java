package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.config.ObjectDBConnection;
import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class EnvioACasaRepository {

    private final EntityManager em;

    @Autowired
    public EnvioACasaRepository(ObjectDBConnection connection) {
        this.em = connection.getEntityManager();
    }

    public void guardarEnvio(EnvioACasa envio) {
        em.getTransaction().begin();
        em.persist(envio);
        em.getTransaction().commit();
    }

    public List<EnvioACasa> obtenerTodosEnvios() {
        TypedQuery<EnvioACasa> query = em.createQuery("SELECT e FROM EnvioACasa e", EnvioACasa.class);
        return query.getResultList();
    }
}
