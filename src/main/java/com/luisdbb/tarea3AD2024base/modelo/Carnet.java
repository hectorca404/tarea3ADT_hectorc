package com.luisdbb.tarea3AD2024base.modelo;

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
@Table(name = "carnets")
public class Carnet {
	// ATRIBUTOS
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "fecha_expedicion", nullable = false)
	private LocalDate fechaexp;

	@Column(nullable = false)
	private double distancia;

	@Column(name = "numero_vips", nullable = false)
	private int nVips;

	@ManyToOne
	@JoinColumn(name = "parada_inicio_id", nullable = false)
	private Parada paradaInicio;

	// CONSTRUCTORES

	public Carnet() {

	}

	public Carnet(Long id, Parada paradaInicio) {
		this.id = id;
		this.paradaInicio = paradaInicio;
		this.distancia = 0.0;
		this.nVips = 0;
		this.fechaexp = LocalDate.now();
	}

	// GETTERS Y SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFechaexp() {
		return fechaexp;
	}

	public void setFechaexp(LocalDate fechaexp) {
		this.fechaexp = fechaexp;
	}

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

	public int getnVips() {
		return nVips;
	}

	public void setnVips(int nVips) {
		this.nVips = nVips;
	}

	public Parada getParadaInicio() {
		return paradaInicio;
	}

	public void setParadaInicio(Parada paradaInicio) {
		this.paradaInicio = paradaInicio;
	}

	// METODOS
	public void incrementarNumeroEstanciasVip() {
		this.nVips++;
	}

	// METODOS ENTITY
	@Override
	public String toString() {
		return "\nCarnet " + "ID: " + id + "\nFecha Expedición: " + fechaexp + "\nDistancia: " + distancia
				+ "\nNúmero de Estancias VIP: " + nVips + "\nParada de Inicio: " + paradaInicio.getNombre();
	}

	@Override
	public int hashCode() {
		return Objects.hash(distancia, fechaexp, id, nVips, paradaInicio);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Carnet other = (Carnet) obj;
		return Double.doubleToLongBits(distancia) == Double.doubleToLongBits(other.distancia)
				&& Objects.equals(fechaexp, other.fechaexp) && Objects.equals(id, other.id) && nVips == other.nVips
				&& Objects.equals(paradaInicio, other.paradaInicio);
	}
}
