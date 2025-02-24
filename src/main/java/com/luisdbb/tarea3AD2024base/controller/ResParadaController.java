package com.luisdbb.tarea3AD2024base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.services.AyudaService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

@Controller
public class ResParadaController {

	@FXML
	private Button exportarDatosButton;

	@FXML
	private Button sellarAlojarButton;

	@FXML
	private Button cerrarSesionButton;

	@FXML
	private Button ayudaButton;

	@FXML
	private Button verEnviosButton;

	@FXML
	private ImageView exportarIcon;

	@FXML
	private ImageView sellarIcon;

	@FXML
	private ImageView cerrarSesionIcon;

	@FXML
	private ImageView ayudaIcon;

	@FXML
	private ImageView verEnviosIcon;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private AyudaService ayudaService;

	@FXML
	public void initialize() {
		exportarIcon.setImage(new Image(getClass().getResourceAsStream("/images/exportar.png")));
		sellarIcon.setImage(new Image(getClass().getResourceAsStream("/images/sellar.png")));
		cerrarSesionIcon.setImage(new Image(getClass().getResourceAsStream("/images/cerrarSesion.png")));
		ayudaIcon.setImage(new Image(getClass().getResourceAsStream("/images/help.png")));
		verEnviosIcon.setImage(new Image(getClass().getResourceAsStream("/images/abierto.png")));

		cerrarSesionButton.setOnAction(event -> volverLogin());
		sellarAlojarButton.setOnAction(event -> sellarAlojar());
		exportarDatosButton.setOnAction(event -> exportarDatos());
		ayudaButton.setOnAction(event -> ayudaService.mostrarAyuda("/help/ResParada.html"));
		verEnviosButton.setOnAction(event -> verEnvio());

		configurarAtajo();

	}

	private void configurarAtajo() {
		ayudaButton.sceneProperty().addListener((observable, oldScene, newScene) -> {
			if (oldScene != null) {
				oldScene.setOnKeyPressed(null);
			}
			if (newScene != null) {
				newScene.setOnKeyPressed(event -> {
					if (event.getCode() == KeyCode.F1) {
						event.consume();
						ayudaService.mostrarAyuda("/help/ResParada.html");
					}
				});
			}
		});
	}

	private void verEnvio() {
		stageManager.switchScene(FxmlView.VERENVIOS);
	}

	private void volverLogin() {
		stageManager.switchScene(FxmlView.PRINCIPAL);
	}

	private void sellarAlojar() {
		stageManager.switchScene(FxmlView.SELLARALOJAR);
	}

	private void exportarDatos() {
		stageManager.switchScene(FxmlView.EXPORTPARADA);
	}

}
