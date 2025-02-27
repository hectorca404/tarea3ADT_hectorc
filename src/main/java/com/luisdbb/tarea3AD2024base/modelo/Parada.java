package com.luisdbb.tarea3AD2024base.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "paradas")
public class Parada implements Serializable {
	// ATRIBUTOS

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nombre_parada", nullable = false)
	private String nombre;

	@Column(nullable = false)
	private Character region;

	@Column(nullable = false)
	private String responsable;

	// RELACIONES (AUXILIARES)
	@Transient
	private List<Estancia> estancias;

	@Transient
	private List<Peregrino> peregrinos;

	// CONSTRUCTORES
	public Parada() {

	}

	public Parada(Long id, String nombre, char region, String responsable) {
		this.id = id;
		this.nombre = nombre;
		this.region = region;
		this.responsable = responsable;
		this.estancias = new ArrayList<>();
		this.peregrinos = new ArrayList<>();
	}

	// GETTERS Y SETTERS

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public char getRegion() {
		return region;
	}

	public void setRegion(char region) {
		this.region = region;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public List<Estancia> getEstancias() {
		return estancias;
	}

	public void setEstancias(List<Estancia> estancias) {
		this.estancias = estancias;
	}

	public List<Peregrino> getPeregrinos() {
		return peregrinos;
	}

	public void setPeregrinos(List<Peregrino> peregrinos) {
		this.peregrinos = peregrinos;
	}

	// METODOS ENITTY
	@Override
	public String toString() {
		return "Nombre: " + nombre + " Region: " + region;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombre.toLowerCase());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Parada other = (Parada) obj;
		return Objects.equals(nombre.toLowerCase(), other.nombre.toLowerCase());
	}
}
