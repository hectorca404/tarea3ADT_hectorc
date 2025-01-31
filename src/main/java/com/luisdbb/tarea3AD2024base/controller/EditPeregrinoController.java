package com.luisdbb.tarea3AD2024base.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Credenciales;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.services.AyudaService;
import com.luisdbb.tarea3AD2024base.services.CredencialesService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.SesionService;
import com.luisdbb.tarea3AD2024base.services.ValidacionesService;
import com.luisdbb.tarea3AD2024base.view.AlertsView;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

@Controller
public class EditPeregrinoController {

	@FXML
	private TextField nombreField;
	@FXML
	private TextField apellidoField;
	@FXML
	private TextField correoField;
	@FXML
	private ComboBox<String> nacionalidadComboBox;
	@FXML
	private Button guardarButton;
	@FXML
	private Button limpiarButton;
	@FXML
	private Hyperlink volverMenuLink;
	@FXML
	private Button ayudaButton;

	@FXML
	private ImageView ayudaIcon;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private PeregrinoService peregrinoService;

	@Autowired
	private CredencialesService credencialesService;

	@Autowired
	private SesionService sesionService;

	@Autowired
	private AyudaService ayudaService;

	@Autowired
	private ValidacionesService validacionesService;

	@Autowired
	private AlertsView alertsView;

	private Peregrino peregrinoActual;

	@FXML
	public void initialize() {
		peregrinoActual = sesionService.getPeregrinoActual();

		cargarPeregrino(peregrinoActual);

		ayudaIcon.setImage(new Image(getClass().getResourceAsStream("/images/help.png")));

		guardarButton.setOnAction(event -> guardarCambios());
		limpiarButton.setOnAction(event -> limpiarFormulario());
		volverMenuLink.setOnAction(event -> volverMenu());
		ayudaButton.setOnAction(event -> ayudaService.mostrarAyuda("/help/EditPeregrino.html"));

		cargarNacionalidades();

		configurarAtajos();
	}

	private void configurarAtajos() {
		guardarButton.sceneProperty().addListener((observable, oldScene, newScene) -> {
			if (oldScene != null) {
				oldScene.setOnKeyPressed(null);
			}
			if (newScene != null) {
				newScene.setOnKeyPressed(event -> {
					switch (event.getCode()) {
					case ENTER -> {
						event.consume();
						guardarCambios();
					}
					case ESCAPE -> {
						event.consume();
						volverMenu();
					}
					case F1 -> {
						event.consume();
						ayudaService.mostrarAyuda("/help/EditPeregrino.html");
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

	public void cargarPeregrino(Peregrino peregrino) {
		nombreField.setText(peregrino.getNombre());
		apellidoField.setText(peregrino.getApellido());

		Credenciales credenciales = credencialesService.obtenerCredencialesPeregrino(peregrino);
		correoField.setText(credenciales.getCorreo());

		nacionalidadComboBox.setValue(peregrino.getNacionalidad());
	}

	private void cargarNacionalidades() {
		List<String> nacionalidades = obtenerNacionalidadesXML("/paises.xml");
		nacionalidadComboBox.setItems(FXCollections.observableArrayList(nacionalidades));
	}

	private void guardarCambios() {
		String nombre = nombreField.getText();
		String apellido = apellidoField.getText();

		if (!validacionesService.validarNombreYApellido(nombre, apellido)) {
			return;
		}
		String nacionalidad = nacionalidadComboBox.getValue();
		if (!validacionesService.validarComboBox(nacionalidad)) {
			return;
		}

		String nuevoCorreo = correoField.getText();
		if (!validacionesService.validarCorreo(nuevoCorreo)) {
			return;
		}

		try {
			peregrinoActual.setNombre(nombreField.getText());
			peregrinoActual.setApellido(apellidoField.getText());
			peregrinoActual.setNacionalidad(nacionalidadComboBox.getValue());
			peregrinoService.guardarCambiosPeregrino(peregrinoActual, nuevoCorreo);
			alertsView.mostrarInfo("Peregrino Editado Correctamente", "Los cambios se guardaron correctamente");
			volverMenu();
		} catch (IllegalArgumentException e) {
			alertsView.mostrarError("Error", e.getMessage());
		} catch (Exception e) {
			alertsView.mostrarError("Error", "Error al guardar los cambios.");
		}

	}

	private void limpiarFormulario() {
		nombreField.clear();
		apellidoField.clear();
		correoField.clear();
		nacionalidadComboBox.getSelectionModel().clearSelection();
	}

	private void volverMenu() {
		stageManager.switchScene(FxmlView.PEREGRINO);
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
