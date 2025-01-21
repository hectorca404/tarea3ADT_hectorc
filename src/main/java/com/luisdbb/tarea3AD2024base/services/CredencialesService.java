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
        Credenciales credenciales = credencialesRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Credenciales incorrectas"));

        if (!credenciales.getContrasena().equals(contrasena)) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }

        return credenciales;
    }
}

