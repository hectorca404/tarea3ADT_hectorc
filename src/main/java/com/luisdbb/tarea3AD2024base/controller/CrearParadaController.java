package com.luisdbb.tarea3AD2024base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.services.AyudaService;
import com.luisdbb.tarea3AD2024base.services.ColeccionParadaService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.ValidacionesService;
import com.luisdbb.tarea3AD2024base.view.AlertsView;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@Controller
public class CrearParadaController {

	@FXML
	private TextField responsableField;

	@FXML
	private TextField correoField;

	@FXML
	private TextField nombreParadaField;

	@FXML
	private TextField regionField;

	@FXML
	private TextField usuarioField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private PasswordField confirmPasswordField;

	@FXML
	private Button crearButton;

	@FXML
	private Button limpiarButton;

	@FXML
	private Hyperlink volverMenuLink;

	@FXML
	private Button ayudaButton;

	@FXML
	private ImageView ayudaIcon;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private ParadaService paradaService;

	@Autowired
	private AyudaService ayudaService;

	@Autowired
	private ValidacionesService validacionesService;

	@Autowired
	private AlertsView alertsView;
	
	@Autowired
	private ColeccionParadaService coleccionParadaService;

	@FXML
	public void initialize() {
		regionField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.length() > 1) {
				regionField.setText(oldValue);
			}
		});
		ayudaIcon.setImage(new Image(getClass().getResourceAsStream("/images/help.png")));

		crearButton.setOnAction(event -> registrarParada());
		limpiarButton.setOnAction(event -> limpiarFormulario());
		volverMenuLink.setOnAction(event -> volverMenu());
		ayudaButton.setOnAction(event -> ayudaService.mostrarAyuda("/help/CrearParada.html"));

		configurarAtajos();
	}

	private void configurarAtajos() {
		crearButton.sceneProperty().addListener((observable, oldScene, newScene) -> {
			if (oldScene != null) {
				oldScene.setOnKeyPressed(null);
			}
			if (newScene != null) {
				newScene.setOnKeyPressed(event -> {
					switch (event.getCode()) {
					case ENTER -> {
						event.consume();
						registrarParada();
					}
					case F1 -> {
						event.consume();
						ayudaService.mostrarAyuda("/help/CrearParada.html");
					}
					case ESCAPE -> {
						event.consume();
						volverMenu();
					}
					case L -> {
						if (event.isControlDown()) {
							event.consume();
							limpiarFormulario();
						}
					}
					}
				});
			}
		});
	}

	private void registrarParada() {
		try {
			String nombre = nombreParadaField.getText();
			char region = regionField.getText().charAt(0);
			String responsable = responsableField.getText();
			String usuario = usuarioField.getText();
			String correo = correoField.getText();
			String password = passwordField.getText();
			String confirmPassword = confirmPasswordField.getText();

			if (!validacionesService.validarNombreUsuario(usuario)) {
				return;
			}

			if (validacionesService.existeUsuario(usuario)) {
				return;
			}

			if (!validacionesService.validarCorreo(correo)) {
				return;
			}
			if (!password.equals(confirmPassword)) {
				alertsView.mostrarError("Error", "Las contraseñas no coinciden");
				return;
			}

			if (!validacionesService.validarNombreParadaYResponsable(nombre)) {
				return;
			}

			if (!validacionesService.validarNombreParadaYResponsable(responsable)) {
				return;
			}

			if (!validacionesService.validarSinEspacios(password)) {
				return;
			}

			if (validacionesService.existeNombreYRegion(nombre, region)) {
				alertsView.mostrarError("Error", "Ya existe una parda con ese nombre en esa region");
				return;
			}

			
			paradaService.registrarParada(nombre, region, responsable, usuario, correo, password);
				
			coleccionParadaService.crearColeccionParada(nombre);
           
			alertsView.mostrarInfo("Exitoso", "Parada registrada correctamente");
			limpiarFormulario();
		} catch (Exception e) {
			alertsView.mostrarError("Error", e.getMessage());
		}
	}

	private void limpiarFormulario() {
		responsableField.clear();
		correoField.clear();
		usuarioField.clear();
		nombreParadaField.clear();
		regionField.clear();
		passwordField.clear();
		confirmPasswordField.clear();
	}

	private void volverMenu() {
		stageManager.switchScene(FxmlView.ADMIN);
	}

}
