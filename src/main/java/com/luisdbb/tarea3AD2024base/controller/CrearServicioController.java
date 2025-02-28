package com.luisdbb.tarea3AD2024base.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.ServicioService;
import com.luisdbb.tarea3AD2024base.services.ValidacionesService;
import com.luisdbb.tarea3AD2024base.utils.UIUtils;
import com.luisdbb.tarea3AD2024base.view.AlertsView;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@Controller
public class CrearServicioController {

	@FXML
	private Button ayudaButton;

	@FXML
	private ImageView ayudaIcon;
	@FXML
	private Button guardarButton;

	@FXML
	private Button cancelarButton;

	@FXML
	private TextField nombreServicioField;

	@FXML
	private TextField precioField;

	@FXML
	private ComboBox<Parada> paradasComboBox;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private ParadaService paradaService;

	@Autowired
	private ServicioService servicioService;

	@Autowired
	private AlertsView alertsView;

	@Autowired
	private ValidacionesService validacionesService;

	private ObservableList<Parada> paradasSeleccionadas = FXCollections.observableArrayList();
	private ObservableList<Parada> listaParadas;

	@FXML
	public void initialize() {
		ayudaIcon.setImage(new Image(getClass().getResourceAsStream("/images/help.png")));
		cancelarButton.setOnAction(event -> cancelar());
		configurarParadas();
		List<Servicio> servicios = servicioService.obtenerTodosServicios();
		for (Servicio s : servicios) {
			System.out.println(s.getNombre());
		}

		guardarButton.setOnAction(event -> guardarServicio());
	}

	private void configurarParadas() {
		listaParadas = obtenerParadas();
		UIUtils.configurarParadasComboBox(paradasComboBox, listaParadas, paradasSeleccionadas);
	}

	private ObservableList<Parada> obtenerParadas() {
		List<Parada> paradas = paradaService.obtenerTodasLasParadas();
		return listaParadas = FXCollections.observableArrayList(paradas);
	}

	private void guardarServicio() {
		Long id = servicioService.obtenerSiguienteIdServicio();

		String nombre = nombreServicioField.getText();
		String precioTexto = precioField.getText();

		if (!validacionesService.validarNombre(nombre)) {
			return;
		}

		if (nombre.isEmpty() || precioTexto.isEmpty()) {
			alertsView.mostrarError("Error", "Hay campos vacios");
			return;
		}

		if (servicioService.existeServicio(nombre)) {
			alertsView.mostrarError("Error!!!", "Ya existe un servicio con este nombre");
			return;
		}

		double precio;
		try {
			precio = Double.parseDouble(precioTexto);
			if (precio <= 0) {
				alertsView.mostrarError("Error", "El precio debe ser mayor que 0");
				return;
			}
		} catch (NumberFormatException e) {
			alertsView.mostrarError("Error", "Debes introducir numeros en el precio");
			return;
		}

		if (paradasSeleccionadas.isEmpty()) {
			alertsView.mostrarError("Error", "Debes seleccionar por lo menos una parada");
			return;
		}

		List<Long> paradaIds = new ArrayList<>();
		for (Parada parada : paradasSeleccionadas) {
			paradaIds.add(parada.getId());
		}

		Servicio servicio = new Servicio(id, nombre, precio);
		servicio.setParadaIds(paradaIds);

		servicioService.guardarServicio(servicio);
		;

		alertsView.mostrarConfirmacion("Exito", "Servicio creado correctamente");

		limpiarFormulario();
	}

	private void cancelar() {
		limpiarFormulario();
		stageManager.switchScene(FxmlView.MENUSERVICIOS);
	}

	private void limpiarFormulario() {
		nombreServicioField.clear();
		precioField.clear();

		paradasComboBox.getSelectionModel().clearSelection();
		paradasSeleccionadas.clear();

		configurarParadas();
	}

}
