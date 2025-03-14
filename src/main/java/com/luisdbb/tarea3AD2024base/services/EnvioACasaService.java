package com.luisdbb.tarea3AD2024base.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.ConjuntoContratado;
import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.repositorios.ConjuntoContratadoRepository;
import com.luisdbb.tarea3AD2024base.repositorios.EnvioACasaRepository;
import com.luisdbb.tarea3AD2024base.repositorios.EstanciaRepository;

@Service
public class EnvioACasaService {

	@Autowired
	private EnvioACasaRepository envioACasaRepository;
	@Autowired
	private ConjuntoContratadoRepository conjuntoContratadoRepository;

	@Autowired
	private EstanciaRepository estanciaRepository;

	public EnvioACasaService(EnvioACasaRepository envioACasaRepository) {
		this.envioACasaRepository = envioACasaRepository;
	}

	public void guardarEnvio(EnvioACasa envio) {
		envioACasaRepository.guardarEnvio(envio);
	}

	public List<EnvioACasa> obtenerEnviosPorParada(Parada parada) {
		List<Estancia> estancias = estanciaRepository.findByParadaId(parada.getId());
		if (estancias.isEmpty()) {
			return new ArrayList<>();
		}

		List<Long> idsEstancia = new ArrayList<>();
	    for (Estancia estancia : estancias) {
	        idsEstancia.add(estancia.getId());
	    }
		List<ConjuntoContratado> conjuntos = conjuntoContratadoRepository.obtenerConjuntosPorIdsEstancia(idsEstancia);

		List<EnvioACasa> enviosEncontrados = new ArrayList<>();
		for (ConjuntoContratado cc : conjuntos) {
			for (Servicio s : cc.getServicios()) {
				if (s instanceof EnvioACasa) {
					enviosEncontrados.add((EnvioACasa) s);
				}
			}
		}

		return enviosEncontrados;
	}

	public Peregrino obtenerPeregrinoDeEnvio(EnvioACasa envio) {
		List<Estancia> estancias = estanciaRepository.findAll();

		List<Long> idsEstancia = estancias.stream().map(Estancia::getId).toList();
		List<ConjuntoContratado> conjuntos = conjuntoContratadoRepository.obtenerConjuntosPorIdsEstancia(idsEstancia);

		for (ConjuntoContratado cc : conjuntos) {
			if (cc.getServicios().contains(envio)) {
				for (Estancia estancia : estancias) {
					if (estancia.getId().equals(cc.getIdEstancia())) {
						return estancia.getPeregrino();
					}
				}
			}
		}
		return null;
	}

}
