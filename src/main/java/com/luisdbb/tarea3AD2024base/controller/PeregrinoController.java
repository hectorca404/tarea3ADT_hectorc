package com.luisdbb.tarea3AD2024base.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.services.SesionService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

@Controller
public class PeregrinoController {

	@FXML
	private Button exportarButton;

	@FXML
	private Button editarContactoButton;

	@FXML
	private Button cerrarSesionButton;

	@FXML
	private ImageView exportarIcon;

	@FXML
	private ImageView editarContactoIcon;

	@FXML
	private ImageView cerrarSesionIcon;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@FXML
	public void initialize() {
		try {
			exportarIcon.setImage(new Image(getClass().getResourceAsStream("/images/exportar.png")));
			editarContactoIcon.setImage(new Image(getClass().getResourceAsStream("/images/editarUsuario.png")));
			cerrarSesionIcon.setImage(new Image(getClass().getResourceAsStream("/images/cerrarSesion.png")));
			cerrarSesionButton.setOnAction(event -> volverLogin());
			editarContactoButton.setOnAction(event -> editarPeregrino());
			exportarButton.setOnAction(event -> exportarPeregrino());

		} catch (Exception e) {
			System.out.println("Error al inicializar PeregrinoController: " + e.getMessage());
		}
	}

	private void volverLogin() {
		stageManager.switchScene(FxmlView.PRINCIPAL);
	}

	private void editarPeregrino() {
		stageManager.switchScene(FxmlView.EDITPEREGRINO);
	}

	private void exportarPeregrino() {

		stageManager.switchScene(FxmlView.EXPORTPEREGRINO);

	}

}
