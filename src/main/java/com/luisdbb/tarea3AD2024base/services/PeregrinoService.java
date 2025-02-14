package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Credenciales;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.ParadasPeregrinos;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Perfil;
import com.luisdbb.tarea3AD2024base.repositorios.CredencialesRepository;
import com.luisdbb.tarea3AD2024base.repositorios.EstanciaRepository;
import com.luisdbb.tarea3AD2024base.repositorios.ParadasPeregrinosRepository;
import com.luisdbb.tarea3AD2024base.repositorios.PeregrinoRepository;

@Service
public class PeregrinoService {

	private final PeregrinoRepository peregrinoRepository;
	private final ParadasPeregrinosRepository paradasPeregrinosRepository;
	private final CredencialesRepository credencialesRepository;
	private final EstanciaRepository estanciaRepository;

	public PeregrinoService(PeregrinoRepository peregrinoRepository,
			ParadasPeregrinosRepository paradasPeregrinosRepository, CredencialesRepository credencialesRepository,
			EstanciaRepository estanciaRepository) {
		this.peregrinoRepository = peregrinoRepository;
		this.paradasPeregrinosRepository = paradasPeregrinosRepository;
		this.credencialesRepository = credencialesRepository;
		this.estanciaRepository = estanciaRepository;

	}

	@Transactional
	public Peregrino registrarPeregrino(String nombreUsuario, String contrasena, String correo, String nombre,
			String apellido, String nacionalidad, Parada paradaInicio) {
		if (credencialesRepository.findByNombreUsuario(nombreUsuario).isPresent()) {
			throw new IllegalArgumentException("El nombre de usuario ya existe");
		}

		Peregrino peregrino = new Peregrino(null, nombre, apellido, nacionalidad, paradaInicio);

		Peregrino peregrinoGuardado = peregrinoRepository.save(peregrino);

		ParadasPeregrinos relacion = new ParadasPeregrinos(peregrinoGuardado, paradaInicio);
		paradasPeregrinosRepository.save(relacion);

		Credenciales credenciales = new Credenciales();
		credenciales.setNombreUsuario(nombreUsuario);
		credenciales.setContrasena(contrasena);
		credenciales.setCorreo(correo);
		credenciales.setPerfil(Perfil.PEREGRINO);
		credenciales.setPeregrino(peregrinoGuardado);

		credencialesRepository.save(credenciales);

		return peregrinoGuardado;
	}

	public List<Parada> obtenerParadas(Long peregrinoId) {
		return paradasPeregrinosRepository.findByPeregrinoId(peregrinoId).stream().map(ParadasPeregrinos::getParada)
				.toList();
	}

	public List<Estancia> obtenerEstancias(Long peregrinoId) {
		return estanciaRepository.findByPeregrinoId(peregrinoId);
	}

	@Transactional
	public void guardarCambiosPeregrino(Peregrino peregrino, String nuevoCorreo) {
		peregrinoRepository.save(peregrino);

		Credenciales credenciales = credencialesRepository.findByPeregrino(peregrino).orElseThrow(
				() -> new IllegalArgumentException("No se encontraron credenciales asociadas al peregrino"));

		credenciales.setCorreo(nuevoCorreo);

		credencialesRepository.save(credenciales);
	}

	@Transactional
	public void actualizarCarnet(Carnet carnet) {
		Peregrino peregrino = peregrinoRepository.findByCarnetId(carnet.getId())
				.orElseThrow(() -> new IllegalArgumentException(
						"No se encontró un peregrino asociado al carnet con ID: " + carnet.getId()));

		peregrino.getCarnet().setDistancia(carnet.getDistancia());
		peregrino.getCarnet().setnVips(carnet.getnVips());

		peregrinoRepository.save(peregrino);
	}

}
