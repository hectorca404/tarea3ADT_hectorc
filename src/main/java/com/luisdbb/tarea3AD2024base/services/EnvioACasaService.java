package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;
import com.luisdbb.tarea3AD2024base.repositorios.EnvioACasaRepository;

@Service
public class EnvioACasaService {
	
	@Autowired
	private EnvioACasaRepository envioACasaRepository;
	
	public void guardarEnvio(EnvioACasa envio) {
		envioACasaRepository.guardarEnvio(envio);
	}
	
	public List<EnvioACasa> obtenerTodosEnvios(){
		return envioACasaRepository.obtenerTodosEnvios();
	}
}
