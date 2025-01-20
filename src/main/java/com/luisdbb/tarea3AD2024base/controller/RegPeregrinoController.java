package com.luisdbb.tarea3AD2024base.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.SpringFXMLLoader;

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
	
	@Autowired
	private SpringFXMLLoader springFXMLLoader;
	
	
	@FXML
    public void initialize() {
        try {
            volverLogin.setOnAction(event -> cambiarVentana("/fxml/Principal.fxml"));
        } catch (Exception e) {
            System.out.println("Error al inicializar el controlador: " + e.getMessage());
        }
    }
	
	
	private void cambiarVentana(String fxmlPath) {
	    try {
	        Parent root = springFXMLLoader.load(fxmlPath);
	        Stage stage = (Stage) volverLogin.getScene().getWindow();
	        stage.setScene(new Scene(root));
	        stage.show();
	    } catch (Exception e) {
	        System.out.println("Error al cerrar sesión: " + e.getMessage());
	    }
	}
}
