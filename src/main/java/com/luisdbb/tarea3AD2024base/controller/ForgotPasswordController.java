package com.luisdbb.tarea3AD2024base.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.SpringFXMLLoader;

@Controller
public class ForgotPasswordController {

    @FXML
    private TextField userField;

    @FXML
    private Button recuperarButton;

    @FXML
    private Hyperlink volverLogin;
    
    @Autowired
    private SpringFXMLLoader springFXMLLoader;

    @FXML
    public void initialize() {
        volverLogin.setOnAction(event -> cambiarVentana("/fxml/Principal.fxml"));
    }

    private void cambiarVentana(String fxmlPath) {
        try {
            Parent root = springFXMLLoader.load(fxmlPath);
            Stage stage = (Stage) volverLogin.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println("Error al cerrar sesion: " + e.getMessage());
        }
    }
}
