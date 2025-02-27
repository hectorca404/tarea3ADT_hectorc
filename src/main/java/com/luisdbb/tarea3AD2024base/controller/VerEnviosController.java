package com.luisdbb.tarea3AD2024base.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.services.EnvioACasaService;
import com.luisdbb.tarea3AD2024base.services.SesionService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@Controller
public class VerEnviosController {

	@FXML
	private TableView<EnvioACasa> enviosTableView;

	@FXML
	private TableColumn<EnvioACasa, String> colPeregrino;

	@FXML
	private TableColumn<EnvioACasa, String> colDireccion;

	@FXML
	private Hyperlink volverMenulink;

	@FXML
	private Button ayudaButton;

	@FXML
	private ImageView ayudaIcon;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private EnvioACasaService envioACasaService;

	@Autowired
	private SesionService sesionService;

	private Parada paradaActual;

	private ObservableList<EnvioACasa> listaEnvios;

	@FXML
	public void initialize() {
		paradaActual = sesionService.getParadaActual();
		ayudaIcon.setImage(new Image(getClass().getResourceAsStream("/images/help.png")));

		volverMenulink.setOnAction(event -> volverAlMenu());
		cargarEnvios();

	}

	private void cargarEnvios() {
		List<EnvioACasa> envios = envioACasaService.obtenerEnviosPorParada(paradaActual);
		listaEnvios = FXCollections.observableArrayList(envios);
		enviosTableView.setItems(listaEnvios);

		colPeregrino.setCellValueFactory(data -> {
			Peregrino peregrino = envioACasaService.obtenerPeregrinoDeEnvio(data.getValue());
			return new SimpleStringProperty(peregrino != null ? peregrino.getNombre() : "Desconocido");
		});

		colDireccion
				.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDireccion().getDireccion()));
	}

	private void volverAlMenu() {
		stageManager.switchScene(FxmlView.RESPARADA);
	}

}
