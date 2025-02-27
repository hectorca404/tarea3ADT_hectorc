package com.luisdbb.tarea3AD2024base.modelo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//OBJECTDB
@Entity
public class EnvioACasa extends Servicio implements Serializable {
	// ATRIBUTOS
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private double peso;
	private int[] volumen;
	private boolean urgente;

	private static final String NOMBRE_ENVIO = "Envio a Casa";
	private static final double PRECIO_ENVIO = 10.0;

	// RELACION
	@Embedded
	private Direccion direccion;

	// CONSTRUCTORES
	public EnvioACasa(Long id) {
		super(id, NOMBRE_ENVIO, PRECIO_ENVIO);
	}

	public EnvioACasa(Long id, double peso, int[] volumen, boolean urgente, Direccion direccion) {
		super(id, NOMBRE_ENVIO, PRECIO_ENVIO);
		this.peso = peso;
		this.volumen = volumen;
		this.urgente = urgente;
		this.direccion = direccion;
	}

	// GETTERS AND SETTERS
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
				+ urgente + ", direccion=" + direccion + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(volumen);
		result = prime * result + Objects.hash(direccion, id, peso, urgente);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnvioACasa other = (EnvioACasa) obj;
		return Objects.equals(direccion, other.direccion) && Objects.equals(id, other.id)
				&& Double.doubleToLongBits(peso) == Double.doubleToLongBits(other.peso) && urgente == other.urgente
				&& Arrays.equals(volumen, other.volumen);
	}

}
