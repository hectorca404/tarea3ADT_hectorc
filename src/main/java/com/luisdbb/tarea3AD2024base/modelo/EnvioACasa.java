package com.luisdbb.tarea3AD2024base.modelo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

//OBJECTDB

@Entity
public class EnvioACasa implements Serializable{
	// ATRIBUTOS
	@Id
	@GeneratedValue
	private Long id;
	private double peso;
	private int[] volumen;
	private boolean urgente;
	
	//RELACIONES
	private Long idParada;

	@OneToOne(cascade = CascadeType.ALL)
	private Direccion direccion;

	// CONSTRUCTORES
	public EnvioACasa() {
	}

	public EnvioACasa(Long id, double peso, int[] volumen, boolean urgente, Long idParada, Direccion direccion) {
		this.id = id;
		this.peso = peso;
		this.volumen = volumen;
		this.urgente = urgente;
		this.idParada = idParada;
		this.direccion = direccion;
	}

	// GETTERS AND SETTERS

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public int[] getVolumen() {
		return volumen;
	}

	public void setVolumen(int[] volumen) {
		this.volumen = volumen;
	}

	public boolean isUrgente() {
		return urgente;
	}

	public void setUrgente(boolean urgente) {
		this.urgente = urgente;
	}

	public Long getIdParada() {
		return idParada;
	}

	public void setIdParada(Long idParada) {
		this.idParada = idParada;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	// METODOS CLASS ENTITY

	@Override
	public String toString() {
		return "EnvioACasa [id=" + id + ", peso=" + peso + ", volumen=" + Arrays.toString(volumen) + ", urgente="
				+ urgente + ", idParada=" + idParada + ", direccion=" + direccion + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(volumen);
		result = prime * result + Objects.hash(direccion, id, idParada, peso, urgente);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnvioACasa other = (EnvioACasa) obj;
		return Objects.equals(direccion, other.direccion) && Objects.equals(id, other.id)
				&& Objects.equals(idParada, other.idParada)
				&& Double.doubleToLongBits(peso) == Double.doubleToLongBits(other.peso) && urgente == other.urgente
				&& Arrays.equals(volumen, other.volumen);
	}

}
