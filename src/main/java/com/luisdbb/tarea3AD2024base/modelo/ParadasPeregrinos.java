package com.luisdbb.tarea3AD2024base.modelo;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "paradas_peregrinos")
public class ParadasPeregrinos implements Serializable {

	@EmbeddedId
	private ParadasPeregrinosId id;

	@MapsId("peregrino")
	@ManyToOne
	@JoinColumn(name = "peregrino_id", nullable = false)
	private Peregrino peregrino;

	@MapsId("parada")
	@ManyToOne
	@JoinColumn(name = "parada_id", nullable = false)
	private Parada parada;

	// CONSTRUCTORES
	public ParadasPeregrinos() {
		this.id = new ParadasPeregrinosId();
	}

	public ParadasPeregrinos(Peregrino peregrino, Parada parada) {
		this.id = new ParadasPeregrinosId(peregrino.getId(), parada.getId());
		this.peregrino = peregrino;
		this.parada = parada;
	}

	// GETTERS Y SETTERS
	public ParadasPeregrinosId getId() {
		return id;
	}

	public void setId(ParadasPeregrinosId id) {
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

	public LocalDate getFecha() {
		return id.getFecha();
	}

	public void setFecha(LocalDate fecha) {
		this.id.setFecha(fecha);
	}

	// METODOS ENTITY

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ParadasPeregrinos that = (ParadasPeregrinos) o;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		return "ParadasPeregrinos{" + "id=" + id + ", peregrino=" + peregrino.getId() + ", parada=" + parada.getId()
				+ '}';
	}
}
