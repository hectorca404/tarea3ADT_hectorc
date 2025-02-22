package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.repositorios.ParadaRepository;
import com.luisdbb.tarea3AD2024base.repositorios.ServicioRepository;

import jakarta.transaction.Transactional;

@Service
public class ServicioService {
	
	@Autowired
	private ServicioRepository servicioRepository;
	
	@Autowired
	private ParadaService paradaService;
	
	@Autowired
	private ParadaRepository paradaRepository;
	
	@Transactional
	public void añadirServicioParada(Servicio servicio, List<Parada> paradasSeleccionadas) {
		servicioRepository.guardarServicio(servicio);
		
		for(Parada p : paradasSeleccionadas) {
			paradaService.asociarServicioParada(p, servicio);
		}
	}
	
	public List<Servicio> obtenerTodosServicios(){
		return servicioRepository.obtenerTodosServicios();
		
	}
	
	@Transactional
	public void eliminarAsociacionesParadas(Servicio servicio) {
	    for (Parada parada : paradaService.obtenerTodasLasParadas()) {
	        if (parada.getServiciosIds().contains(servicio.getId())) {
	            parada.getServiciosIds().remove(servicio.getId());
	            paradaRepository.save(parada);
	        }
	    }
	}

}
