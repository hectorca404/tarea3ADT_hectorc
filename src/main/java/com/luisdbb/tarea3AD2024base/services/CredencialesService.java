package com.luisdbb.tarea3AD2024base.services;

import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Credenciales;
import com.luisdbb.tarea3AD2024base.modelo.Perfil;
import com.luisdbb.tarea3AD2024base.repositorios.CredencialesRepository;

@Service
public class CredencialesService {

    private CredencialesRepository credencialesRepository;

    public CredencialesService(CredencialesRepository credencialesRepository) {
        this.credencialesRepository = credencialesRepository;
    }

    public Credenciales crearAdministrador() {
        if (credencialesRepository.findByNombreUsuario("admin").isEmpty()) {
            Credenciales admin = new Credenciales("admin", "1234", "admin@gmail.com", Perfil.ADMINISTRADOR);
            return credencialesRepository.save(admin);
        }
        return null;
    }

    public Credenciales validarCredenciales(String nombreUsuario, String contrasena) {
        return credencialesRepository.findByNombreUsuario(nombreUsuario)
                .filter(credenciales -> credenciales.getContrasena().equals(contrasena))
                .orElseThrow(() -> new IllegalArgumentException("Credeciales incorrectas"));
    }
}

