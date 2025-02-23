package com.luisdbb.tarea3AD2024base.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.repositorios.ServicioRepository;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.ServicioService;
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
    
    
    
    private ObservableList<Parada> paradasSeleccionadas = FXCollections.observableArrayList();
    private ObservableList<Parada> listaParadas;

    @FXML
    public void initialize() {
        ayudaIcon.setImage(new Image(getClass().getResourceAsStream("/images/help.png")));
        cancelarButton.setOnAction(event -> cancelar());
        configurarParadas();
        List<Servicio> servicios = servicioService.obtenerTodosServicios();
        for(Servicio s: servicios) {
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
        List<Servicio> servicios = servicioService.obtenerTodosServicios();
        Long id = (long) (servicios.size() + 1); 

        String nombre = nombreServicioField.getText().trim();
        String precioTexto = precioField.getText().trim();

        if (nombre.isEmpty() || precioTexto.isEmpty()) {
            alertsView.mostrarError("Error", "Hay campos vacios");
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
            alertsView.mostrarAdvertencia("Error", "Debes introducir numeros en el precio");
            return;
        }

        if (paradasSeleccionadas.isEmpty()) {
            alertsView.mostrarAdvertencia("Advertencia", "Debes seleccionar por lo menos una parada");
            return;
        }

        Servicio servicio = new Servicio(id, nombre, precio);
        servicioService.añadirServicioParada(servicio, paradasSeleccionadas);

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
    }

}
