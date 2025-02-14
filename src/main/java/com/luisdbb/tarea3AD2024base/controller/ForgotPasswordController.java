package com.luisdbb.tarea3AD2024base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.services.AyudaService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@Controller
public class ForgotPasswordController {

	@FXML
	private TextField userField;

	@FXML
	private Button recuperarButton;

	@FXML
	private Button ayudaButton;

	@FXML
	private Hyperlink volverLogin;

	@FXML
	private ImageView ayudaIcon;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private AyudaService ayudaService;

	@FXML
	public void initialize() {
		ayudaIcon.setImage(new Image(getClass().getResourceAsStream("/images/help.png")));

		volverLogin.setOnAction(event -> volverLogin());
		ayudaButton.setOnAction(event -> ayudaService.mostrarAyuda("/help/ForgotPass.html"));

		configurarAtajos();
	}

	private void configurarAtajos() {
		userField.sceneProperty().addListener((observable, oldScene, newScene) -> {
			if (newScene != null) {
				newScene.setOnKeyPressed(event -> {
					switch (event.getCode()) {
					case ENTER -> event.consume();
					case F1 -> {
						event.consume();
						ayudaService.mostrarAyuda("/help/ForgotPass.html");
					}
					case ESCAPE -> {
						event.consume();
						volverLogin();
					}
					}
				});
			}
		});
	}

	private void volverLogin() {
		stageManager.switchScene(FxmlView.PRINCIPAL);
	}
}
