package com.luisdbb.tarea3AD2024base.services;

import com.luisdbb.tarea3AD2024base.modelo.Credenciales;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Perfil;
import com.luisdbb.tarea3AD2024base.repositorios.CredencialesRepository;
import com.luisdbb.tarea3AD2024base.repositorios.ParadaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParadaService {

    private final ParadaRepository paradaRepository;
    private final CredencialesRepository credencialesRepository;

    public ParadaService(ParadaRepository paradaRepository, CredencialesRepository credencialesRepository) {
        this.paradaRepository = paradaRepository;
        this.credencialesRepository = credencialesRepository;
    }

    @Transactional
    public void registrarParada(String nombreParada, char region, String responsable, String usuario,String correo, String contrasena) {
        //CREAR PARADA
        Parada parada = new Parada(null, nombreParada, region, responsable);
        parada = paradaRepository.save(parada);

        //CREAR CREDENCIALES
        Credenciales credenciales = new Credenciales(usuario, contrasena, correo, Perfil.PARADA);
        credenciales.setParada(parada);
        credencialesRepository.save(credenciales);
    }
}
