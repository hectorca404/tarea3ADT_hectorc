package com.luisdbb.tarea3AD2024base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.SpringFXMLLoader;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

@Controller
public class AdminController {

	@FXML
	private Button crearParadaButton;

	@FXML
	private Button cerrarSesionButton;

	@FXML
	private ImageView crearParadaIcon;

	@FXML
	private ImageView cerrarSesionIcon;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@FXML
	public void initialize() {
		try {
			crearParadaIcon.setImage(new Image(getClass().getResourceAsStream("/images/parada.png")));
			cerrarSesionIcon.setImage(new Image(getClass().getResourceAsStream("/images/cerrarSesion.png")));

			crearParadaButton.setOnAction(event -> crearParada());
			cerrarSesionButton.setOnAction(event -> cerrarSesion());
		} catch (Exception e) {
			System.out.println("Error al iniciar AdminController: " + e.getMessage());
		}
	}

	private void crearParada() {
		stageManager.switchScene(FxmlView.CREARPARADA);
	}

	private void cerrarSesion() {
		stageManager.switchScene(FxmlView.PRINCIPAL);
	}

}

