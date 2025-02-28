package com.luisdbb.tarea3AD2024base.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Credenciales;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.ParadasPeregrinosId;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Perfil;
import com.luisdbb.tarea3AD2024base.repositorios.CredencialesRepository;
import com.luisdbb.tarea3AD2024base.repositorios.EstanciaRepository;
import com.luisdbb.tarea3AD2024base.repositorios.ParadaRepository;
import com.luisdbb.tarea3AD2024base.repositorios.ParadasPeregrinosRepository;
import com.luisdbb.tarea3AD2024base.view.AlertsView;

@Service
public class ValidacionesService {

	private final CredencialesRepository credencialesRepository;
	private final ParadasPeregrinosRepository paradasPeregrinosRepository;
	private final AlertsView alertsView;
	private final ParadaRepository paradaRepository;
	private final EstanciaRepository estanciaRepository;

	public ValidacionesService(CredencialesRepository credencialesRepository, AlertsView alertsView,
			ParadasPeregrinosRepository paradasPeregrinosRepository, ParadaRepository paradaRepository,
			EstanciaRepository estanciaRepository) {
		this.credencialesRepository = credencialesRepository;
		this.alertsView = alertsView;
		this.paradasPeregrinosRepository = paradasPeregrinosRepository;
		this.paradaRepository = paradaRepository;
		this.estanciaRepository = estanciaRepository;
	}

	// PRINCIPAL

	public Credenciales validarCredenciales(String nombreUsuario, String password) {
		Properties properties = new Properties();
		try (FileInputStream fis = new FileInputStream("src/main/resources/application.properties")) {
			properties.load(fis);
		} catch (IOException e) {
			System.out.println("Error al acceder al fichero: " + e.getMessage());
			return null;
		}

		String usuarioAdmin = properties.getProperty("usuarioadmin");
		String passAdmin = properties.getProperty("passadmin");

		if (usuarioAdmin != null && usuarioAdmin.equals(nombreUsuario)) {
			if (passAdmin != null && passAdmin.equals(password)) {
				return new Credenciales(nombreUsuario, password, Perfil.ADMINISTRADOR);
			} else {
				alertsView.mostrarError("Error", "Contraseña incorrecta.");
				return null;
			}
		}

		Optional<Credenciales> credencialesOptional = credencialesRepository.findByNombreUsuario(nombreUsuario);

		if (credencialesOptional.isEmpty()) {
			alertsView.mostrarError("Error", "Usuario no encontrado.");
			return null;
		}

		Credenciales credenciales = credencialesOptional.get();

		if (!credenciales.getContrasena().equals(password)) {
			alertsView.mostrarError("Error", "Contraseña incorrecta.");
			return null;
		}

		return credenciales;
	}

	// REG PEREGRINO

	public boolean existeUsuario(String nombreUsuario) {
		if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
			alertsView.mostrarError("Error", "El nombre de usuario no puede estar vacio.");
			return false;
		}

		boolean existe = credencialesRepository.existsByNombreUsuario(nombreUsuario);

		if (existe) {
			alertsView.mostrarError("Error", "El usuario '" + nombreUsuario + "' ya existe.");
			return true;
		}

		return false;
	}

	public boolean validarNombreUsuario(String nombreUsuario) {
		if (nombreUsuario == null) {
			alertsView.mostrarError("Error", "El nombre de usuario no puede estar vacio.");
			return false;
		}
		if (nombreUsuario.contains(" ")) {
			alertsView.mostrarError("Error", "El nombre de usuario no puede contener espacios.");
			return false;
		}
		return true;
	}

	public boolean validarCorreo(String correo) {
		if (correo == null || correo.trim().isEmpty()) {
			alertsView.mostrarError("Error", "El correo no puede estar vacío.");
			return false;
		}

		if (correo.contains(" ")) {
			alertsView.mostrarError("Error", "El correo no puede tener espacios en blanco.");
			return false;
		}

		String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

		if (!correo.matches(regex)) {
			alertsView.mostrarError("Error", "El correo debe ser válido y sin caracteres especiales en el usuario.");
			return false;
		}

		return true;
	}

	public boolean validarSinEspacios(String entrada) {
		if (entrada == null) {
			alertsView.mostrarError("Error", "La constraseña no puede estar vacia.");
			return false;
		}
		if (entrada.contains(" ")) {
			alertsView.mostrarError("Error", "La contraseña no puede contener espacios.");
			return false;
		}
		return true;
	}

	public boolean validarComboBox(String entrada) {
		if (entrada == null) {
			alertsView.mostrarError("Error", "Los desplegables no pueden estar vacios.");
			return false;
		}

		return true;
	}

	public boolean validarNombreYApellido(String name, String apell) {
		if (name == null || apell == null) {
			alertsView.mostrarError("Error", "El campo nombre o apellido no pueden estar vacios.");
			return false;
		}
		if (name.contains(" ") || apell.contains(" ")) {
			alertsView.mostrarError("Error", "El campo nombre o apellido no puede contener espacios.");
			return false;
		}
		for (int i = 1; i < name.length(); i++) {
			if (!Character.isLetter(name.charAt(i))) {
				alertsView.mostrarError("Error", "El campo nombre no puede contener números ni caracteres especiales.");
				return false;
			}
		}
		for (int i = 1; i < apell.length(); i++) {
			if (!Character.isLetter(apell.charAt(i))) {
				alertsView.mostrarError("Error",
						"El campo apellido no puede contener números ni caracteres especiales.");
				return false;
			}
		}

		return true;
	}

	// SELLARALOJAR
	public boolean yaSelladoHoy(Peregrino peregrino, Parada parada, LocalDate fecha) {
		ParadasPeregrinosId id = new ParadasPeregrinosId(peregrino.getId(), parada.getId());
		id.setFecha(fecha);
		return paradasPeregrinosRepository.existsById(id);
	}

	public boolean yaAlojoHoy(Peregrino peregrino, Parada parada, LocalDate fecha) {
		return estanciaRepository.existsByPeregrinoAndParadaAndFecha(peregrino, parada, fecha);
	}

	// CREAR PARADA

	public boolean existeNombreYRegion(String nombre, char region) {
		return paradaRepository.existsByNombreAndRegion(nombre, region);
	}

	public boolean validarNombreParadaYResponsable(String nombre) {
		if (nombre == null || nombre.trim().isEmpty()) {
			alertsView.mostrarError("Error", "El campo no puede estar vacío.");
			return false;
		}

		if (!nombre.matches("[a-zA-ZÑñ ]+")) {
			alertsView.mostrarError("Error",
					"Los nombres solo puede contener letras sin tildes y espacios, sin números ni caracteres especiales.");
			return false;
		}

		return true;
	}

	public boolean validarNombre(String cadena) {
		for (int i = 1; i < cadena.length(); i++) {
			if (!Character.isLetter(cadena.charAt(i))) {
				alertsView.mostrarError("Error", "El campo nombre no puede contener números ni caracteres especiales.");
				return false;
			}
		}
		return true;

	}

}
