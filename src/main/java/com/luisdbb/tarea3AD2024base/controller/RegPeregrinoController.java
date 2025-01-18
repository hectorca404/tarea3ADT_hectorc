package com.luisdbb.tarea3AD2024base.controller;
import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

@Controller
public class RegPeregrinoController {
	
	@FXML
	private Hyperlink volverLogin;
	
	
	@FXML
    public void initialize() {
        try {
            volverLogin.setOnAction(event -> volverAPrincipal());
        } catch (Exception e) {
            System.out.println("Error al inicializar el controlador: " + e.getMessage());
        }
    }
	
	
	private void volverAPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Principal.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) volverLogin.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            System.out.println("Error al volver a la ventana principal: " + e.getMessage());
        }
    }
}
