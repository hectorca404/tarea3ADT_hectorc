package com.luisdbb.tarea3AD2024base.modelo;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

//OBJECTDB
@Entity
public class Direccion implements Serializable{
	@Id
	@GeneratedValue
	private Long id;
	private String direccion;
	private String localidad;

	// CONTRUCTORES

	public Direccion() {

	}

	public Direccion(Long id, String direccion, String localidad) {
		super();
		this.id = id;
		this.direccion = direccion;
		this.localidad = localidad;
	}

	// GETTERS AND SETTERS

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	// METODOS CLASS ENTITY
	@Override
	public String toString() {
		return "Direccion [id=" + id + ", direccion=" + direccion + ", localidad=" + localidad + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(direccion, id, localidad);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Direccion other = (Direccion) obj;
		return Objects.equals(direccion, other.direccion) && Objects.equals(id, other.id)
				&& Objects.equals(localidad, other.localidad);
	}

}
