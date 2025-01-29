package com.luisdbb.tarea3AD2024base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.SpringFXMLLoader;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.services.AyudaService;
import com.luisdbb.tarea3AD2024base.services.CredencialesService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

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
	private Button volverMenuButton;

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

	@FXML
	public void initialize() {
		ayudaIcon.setImage(new Image(getClass().getResourceAsStream("/images/help.png")));

		crearButton.setOnAction(event -> registrarParada());
		limpiarButton.setOnAction(event -> limpiarFormulario());
		volverMenuButton.setOnAction(event -> volverMenu());
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

			if (!password.equals(confirmPassword)) {
				mostrarAlerta("Error", "Las contraseñas no coinciden", Alert.AlertType.ERROR);
				return;
			}

			paradaService.registrarParada(nombre, region, responsable, usuario, correo, password);
			mostrarAlerta("Exitoso", "Parada registrada correctamente", Alert.AlertType.INFORMATION);
			limpiarFormulario();
		} catch (Exception e) {
			mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
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

	private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
		Alert alerta = new Alert(tipo);
		alerta.setTitle(titulo);
		alerta.setHeaderText(null);
		alerta.setContentText(mensaje);
		alerta.showAndWait();
	}
}
