package com.luisdbb.tarea3AD2024base.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

@Controller
public class ForgotPasswordController {

    @FXML
    private TextField userField;

    @FXML
    private Button recuperarButton;

    @FXML
    private Hyperlink volverLogin;

    @FXML
    public void initialize() {
        volverLogin.setOnAction(event -> cambiarVentana("Principal.fxml"));
    }

    private void cambiarVentana(String nombreFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + nombreFXML));
            Parent root = loader.load();
            Stage stage = (Stage) volverLogin.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            System.out.println("Error al cargar la ventana: " + e.getMessage());
        }
    }
}
