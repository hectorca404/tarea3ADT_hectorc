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
public class EditarServicioController {

	@FXML
	private ComboBox<Servicio> servicioComboBox;

	@FXML
	private TextField nombreServicioField;

	@FXML
	private TextField precioField;

	@FXML
	private ComboBox<Parada> paradasComboBox;

	@FXML
	private Button guardarButton;

	@FXML
	private Button cancelarButton;

	@FXML
	private Button ayudaButton;

	@FXML
	private ImageView ayudaIcon;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private ServicioService servicioService;

	@Autowired
	private ParadaService paradaService;

	@Autowired
	private AlertsView alertsView;

	@Autowired
	private ValidacionesService validacionesService;

	private ObservableList<Parada> listaParadas = FXCollections.observableArrayList();
	private ObservableList<Parada> paradasSeleccionadas = FXCollections.observableArrayList();

	@FXML
	public void initialize() {
		ayudaIcon.setImage(new Image(getClass().getResourceAsStream("/images/help.png")));

		cancelarButton.setOnAction(event -> cancelar());
		guardarButton.setOnAction(event -> actualizarServicio());

		cargarServicios();

		servicioComboBox.setOnAction(event -> {
			Servicio servicioSeleccionado = servicioComboBox.getSelectionModel().getSelectedItem();
			if (servicioSeleccionado != null) {
				cargarDatosServicio(servicioSeleccionado);
				cargarParadas();
			}
		});

	}

	private void cargarParadas() {
		listaParadas.setAll(paradaService.obtenerTodasLasParadas());
		paradasComboBox.setItems(listaParadas);
	}

	private void cargarServicios() {
		List<Servicio> servicios = servicioService.obtenerTodosServicios();
		servicioComboBox.setItems(FXCollections.observableArrayList(servicios));
		servicioComboBox.setPromptText("Selecciona un servicio");
	}

	private void cargarDatosServicio(Servicio servicio) {
		nombreServicioField.setText(servicio.getNombre());
		precioField.setText(String.valueOf(servicio.getPrecio()));

		paradasSeleccionadas.clear();
		
		if (listaParadas.isEmpty()) {
	        cargarParadas();
	    }

		for (Parada parada : listaParadas) {
			if (servicio.getParadaIds().contains(parada.getId())) {
				paradasSeleccionadas.add(parada);
			}
		}

		UIUtils.configurarParadasComboBox(paradasComboBox, listaParadas, paradasSeleccionadas);
	}
	


	private void actualizarServicio() {
		Servicio servicioSeleccionado = servicioComboBox.getSelectionModel().getSelectedItem();
		if (servicioSeleccionado == null) {
			alertsView.mostrarError("Error", "Debes seleccionar un servicio para editarlo");
			return;
		}

		String nombre = nombreServicioField.getText();
		String precioTexto = precioField.getText();

		if (!validacionesService.validarNombre(nombre)) {
			return;
		}

		if (nombre.isEmpty() || precioTexto.isEmpty()) {
			alertsView.mostrarError("Error", "Tienes campos vacios");
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
			alertsView.mostrarAdvertencia("Error", "Debes introducir un numero");
			return;
		}

		servicioSeleccionado.setNombre(nombre);
		servicioSeleccionado.setPrecio(precio);

		List<Long> nuevasParadas = new ArrayList<>();
		for (Parada parada : paradasSeleccionadas) {
			nuevasParadas.add(parada.getId());
		}
		servicioSeleccionado.setParadaIds(nuevasParadas);
		
		servicioService.guardarServicio(servicioSeleccionado);

		alertsView.mostrarConfirmacion("Exito", "Servicio editado correctamente");

		cancelar();
	}

	private void cancelar() {
		stageManager.switchScene(FxmlView.MENUSERVICIOS);

	}

}