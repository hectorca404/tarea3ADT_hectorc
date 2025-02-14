package com.luisdbb.tarea3AD2024base.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class ParadasPeregrinosId implements Serializable {

	private LocalDate fecha;
	private Long peregrino;
	private Long parada;

	// CONTRUCTORES
	public ParadasPeregrinosId() {
	}

	public ParadasPeregrinosId(Long peregrino, Long parada) {
		this.fecha = LocalDate.now();
		this.peregrino = peregrino;
		this.parada = parada;
	}

	// GETTERS Y SETTERS
	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Long getPeregrino() {
		return peregrino;
	}

	public void setPeregrino(Long peregrino) {
		this.peregrino = peregrino;
	}

	public Long getParada() {
		return parada;
	}

	public void setParada(Long parada) {
		this.parada = parada;
	}

	// METODOS CLASE ENTITY

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ParadasPeregrinosId that = (ParadasPeregrinosId) o;
		return Objects.equals(fecha, that.fecha) && Objects.equals(peregrino, that.peregrino)
				&& Objects.equals(parada, that.parada);
	}

	@Override
	public int hashCode() {
		return Objects.hash(fecha, peregrino, parada);
	}
}
