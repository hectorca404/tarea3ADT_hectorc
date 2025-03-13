package com.luisdbb.tarea3AD2024base.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.services.CarnetExistDBService;
import com.luisdbb.tarea3AD2024base.services.SesionService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@Controller
public class VerPeregrinosParadaController {

	@FXML
	private Hyperlink volverMenulink;

	@FXML
	private ImageView ayudaIcon;

	@FXML
	private Button ayudaButton;

	@FXML
	private TreeView<String> perTreeView;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private CarnetExistDBService carnetExistDBService;

	@Autowired
	private SesionService sesionService;

	@FXML
	public void initialize() {
		Parada paradaActual = sesionService.getParadaActual();
		ayudaIcon.setImage(new Image(getClass().getResourceAsStream("/images/help.png")));

		volverMenulink.setOnAction(event -> volverMenu());

		cargarCarnets(paradaActual.getNombre());
	}

	private void volverMenu() {
		stageManager.switchScene(FxmlView.RESPARADA);
	}

	private void cargarCarnets(String nombreParada) {
		List<String> carnets = carnetExistDBService.obtenerCarnetsPorParada(nombreParada);

		TreeItem<String> raiz = new TreeItem<>("Carnets");

		for (String carnet : carnets) {
			TreeItem<String> item = new TreeItem<>(carnet);
			raiz.getChildren().add(item);
		}

		perTreeView.setRoot(raiz);
		perTreeView.setShowRoot(true);
	}
}