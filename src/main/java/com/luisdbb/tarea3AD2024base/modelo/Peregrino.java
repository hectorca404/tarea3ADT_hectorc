package com.luisdbb.tarea3AD2024base.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "peregrinos")
public class Peregrino implements Serializable {
	// ATRIBUTOS

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nombre;

	@Column(nullable = false)
	private String apellido;

	@Column(nullable = false)
	private String nacionalidad;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "carnet_id", nullable = false)
	private Carnet carnet;

	// RELACIONES (AUXILIARES)
	@Transient
	private List<Parada> paradas;

	@Transient
	private List<Estancia> estancias;

	// CONSTRUCTORES

	public Peregrino() {

	}

	public Peregrino(Long id, String nombre, String apellido, String nacionalidad, Parada paradaInicio) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.nacionalidad = nacionalidad;
		this.carnet = new Carnet(id, paradaInicio);
		this.paradas = new ArrayList<>();
		this.estancias = new ArrayList<>();
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

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public Carnet getCarnet() {
		return carnet;
	}

	public void setCarnet(Carnet carnet) {
		this.carnet = carnet;
	}

	public List<Parada> getParadas() {
		return paradas;
	}

	public void setParadas(List<Parada> paradas) {
		this.paradas = paradas;
	}

	public List<Estancia> getEstancias() {
		return estancias;
	}

	public void setEstancias(List<Estancia> estancias) {
		this.estancias = estancias;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	// METODOS
	public void agregarParada(Parada parada) {
		paradas.add(parada);
	}

	public void agregarEstancia(Estancia estancia) {
		estancias.add(estancia);
	}

	// METODOS ENTITY
	@Override
	public String toString() {
		return "Peregrino:" + "\nID: " + id + "\nNombre: " + nombre + "\nNacionalidad: " + nacionalidad + "\nCarnet: "
				+ carnet.toString() + "\nParadas: " + paradas.size() + "\nEstancias: " + estancias.size();
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nombre, nacionalidad);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Peregrino other = (Peregrino) obj;
		return Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(nacionalidad, other.nacionalidad);
	}
}
