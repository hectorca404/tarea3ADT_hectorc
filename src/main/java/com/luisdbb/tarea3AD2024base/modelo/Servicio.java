package com.luisdbb.tarea3AD2024base.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//DB4O

public class Servicio implements Serializable {
	// ATRIBUTOS
	private Long id;
	private String nombre;
	private double precio;

	private List<Long> paradaIds = new ArrayList<>();

	// CONTRUSTORES

	public Servicio() {
	}

	public Servicio(Long id, String nombre, double precio) {
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.paradaIds = new ArrayList<>();
	}

	// GETTERS AND SETTER

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

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public List<Long> getParadaIds() {
		return paradaIds;
	}

	public void setParadaIds(List<Long> paradaIds) {
		this.paradaIds = paradaIds;
	}

	public void agregarParada(Long paradaId) {
		if (!paradaIds.contains(paradaId)) {
			paradaIds.add(paradaId);
		}
	}

	public void eliminarParada(Long paradaId) {
		paradaIds.remove(paradaId);
	}

	// METODOS CLASS ENTITY

	@Override
	public String toString() {
		return nombre;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nombre, precio);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Servicio other = (Servicio) obj;
		return Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre)
				&& Double.doubleToLongBits(precio) == Double.doubleToLongBits(other.precio);
	}

}
