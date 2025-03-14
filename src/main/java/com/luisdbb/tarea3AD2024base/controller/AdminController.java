package com.luisdbb.tarea3AD2024base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.services.AyudaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.view.AlertsView;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

@Controller
public class AdminController {

	@FXML
	private Button crearParadaButton;

	@FXML
	private Button cerrarSesionButton;

	@FXML
	private Button serviciosButton;

	@FXML
	private Button ayudaButton;
	
	@FXML
	private Button exportarCarnetsButton;

	@FXML
	private ImageView crearParadaIcon;

	@FXML
	private ImageView cerrarSesionIcon;

	@FXML
	private ImageView serviciosIcon;

	@FXML
	private ImageView ayudaIcon;
	
	@FXML
	private ImageView exportarCarnetsIcon;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private AyudaService ayudaService;
	
	@Autowired
	private AlertsView alertsView;
	
	@Autowired
	private PeregrinoService peregrinoService;

	@FXML
	public void initialize() {
		crearParadaIcon.setImage(new Image(getClass().getResourceAsStream("/images/parada.png")));
		cerrarSesionIcon.setImage(new Image(getClass().getResourceAsStream("/images/cerrarSesion.png")));
		ayudaIcon.setImage(new Image(getClass().getResourceAsStream("/images/help.png")));
		serviciosIcon.setImage(new Image(getClass().getResourceAsStream("/images/servicio.png")));
		exportarCarnetsIcon.setImage(new Image(getClass().getResourceAsStream("/images/exportar.png")));

		crearParadaButton.setOnAction(event -> crearParada());
		cerrarSesionButton.setOnAction(event -> cerrarSesion());
		ayudaButton.setOnAction(event -> ayudaService.mostrarAyuda("/help/Admin.html"));
		serviciosButton.setOnAction(event -> mostrarMenuServicios());

		configurarAtajo();
	}

	private void configurarAtajo() {
		ayudaButton.sceneProperty().addListener((observable, oldScene, newScene) -> {
			if (newScene != null) {
				newScene.setOnKeyPressed(event -> {
					if (event.getCode() == KeyCode.F1) {
						event.consume();
						ayudaService.mostrarAyuda("/help/Admin.html");
					}
				});
			}
		});
	}
	
	private void exportarCarnetsActuales() {
		alertsView.mostrarInfo("Exportacion Exitosa", "Los carnets actuales de los peregrinos se han exportado correctamente.");
	}

	private void crearParada() {
		stageManager.switchScene(FxmlView.CREARPARADA);
	}

	private void mostrarMenuServicios() {
		stageManager.switchScene(FxmlView.MENUSERVICIOS);
	}

	private void cerrarSesion() {
		stageManager.switchScene(FxmlView.PRINCIPAL);
	}

}
