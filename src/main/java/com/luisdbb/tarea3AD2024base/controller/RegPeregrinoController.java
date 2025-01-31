package com.luisdbb.tarea3AD2024base.controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.services.AyudaService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.ValidacionesService;
import com.luisdbb.tarea3AD2024base.view.AlertsView;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RegPeregrinoController {

	@FXML
	private TextField userField;

	@FXML
	private TextField nombreField;

	@FXML
	private TextField apellidoField;

	@FXML
	private TextField correoField;

	@FXML
	private ComboBox<String> nacionalidadComboBox;

	@FXML
	private ComboBox<Parada> paradaInicioComboBox;

	@FXML
	private PasswordField passwordField;

	@FXML
	private PasswordField confirmPasswordField;

	@FXML
	private Button registrarButton;

	@FXML
	private Button limpiarButton;

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
	private PeregrinoService peregrinoService;

	@Autowired
	private ParadaService paradaService;

	@Autowired
	private AyudaService ayudaService;

	@Autowired
	private AlertsView alertsView;

	@Autowired
	private ValidacionesService validacionesService;

	@FXML
	public void initialize() {
		cargarNacionalidades();
		cargarParadas();

		ayudaIcon.setImage(new Image(getClass().getResourceAsStream("/images/help.png")));

		registrarButton.setOnAction(event -> registrarPeregrino());
		limpiarButton.setOnAction(event -> limpiarFormulario());
		volverLogin.setOnAction(event -> volverLogin());
		ayudaButton.setOnAction(event -> ayudaService.mostrarAyuda("/help/RegPeregrino.html"));

		configurarAtajos();
	}

	private void configurarAtajos() {
		registrarButton.sceneProperty().addListener((observable, oldScene, newScene) -> {
			if (newScene != null) {
				newScene.setOnKeyPressed(event -> {
					switch (event.getCode()) {
					case ENTER -> {
						event.consume();
						registrarPeregrino();
					}
					case F1 -> {
						event.consume();
						ayudaService.mostrarAyuda("/help/RegPeregrino.html");
					}
					case ESCAPE -> {
						event.consume();
						volverLogin();
					}
					case L -> {
						if (event.isControlDown()) {
							event.consume();
							limpiarFormulario();
						}
					}
					}
				});
			}
		});
	}

	private void volverLogin() {
		stageManager.switchScene(FxmlView.PRINCIPAL);
	}

	private void cargarNacionalidades() {
		List<String> nacionalidades = obtenerNacionalidadesXML("/paises.xml");
		nacionalidadComboBox.setItems(FXCollections.observableArrayList(nacionalidades));
	}

	private void cargarParadas() {
		List<Parada> paradas = paradaService.obtenerTodasLasParadas();
		paradaInicioComboBox.setItems(FXCollections.observableArrayList(paradas));
	}

	private void registrarPeregrino() {

		String nombreUsuario = userField.getText();
		if (validacionesService.existeUsuario(nombreUsuario)) {
			return;
		}
		if (!validacionesService.validarNombreUsuario(nombreUsuario)) {
			return;
		}

		String contrasena = passwordField.getText();
		String confirmarContrasena = confirmPasswordField.getText();

		String correo = correoField.getText();
		if (!validacionesService.validarCorreo(correo)) {
			return;
		}

		String nombre = nombreField.getText();
		String apellido = apellidoField.getText();
		if (!validacionesService.validarNombreYApellido(nombre, apellido)) {
			return;
		}

		String nacionalidad = nacionalidadComboBox.getValue();
		if (!validacionesService.validarComboBox(nacionalidad)) {
			return;
		}

		Parada paradaInicio = paradaInicioComboBox.getValue();
		if (paradaInicio == null) {
			alertsView.mostrarError("Error", "Debes seleccionar una parada de inicio.");
			return;
		}
		if (!validacionesService.validarSinEspacios(contrasena)) {
			return;
		}

		if (!contrasena.equals(confirmarContrasena)) {
			alertsView.mostrarError("Error", "Las contraseñas no coinciden");
			return;
		}

		try {
			peregrinoService.registrarPeregrino(nombreUsuario, contrasena, correo, nombre, apellido, nacionalidad,
					paradaInicio);
			alertsView.mostrarInfo("Peregrino Reistrado", "Peregrino registrado correctamente");
			limpiarFormulario();

		} catch (IllegalArgumentException e) {
			alertsView.mostrarError("Error", e.getMessage());
		}

	}

	private void limpiarFormulario() {
		userField.clear();
		nombreField.clear();
		apellidoField.clear();
		correoField.clear();
		nacionalidadComboBox.getSelectionModel().clearSelection();
		paradaInicioComboBox.getSelectionModel().clearSelection();
		passwordField.clear();
		confirmPasswordField.clear();
	}

	private List<String> obtenerNacionalidadesXML(String rutaArchivo) {
		List<String> nacionalidades = new ArrayList<>();
		try {
			InputStream inputStream = getClass().getResourceAsStream(rutaArchivo);

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(inputStream);
			NodeList paises = document.getElementsByTagName("pais");
			for (int i = 0; i < paises.getLength(); i++) {
				Element pais = (Element) paises.item(i);
				String nombre = pais.getElementsByTagName("nombre").item(0).getTextContent();
				nacionalidades.add(nombre);
			}
		} catch (Exception e) {
			System.out.println("Error al cargar las nacionalidades: " + e.getMessage());
		}
		return nacionalidades;
	}
}
