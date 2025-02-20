package com.luisdbb.tarea3AD2024base.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Credenciales;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.ParadasPeregrinos;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Perfil;
import com.luisdbb.tarea3AD2024base.services.AyudaService;
import com.luisdbb.tarea3AD2024base.services.CredencialesService;
import com.luisdbb.tarea3AD2024base.services.EstanciaService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.SesionService;
import com.luisdbb.tarea3AD2024base.services.ValidacionesService;
import com.luisdbb.tarea3AD2024base.utils.UIUtils;
import com.luisdbb.tarea3AD2024base.view.AlertsView;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


@Controller
public class SellarAlojarController {

	@FXML
	private ComboBox<String> peregrinoComboBox;
	@FXML
	private ComboBox<String> serviciosComboBox;

	@FXML
	private CheckBox alojarCheckBox;

	@FXML
	private CheckBox vipCheckBox;

	@FXML
	private Button confirmarButton;

	@FXML
	private Button limpiarButton;

	@FXML
	private Hyperlink volverMenuLink;

	@FXML
	private Button ayudaButton;

	@Autowired
	private CredencialesService credencialesService;

	@FXML
	private ImageView ayudaIcon;

	@Autowired
	private PeregrinoService peregrinoService;
	
	@Autowired
	private SesionService sesionService;
	
	@Autowired
	private ParadaService paradaService;

	@Autowired
	private EstanciaService estanciaService;

	@Autowired
	private AyudaService ayudaService;

	@Autowired
	private AlertsView alertsView;

	@Autowired
	private ValidacionesService validacionesService;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@FXML 
	private ToggleGroup modoPagoGroup;
	
	@FXML
	private RadioButton efectivoRadio;
	
	@FXML 
	private RadioButton tarjetaRadio;
	
	@FXML 
	private RadioButton bizumRadio;

	@FXML 
	private CheckBox envioCheckBox;
	
	@FXML
	private TextField pesoField;
	
	@FXML
	private TextField volumenX;
	
	@FXML
	private TextField volumenY;
	
	@FXML
	private TextField volumenZ;
	
	@FXML
	private TextField direccionField;
	
	@FXML
	private TextField localidadField;
	
	@FXML 
	private CheckBox urgenteCheckBox;
	
	
	private ObservableList<String> serviciosSeleccionados = FXCollections.observableArrayList();
	
	//EJEMPLO PROVISIONARL METEMOS SERVICIOS A PELO (AUN NO SE HAN CREADO)
	private ObservableList<String> listaServicios = FXCollections.observableArrayList("WiFi", "Desayuno", "Parking", "Gimnasio");

	

	@FXML
	public void initialize() {
	    configurarModoPago();
	    configurarServicios();
	    configurarEventos();
	    configurarBotones();
	    cargarPeregrinosComboBox();
	    configurarAtajos();
	}

	private void configurarModoPago() {
	    modoPagoGroup = new ToggleGroup();
	    efectivoRadio.setToggleGroup(modoPagoGroup);
	    tarjetaRadio.setToggleGroup(modoPagoGroup);
	    bizumRadio.setToggleGroup(modoPagoGroup);
	}

	private void configurarServicios() {
	    UIUtils.configurarServiciosComboBox(serviciosComboBox, listaServicios);
	}

	private void configurarEventos() {
	    alojarCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
	        actualizarEstadoCampos(newValue);
	    });

	    envioCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
	        UIUtils.setEstadoCamposEnvio(newValue, pesoField, volumenX, volumenY, volumenZ, direccionField, localidadField, urgenteCheckBox);
	    });
	}

	private void actualizarEstadoCampos(boolean alojar) {
	    boolean activar = alojar;

	    serviciosComboBox.setDisable(!activar);
	    vipCheckBox.setDisable(!activar);
	    envioCheckBox.setDisable(!activar);
	    efectivoRadio.setDisable(!activar);
	    tarjetaRadio.setDisable(!activar);
	    bizumRadio.setDisable(!activar);

	    if (!activar) {
	        modoPagoGroup.selectToggle(null);
	        envioCheckBox.setSelected(false);
	    }

	    UIUtils.setEstadoCamposEnvio(activar, pesoField, volumenX, volumenY, volumenZ, direccionField, localidadField, urgenteCheckBox);
	}

	private void configurarBotones() {
	    ayudaIcon.setImage(new Image(getClass().getResourceAsStream("/images/help.png")));
	    ayudaButton.setOnAction(event -> ayudaService.mostrarAyuda("/help/SellarAlojar.html"));
	    volverMenuLink.setOnAction(event -> volverLogin());
	    limpiarButton.setOnAction(event -> limpiarFormulario());
	    confirmarButton.setOnAction(event -> sellarAlojar());
	}


	private void configurarAtajos() {
		confirmarButton.sceneProperty().addListener((observable, oldScene, newScene) -> {
			if (oldScene != null) {
				oldScene.setOnKeyPressed(null);
			}
			if (newScene != null) {
				newScene.setOnKeyPressed(event -> {
					switch (event.getCode()) {
					case ENTER -> {
						event.consume();
						sellarAlojar();
					}
					case F1 -> {
						event.consume();
						ayudaService.mostrarAyuda("/help/SellarAlojar.html");
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
		stageManager.switchScene(FxmlView.RESPARADA);
	}

	private void limpiarFormulario() {
		peregrinoComboBox.getSelectionModel().clearSelection();
		alojarCheckBox.setSelected(false);
		vipCheckBox.setSelected(false);
		serviciosComboBox.getSelectionModel().clearSelection();
	}

	private List<Credenciales> cargarPeregrinos() {
		List<Credenciales> credenciales = credencialesService.obtenerUsuarios(Perfil.PEREGRINO);
		return credenciales;
	}

	private void cargarPeregrinosComboBox() {
		List<Credenciales> credenciales = cargarPeregrinos();

		ObservableList<String> peregrinoNombres = FXCollections.observableArrayList();
		for (Credenciales credencial : credenciales) {
			peregrinoNombres.add(credencial.getNombreUsuario());
		}

		peregrinoComboBox.setItems(peregrinoNombres);
	}

	private void sellarAlojar() {
		String nombreUsuarioSeleccionado = peregrinoComboBox.getSelectionModel().getSelectedItem();

		if (nombreUsuarioSeleccionado == null) {
			alertsView.mostrarError("Error", "Hay que seleccionar un usuario.");
			return;
		}

		Optional<Credenciales> credencialesOp = credencialesService
				.obtenerCredencialPorUsuario(nombreUsuarioSeleccionado);

		if (credencialesOp.isEmpty()) {
			alertsView.mostrarError("Error", "Usuario no encontrado.");
			return;
		}

		Credenciales credenciales = credencialesOp.get();
		Peregrino peregrino = credenciales.getPeregrino();
		Parada paradaActual = sesionService.getParadaActual();
		LocalDate fechaHoy = LocalDate.now();

		boolean yaSellado = validacionesService.yaSelladoHoy(peregrino, paradaActual, fechaHoy);

		boolean yaAlojado = validacionesService.yaAlojoHoy(peregrino, paradaActual, fechaHoy);

		if (yaSellado && !alojarCheckBox.isSelected()) {
			alertsView.mostrarError("Error", "Este peregrino ya ha sellado en esta parada hoy.");
			return;
		}

		if (yaAlojado && alojarCheckBox.isSelected()) {
			alertsView.mostrarError("Error", "Este peregrino ya se ha alojado en esta parada hoy.");
			return;
		}

		Carnet carnet = peregrino.getCarnet();

		if (!yaSellado) {
			carnet.setDistancia(carnet.getDistancia() + 5);
			ParadasPeregrinos paradasPeregrinos = new ParadasPeregrinos(peregrino, paradaActual);
			paradaService.guardarParadasPeregrinos(paradasPeregrinos);
		}

		if (alojarCheckBox.isSelected() && !yaAlojado) {
			boolean esVip = vipCheckBox.isSelected();

			Estancia estancia = new Estancia();
			estancia.setVip(esVip);
			estancia.setParada(paradaActual);
			estancia.setPeregrino(peregrino);

			estanciaService.guardarEstancia(estancia);

			if (esVip) {
				carnet.setnVips(carnet.getnVips() + 1);
			}
		}

		peregrinoService.actualizarCarnet(carnet);

		alertsView.mostrarInfo("Éxito", "Sellado/Alojamiento completado.");
		limpiarFormulario();
	}

}
