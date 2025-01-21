package com.luisdbb.tarea3AD2024base.services;

import com.luisdbb.tarea3AD2024base.modelo.*;
import com.luisdbb.tarea3AD2024base.repositorios.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeregrinoService {

    private final PeregrinoRepository peregrinoRepository;
    private final ParadasPeregrinosRepository paradasPeregrinosRepository;
    private final CredencialesRepository credencialesRepository;

    public PeregrinoService(PeregrinoRepository peregrinoRepository, ParadasPeregrinosRepository paradasPeregrinosRepository,  CredencialesRepository credencialesRepository) {
        this.peregrinoRepository = peregrinoRepository;
        this.paradasPeregrinosRepository = paradasPeregrinosRepository;
        this.credencialesRepository = credencialesRepository;
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



    @Transactional(readOnly = true)
    public List<Parada> obtenerParadasPorPeregrino(Long peregrinoId) {
        return paradasPeregrinosRepository.findAllByPeregrino_Id(peregrinoId).stream()
                .map(ParadasPeregrinos::getParada)
                .collect(Collectors.toList());
    }
}


