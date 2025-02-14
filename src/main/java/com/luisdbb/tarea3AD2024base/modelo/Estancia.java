package com.luisdbb.tarea3AD2024base.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "estancias")
public class Estancia implements Serializable {
	// ATRIBUTOS
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private LocalDate fecha;

	@Column(nullable = false)
	private boolean vip;

	// RELACIONES

	@ManyToOne
	@JoinColumn(name = "parada_id", nullable = false)
	private Parada parada;

	@ManyToOne
	@JoinColumn(name = "peregrino_id", nullable = false)
	private Peregrino peregrino;

	// CONSTRUCTORES

	public Estancia() {
		this.fecha = LocalDate.now();
	}

	public Estancia(Long id, boolean vip, Parada parada, Peregrino peregrino) {
		this.id = id;
		this.fecha = LocalDate.now();
		this.vip = vip;
		this.parada = parada;
		this.peregrino = peregrino;
	}

	public Estancia(Long id, boolean vip, Parada parada) {
		this.id = id;
		this.fecha = LocalDate.now();
		this.vip = vip;
		this.parada = parada;
	}

	// GETTERS Y SETTERS

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public boolean isVip() {
		return vip;
	}

	public void setVip(boolean vip) {
		this.vip = vip;
	}

	public Parada getParada() {
		return parada;
	}

	public void setParada(Parada parada) {
		this.parada = parada;
	}

	public Peregrino getPeregrino() {
		return peregrino;
	}

	public void setPeregrino(Peregrino peregrino) {
		this.peregrino = peregrino;
	}

	// METODOS ENTITY
	@Override
	public String toString() {
		return "Estancia ID: " + id + "\nFecha: " + fecha + "\nEs VIP: " + (vip ? "Si" : "No") + "\nParada: "
				+ parada.getId() + "\nPeregrino ID: " + peregrino.getId();
	}

	@Override
	public int hashCode() {
		return Objects.hash(fecha, id, parada, peregrino, vip);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Estancia other = (Estancia) obj;
		return Objects.equals(fecha, other.fecha) && Objects.equals(id, other.id)
				&& Objects.equals(parada, other.parada) && Objects.equals(peregrino, other.peregrino)
				&& vip == other.vip;
	}
}
