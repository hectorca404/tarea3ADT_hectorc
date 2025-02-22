package com.luisdbb.tarea3AD2024base.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
//DB40

public class ConjuntoContratado implements Serializable {
	private Long id;
	private double precioTotal;
	private char modoPago;
	private String extra;

	// RELACION SERVICIOS
	private List<Servicio> servicios;
	private Long idEstancia;

	public ConjuntoContratado() {
		this.servicios = new ArrayList<>();
	}

	public ConjuntoContratado(Long id, double precioTotal, char modoPago, String extra, Long idEstancia) {
		this.id = id;
		this.precioTotal = precioTotal;
		this.modoPago = modoPago;
		this.extra = extra;
		this.idEstancia = idEstancia;
		this.servicios = new ArrayList<>();
	}

	// GETTERS AND SETTERS

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
	}

	public char getModoPago() {
		return modoPago;
	}

	public void setModoPago(char modoPago) {
		this.modoPago = modoPago;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public List<Servicio> getServicios() {
		return servicios;
	}

	public void setServicios(List<Servicio> servicios) {
		this.servicios = servicios;
	}

	public Long getIdEstancia() {
		return idEstancia;
	}

	public void setIdEstancia(Long idEstancia) {
		this.idEstancia = idEstancia;
	}

	// METODOS AUXILIARES
	public void agregarServicio(Servicio servicio) {
		if (!servicios.contains(servicio)) {
			servicios.add(servicio);
		}
	}

	public void eliminarServicio(Servicio servicio) {
		servicios.remove(servicio);
	}

	// METODOS CLASS ENTITY

	@Override
	public String toString() {
		return "ConjuntoContratado [id=" + id + ", precioTotal=" + precioTotal + ", modoPago=" + modoPago + ", extra="
				+ extra + ", servicios=" + servicios + ", idEstancia=" + idEstancia + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(extra, id, idEstancia, modoPago, precioTotal, servicios);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConjuntoContratado other = (ConjuntoContratado) obj;
		return Objects.equals(extra, other.extra) && Objects.equals(id, other.id)
				&& Objects.equals(idEstancia, other.idEstancia) && modoPago == other.modoPago
				&& Double.doubleToLongBits(precioTotal) == Double.doubleToLongBits(other.precioTotal)
				&& Objects.equals(servicios, other.servicios);
	}

}
