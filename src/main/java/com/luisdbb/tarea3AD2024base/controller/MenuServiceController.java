package com.luisdbb.tarea3AD2024base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@Controller
public class MenuServiceController {

	@FXML
	private Button ayudaButton;

	@FXML
	private ImageView ayudaIcon;

	@FXML
	private Button crearServicioButton;

	@FXML
	private ImageView crearServicioIcon;

	@FXML
	private Button editarServicioButton;

	@FXML
	private ImageView editarServicioIcon;

	@FXML
	private Hyperlink volverMenuLink;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@FXML
	public void initialize() {
		ayudaIcon.setImage(new Image(getClass().getResourceAsStream("/images/help.png")));
		crearServicioIcon.setImage(new Image(getClass().getResourceAsStream("/images/agregarUsuario.png")));
		editarServicioIcon.setImage(new Image(getClass().getResourceAsStream("/images/editarUsuario.png")));

		crearServicioButton.setOnAction(event -> abrirCrearServicio());
		editarServicioButton.setOnAction(event -> editarServicio());
		volverMenuLink.setOnAction(event -> volverAlMenuAdministrador());
	}

	private void volverAlMenuAdministrador() {
		stageManager.switchScene(FxmlView.ADMIN);
	}

	private void abrirCrearServicio() {
		stageManager.switchScene(FxmlView.CREARSERVICIO);
	}

	private void editarServicio() {
		stageManager.switchScene(FxmlView.EDITARSERVICIO);
	}

}
