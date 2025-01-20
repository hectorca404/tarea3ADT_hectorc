package com.luisdbb.tarea3AD2024base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.SpringFXMLLoader;
import com.luisdbb.tarea3AD2024base.services.CredencialesService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@Controller
public class CrearParadaController {

    @FXML
    private TextField responsableField;

    @FXML
    private TextField correoField;

    @FXML
    private TextField nombreParadaField;

    @FXML
    private TextField regionField;
    @FXML
    private TextField usuarioField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button crearButton;

    @FXML
    private Button limpiarButton;

    @FXML
    private Button volverMenuButton;
    
    @Autowired
    private SpringFXMLLoader springFXMLLoader;
    
    @Autowired
    private ParadaService paradaService;

    @FXML
    public void initialize() {
    	crearButton.setOnAction(event -> registrarParada());
        limpiarButton.setOnAction(event -> limpiarFormulario());
        volverMenuButton.setOnAction(event -> cambiarVentana("/fxml/Admin.fxml"));
    }
    
    private void registrarParada() {
        try {
            String nombre = nombreParadaField.getText();
            char region = regionField.getText().charAt(0);
            String responsable = responsableField.getText();
            String usuario = usuarioField.getText();
            String correo = correoField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if (!password.equals(confirmPassword)) {
                mostrarAlerta("Error", "Las contraseñas no coinciden", Alert.AlertType.ERROR);
                return;
            }

            paradaService.registrarParada(nombre, region, responsable, usuario ,correo, password);
            mostrarAlerta("Exitoso", "Parada registrada correctamente", Alert.AlertType.INFORMATION);
            limpiarFormulario();
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void limpiarFormulario() {
        responsableField.clear();
        correoField.clear();
        usuarioField.clear();
        nombreParadaField.clear();
        regionField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
    }

    private void cambiarVentana(String fxmlPath) {
        try {
            Parent root = springFXMLLoader.load(fxmlPath);
            Stage stage = (Stage) volverMenuButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println("Error al cerrar sesion: " + e.getMessage());
        }
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}

