package com.luisdbb.tarea3AD2024base.modelo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "paradas_peregrinos")
public class ParadasPeregrinos implements Serializable {
	
	// ATRBUTOS
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "peregrino_id", nullable = false)
    private Peregrino peregrino;

    @ManyToOne
    @JoinColumn(name = "parada_id", nullable = false)
    private Parada parada;

    // CONSTRUCTORES
    public ParadasPeregrinos() {
    	
    }

    public ParadasPeregrinos(Peregrino peregrino, Parada parada) {
        this.peregrino = peregrino;
        this.parada = parada;
    }

    // GETTERS Y SETTERS
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Peregrino getPeregrino() {
        return peregrino;
    }

    public void setPeregrino(Peregrino peregrino) {
        this.peregrino = peregrino;
    }

    public Parada getParada() {
        return parada;
    }

    public void setParada(Parada parada) {
        this.parada = parada;
    }
    
    // METODOS ENTITY
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParadasPeregrinos that = (ParadasPeregrinos) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ParadasPeregrinos{" +
                "id=" + id +
                ", peregrino="+peregrino.getId() +
                ", parada=" + parada.getId() +
                '}';
    }
}

