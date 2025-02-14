package com.luisdbb.tarea3AD2024base.services;

import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;

@Service
public class SesionService {

	private Peregrino peregrinoActual;
	private Parada paradaActual;

	public SesionService() {

	}

	public void setPeregrinoActual(Peregrino peregrino) {
		this.peregrinoActual = peregrino;
	}

	public Peregrino getPeregrinoActual() {
		return peregrinoActual;
	}

	public void setParadaActual(Parada parada) {
		this.paradaActual = parada;
	}

	public Parada getParadaActual() {
		return paradaActual;
	}

}
