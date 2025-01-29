package com.luisdbb.tarea3AD2024base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.SpringFXMLLoader;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Credenciales;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Perfil;
import com.luisdbb.tarea3AD2024base.services.AyudaService;
import com.luisdbb.tarea3AD2024base.services.CredencialesService;
import com.luisdbb.tarea3AD2024base.services.SesionService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

@Controller
public class PrincipalController {

	@FXML
	private ImageView logo;

	@FXML
	private TextField userLogField;

	@FXML
	private PasswordField passField;

	@FXML
	private Button passButton;

	@FXML
	private Button ayudaButton;

	@FXML
	private ImageView ojoIcon;

	@FXML
	private ImageView ayudaIcon;

	@FXML
	private Button logButton;

	@FXML
	private Hyperlink forgotPass;

	@FXML
	private Hyperlink regisLink;

	private boolean contraseñaVisible = false;

	@Autowired
	private CredencialesService credencialesService;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private SesionService sesionService;

	@Autowired
	private AyudaService ayudaService;

	@FXML
	public void initialize() {
		logo.setImage(new Image(getClass().getResourceAsStream("/images/logo2.png")));
		ojoIcon.setImage(new Image(getClass().getResourceAsStream("/images/ceerrado.png")));
		ayudaIcon.setImage(new Image(getClass().getResourceAsStream("/images/help.png")));

		passButton.setOnAction(event -> visualizarContraseña());
		forgotPass.setOnAction(event -> forgotPass());
		regisLink.setOnAction(event -> regPeregrino());
		logButton.setOnAction(event -> iniciarSesion());
		ayudaButton.setOnAction(event -> ayudaService.mostrarAyuda("/help/Principal.html"));

		configurarAtajos();

	}

	private void configurarAtajos() {
		userLogField.sceneProperty().addListener((observable, oldScene, newScene) -> {
			if (newScene != null) {
				newScene.setOnKeyPressed(event -> {
					if (event.getTarget() instanceof TextField || event.getTarget() instanceof PasswordField) {
						if (event.getCode() == KeyCode.ENTER) {
							event.consume();
							iniciarSesion();
						}
					} else if (event.getCode() == KeyCode.F1) {
						event.consume();
						ayudaService.mostrarAyuda("/help/Principal.html");
					}
				});
			}
		});
	}

	private void iniciarSesion() {
		String username = userLogField.getText();
		String password = passField.getText();

		try {
			Credenciales credenciales = credencialesService.validarCredenciales(username, password);
			vistaSegunRol(credenciales.getPerfil(), credenciales);

		} catch (IllegalArgumentException e) {
			mostrarError("Credenciales inválidas");
		}
	}

	private void vistaSegunRol(Perfil perfil, Credenciales credenciales) {
		switch (perfil) {
		case PEREGRINO -> {
			Peregrino peregrino = credenciales.getPeregrino();
			sesionService.setPeregrinoActual(peregrino);
			menuPeregrino();
		}
		case ADMINISTRADOR -> {
			menuAdmin();
		}
		case PARADA -> {
			Parada parada = credenciales.getParada();
			sesionService.setParadaActual(parada);
			menuParada();
		}
		default -> mostrarError("Usuario no existe");
		}
	}

	private void mostrarError(String mensaje) {
		System.out.println(mensaje);
	}

	private void visualizarContraseña() {
		if (contraseñaVisible) {
			passField.setText(passField.getPromptText());
			passField.setPromptText("");
			passField.setDisable(false);
			ojoIcon.setImage(new Image(getClass().getResourceAsStream("/images/ceerrado.png")));
			contraseñaVisible = false;
		} else {
			passField.setPromptText(passField.getText());
			passField.clear();
			passField.setDisable(true);
			ojoIcon.setImage(new Image(getClass().getResourceAsStream("/images/abierto.png")));
			contraseñaVisible = true;
		}
	}

	private void menuAdmin() {
		stageManager.switchScene(FxmlView.ADMIN);
	}

	private void menuPeregrino() {
		stageManager.switchScene(FxmlView.PEREGRINO);
	}

	private void menuParada() {
		stageManager.switchScene(FxmlView.RESPARADA);
	}

	private void forgotPass() {
		stageManager.switchScene(FxmlView.FORGOTPASS);
	}

	private void regPeregrino() {
		stageManager.switchScene(FxmlView.REGPEREGRINO);
	}

}
