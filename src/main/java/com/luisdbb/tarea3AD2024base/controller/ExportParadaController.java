package com.luisdbb.tarea3AD2024base.controller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.services.AyudaService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.SesionService;
import com.luisdbb.tarea3AD2024base.view.AlertsView;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

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
	private Hyperlink volverMenuLink;

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

	@Autowired
	private AlertsView alertsView;
	
	@Autowired
    private DataSource dataSource;

	private LocalDate fechaInicio = null;
	private LocalDate fechaFin = null;

	private Parada paradaActual;

	@FXML

	public void initialize() {
		paradaActual = sesionService.getParadaActual();

		desdeDatePicker.valueProperty().addListener((obs, oldDate, newDate) -> actualizarTablaConFechas());
		hastaDatePicker.valueProperty().addListener((obs, oldDate, newDate) -> actualizarTablaConFechas());
		;

		ayudaIcon.setImage(new Image(getClass().getResourceAsStream("/images/help.png")));

		volverMenuLink.setOnAction(event -> volverMenu());
		limpiarButton.setOnAction(event -> limpiarFormulario());

		exportarButton.setOnAction(event -> {
			exportarDatosParadaXML(paradaActual);
			exportarParadaPDF(paradaActual);
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
						exportarDatosParadaXML(paradaActual);
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

	public void exportarDatosParadaXML(Parada parada) {
		fechaInicio = desdeDatePicker.getValue();
		fechaFin = hastaDatePicker.getValue();

		if (fechaInicio == null || fechaFin == null) {
			alertsView.mostrarError("Error", "Las fechas no pueden estar vacías.");
			return;
		}
		if (fechaFin.isBefore(fechaInicio)) {
			alertsView.mostrarError("Error", "La fecha de fin no puede ser anterior a la fecha de inicio.");
			return;
		}

		if (parada == null) {
			alertsView.mostrarError("Error", "No hay una parada seleccionada.");
			return;
		}

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
				alertsView.mostrarInfo("Éxito", "Datos exportados correctamente en: " + fichero);
			}

		} catch (ParserConfigurationException | IOException | TransformerException e) {
			System.out.println("Error al exportar los datos: " + e.getMessage());
			e.printStackTrace();
		}
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

	}

	private void actualizarTablaConFechas() {
		LocalDate fechaInicio = desdeDatePicker.getValue();
		LocalDate fechaFin = hastaDatePicker.getValue();

		if (fechaInicio == null || fechaFin == null) {
			return;
		}

		if (fechaFin.isBefore(fechaInicio)) {
			alertsView.mostrarError("Error", "La fecha de fin no puede ser antes a la fecha de inicio.");
			return;
		}

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

	public void exportarParadaPDF(Parada parada) {
		fechaInicio = desdeDatePicker.getValue();
		fechaFin = hastaDatePicker.getValue();

		if (fechaInicio == null || fechaFin == null) {
			alertsView.mostrarError("Error", "Las fechas no pueden estar vacías.");
			return;
		}
		if (fechaFin.isBefore(fechaInicio)) {
			alertsView.mostrarError("Error", "La fecha de fin no puede ser anterior a la fecha de inicio.");
			return;
		}

		List<Estancia> estancias = paradaService.obtenerEstancias(parada.getId());
		List<Estancia> estanciasFiltradas = new ArrayList<>();

		for (Estancia estancia : estancias) {
			if (estancia.getFecha() != null && !estancia.getFecha().isBefore(fechaInicio)
					&& !estancia.getFecha().isAfter(fechaFin)) {
				estanciasFiltradas.add(estancia);
			}
		}
		long totalPeregrinos = estanciasFiltradas.stream().map(estancia -> estancia.getPeregrino().getId()).distinct()
				.count();

		try {
			File reporteFile = new ClassPathResource("ParadaInforme/Parada.jasper").getFile();
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reporteFile);

			List<Map<String, Object>> listaDatos = new ArrayList<>();
			for (Estancia estancia : estanciasFiltradas) {
				Map<String, Object> row = new HashMap<>();
				row.put("nombre", estancia.getPeregrino().getNombre());
				row.put("apellido", estancia.getPeregrino().getApellido());
				row.put("fecha_estancia", java.sql.Date.valueOf(estancia.getFecha()));
				row.put("vip", estancia.isVip());
				row.put("nombre_parada", paradaActual.getNombre());
				row.put("total_peregrinos", totalPeregrinos);
				listaDatos.add(row);
			}

			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaDatos);

			Map<String, Object> parametros = new HashMap<>();
			parametros.put("nombre_parada", parada.getNombre());
			parametros.put("total_peregrinos", (int) totalPeregrinos);

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);

			File carpetaExportacion = new File("src/main/resources/ParadaInforme");
			if (!carpetaExportacion.exists()) {
				carpetaExportacion.mkdirs();
			}

			String nombreArchivo = "Parada_" + parada.getNombre().replaceAll(" ", "_") + "_" + fechaInicio + "_"
					+ fechaFin + ".pdf";
			File archivoPDF = new File(carpetaExportacion, nombreArchivo);

			JasperExportManager.exportReportToPdfFile(jasperPrint, archivoPDF.getAbsolutePath());
			mostrarPDF(archivoPDF);

		} catch (Exception e) {
			e.printStackTrace();
			alertsView.mostrarError("Error", "No se pudo generar el informe: " + e.getMessage());
		}
	}

	public void mostrarPDF(File archivoPDF) {
		try {
			String ruta = archivoPDF.getAbsolutePath();
			String os = System.getProperty("os.name").toLowerCase();

			if (os.contains("win")) {
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + ruta);
			} else if (os.contains("mac")) {
				Runtime.getRuntime().exec("open " + ruta);
			} else if (os.contains("nix") || os.contains("nux")) {
				Runtime.getRuntime().exec("xdg-open " + ruta);
			} else {
				System.out.println("Sistema operativo no soportado para abrir el PDF.");
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error al lanzar el proceso del PDF: " + e.getMessage());
		}
	}

}