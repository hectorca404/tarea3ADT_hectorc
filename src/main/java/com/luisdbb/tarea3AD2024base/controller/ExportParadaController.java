package com.luisdbb.tarea3AD2024base.controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Credenciales;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.services.AyudaService;
import com.luisdbb.tarea3AD2024base.services.CredencialesService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.SesionService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Controller
public class ExportParadaController {
	@FXML
	private TableView<Estancia> tablaPeregrinos;
	@FXML
	private TableColumn<Estancia, String> peregrinoColumn;
	@FXML
	private TableColumn<Estancia, String> estanciaColumn;
	@FXML
	private TableColumn<Estancia, String> vipColumn;

	@FXML
	private DatePicker desdeDatePicker;

	@FXML
	private DatePicker hastaDatePicker;

	@FXML
	private Button exportarButton;

	@FXML
	private Button limpiarButton;

	@FXML
	private Button volverMenuButton;

	@FXML
	private Button ayudaButton;

	@FXML
	private ImageView ayudaIcon;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private SesionService sesionService;

	@Autowired
	private ParadaService paradaService;

	@Autowired
	private AyudaService ayudaService;

	private LocalDate fechaInicio = null;
	private LocalDate fechaFin = null;

	private Parada paradaActual;

	@FXML

	public void initialize() {
		LocalDate[] fechas = rangoDeFechas();
		paradaActual = sesionService.getParadaActual();

		desdeDatePicker.valueProperty().addListener((obs, oldDate, newDate) -> actualizarTablaConFechas());
		hastaDatePicker.valueProperty().addListener((obs, oldDate, newDate) -> actualizarTablaConFechas());
		;

		ayudaIcon.setImage(new Image(getClass().getResourceAsStream("/images/help.png")));

		volverMenuButton.setOnAction(event -> volverMenu());
		limpiarButton.setOnAction(event -> limpiarFormulario());

		exportarButton.setOnAction(event -> {
			exportarDatosParadaXML(paradaActual, fechas[0], fechas[1]);
		});
		ayudaButton.setOnAction(event -> ayudaService.mostrarAyuda("/help/ExportParada.html"));

		configurarAtajos();
	}

	private void configurarAtajos() {
		exportarButton.sceneProperty().addListener((observable, oldScene, newScene) -> {
			if (oldScene != null) {
				oldScene.setOnKeyPressed(null);
			}
			if (newScene != null) {
				newScene.setOnKeyPressed(event -> {
					switch (event.getCode()) {
					case ENTER -> {
						event.consume();
						exportarDatosParadaXML(paradaActual, rangoDeFechas()[0], rangoDeFechas()[1]);
					}
					case F1 -> {
						event.consume();
						ayudaService.mostrarAyuda("/help/ExportParada.html");
					}
					case ESCAPE -> {
						event.consume();
						volverMenu();
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

	public void exportarDatosParadaXML(Parada parada, LocalDate fechaInicio, LocalDate fechaFin) {

		List<Estancia> estancias = paradaService.obtenerEstancias(parada.getId());
		List<Estancia> estanciasFiltradas = new ArrayList<>();

		for (Estancia estancia : estancias) {
			if (estancia.getFecha() != null && !estancia.getFecha().isBefore(fechaInicio)
					&& !estancia.getFecha().isAfter(fechaFin)) {
				estanciasFiltradas.add(estancia);
			}
		}

		String pathExportacion = "src/main/resources/paradasXML/";
		String nombreFichero = parada.getNombre().replaceAll(" ", "_") + "_" + fechaInicio + "_" + fechaFin + ".xml";
		String fichero = pathExportacion + nombreFichero;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();

			Element paradaElement = doc.createElement("parada");
			doc.appendChild(paradaElement);

			Element idElement = doc.createElement("id");
			idElement.setTextContent(String.valueOf(parada.getId()));
			paradaElement.appendChild(idElement);

			Element nombreElement = doc.createElement("nombre");
			nombreElement.setTextContent(parada.getNombre());
			paradaElement.appendChild(nombreElement);

			Element regionElement = doc.createElement("region");
			regionElement.setTextContent("" + parada.getRegion());
			paradaElement.appendChild(regionElement);

			Element fechaInicioElement = doc.createElement("fecha_inicio");
			fechaInicioElement.setTextContent(fechaInicio.format(formatter));
			paradaElement.appendChild(fechaInicioElement);

			Element fechaFinElement = doc.createElement("fecha_fin");
			fechaFinElement.setTextContent(fechaFin.format(formatter));
			paradaElement.appendChild(fechaFinElement);

			Element estanciasElement = doc.createElement("estancias");
			for (Estancia estancia : estanciasFiltradas) {
				Element estanciaElement = doc.createElement("estancia");

				Element idEstanciaElement = doc.createElement("id");
				idEstanciaElement.setTextContent(String.valueOf(estancia.getId()));
				estanciaElement.appendChild(idEstanciaElement);

				Element nombrePeregrinoElement = doc.createElement("nombre_peregrino");
				nombrePeregrinoElement.setTextContent(estancia.getPeregrino().getNombre());
				estanciaElement.appendChild(nombrePeregrinoElement);

				Element fechaElement = doc.createElement("fecha");
				fechaElement.setTextContent(estancia.getFecha().format(formatter));
				estanciaElement.appendChild(fechaElement);

				Element vipElement = doc.createElement("vip");
				vipElement.setTextContent(estancia.isVip() ? "Si" : "No");
				estanciaElement.appendChild(vipElement);

				estanciasElement.appendChild(estanciaElement);
			}
			paradaElement.appendChild(estanciasElement);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			File directorio = new File(pathExportacion);
			if (!directorio.exists()) {
				directorio.mkdirs();
			}

			try (OutputStream outputStream = new FileOutputStream(new File(fichero))) {
				transformer.transform(new DOMSource(doc), new StreamResult(outputStream));
				mostrarAlerta("Éxito", "Datos exportados correctamente en: " + fichero);
			}

		} catch (ParserConfigurationException | IOException | TransformerException e) {
			mostrarAlerta("Error", "Error al exportar los datos: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private LocalDate[] rangoDeFechas() {
		LocalDate[] rangoFechas = new LocalDate[2];

		fechaInicio = desdeDatePicker.getValue();
		fechaFin = hastaDatePicker.getValue();

		if (fechaInicio == null || fechaFin == null || fechaFin.isBefore(fechaInicio)) {
			return rangoFechas;
		}

		rangoFechas[0] = fechaInicio;
		rangoFechas[1] = fechaFin;
		return rangoFechas;
	}

	private void cargarPeregrinosYEstancias(Parada parada, LocalDate fechaInicio, LocalDate fechaFin) {
		List<Estancia> estancias = paradaService.obtenerEstancias(parada.getId());

		List<Estancia> estanciasFiltradas = estancias.stream().filter(estancia -> estancia.getFecha() != null
				&& !estancia.getFecha().isBefore(fechaInicio) && !estancia.getFecha().isAfter(fechaFin)).toList();

		ObservableList<Estancia> estanciasObservable = FXCollections.observableArrayList(estanciasFiltradas);
		tablaPeregrinos.setItems(estanciasObservable);

		peregrinoColumn.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getPeregrino().getNombre()));

		estanciaColumn.setCellValueFactory(cellData -> new SimpleStringProperty("Estancia"));

		vipColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isVip() ? "Si" : "No"));

		List<Estancia> estanciass = paradaService.obtenerEstancias(parada.getId());
		System.out.println("Número total de estancias: " + estanciass.size());

	}

	private void actualizarTablaConFechas() {
		LocalDate fechaInicio = desdeDatePicker.getValue();
		LocalDate fechaFin = hastaDatePicker.getValue();

		cargarPeregrinosYEstancias(paradaActual, fechaInicio, fechaFin);
	}

	private void limpiarFormulario() {
		desdeDatePicker.setValue(null);
		hastaDatePicker.setValue(null);
		tablaPeregrinos.getItems().clear();
	}

	@FXML
	private void volverMenu() {
		stageManager.switchScene(FxmlView.RESPARADA);
	}

	private void mostrarAlerta(String titulo, String mensaje) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
	}
}