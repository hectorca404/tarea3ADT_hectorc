package com.luisdbb.tarea3AD2024base.controller;

import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

@Controller
public class LoginController {

    @FXML
    private Button volverBoton;

    @FXML
    private ImageView volverIcon;

    @FXML
    private TextField userField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button ojoBoton;

    @FXML
    private ImageView ojoIcon;

    private boolean passwordVisible = false;

    @FXML
    public void initialize() {
        try {
            // Configurar imágenes iniciales
            volverIcon.setImage(new Image(getClass().getResourceAsStream("/images/volver.png")));
            ojoIcon.setImage(new Image(getClass().getResourceAsStream("/images/ceerrado.png")));

            // Configurar eventos de los botones
            volverBoton.setOnAction(event -> volverAPrincipal());
            ojoBoton.setOnAction(event -> visibilidad());
        } catch (Exception e) {
            System.out.println("Error al inicializar el controlador: " + e.getMessage());
        }
    }

    private void visibilidad() {
        passwordVisible = !passwordVisible;
        if (passwordVisible) {
            passwordField.setPromptText(passwordField.getText());
            passwordField.clear();
            ojoIcon.setImage(new Image(getClass().getResourceAsStream("/images/abierto.png")));
        } else {
        	passwordField.setText(passwordField.getText());
            ojoIcon.setImage(new Image(getClass().getResourceAsStream("/images/ceerrado.png")));
        }
    }

 
    private void volverAPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Principal.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) volverBoton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            System.out.println("Error al volver a la ventana principal: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


