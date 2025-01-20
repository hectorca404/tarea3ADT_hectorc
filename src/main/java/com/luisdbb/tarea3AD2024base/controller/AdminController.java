package com.luisdbb.tarea3AD2024base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.SpringFXMLLoader;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

@Controller
public class AdminController {

    @FXML
    private Button crearParadaButton;

    @FXML
    private Button cerrarSesionButton;

    @FXML
    private ImageView crearParadaIcon;

    @FXML
    private ImageView cerrarSesionIcon;
    
    @Autowired
    private SpringFXMLLoader springFXMLLoader;

    @FXML
    public void initialize() {
        try {
            crearParadaIcon.setImage(new Image(getClass().getResourceAsStream("/images/parada.png")));
            cerrarSesionIcon.setImage(new Image(getClass().getResourceAsStream("/images/cerrarSesion.png")));

            crearParadaButton.setOnAction(event -> cambiarVentana("/fxml/CrearParada.fxml"));
            cerrarSesionButton.setOnAction(event -> cambiarVentana("/fxml/Principal.fxml"));
        } catch (Exception e) {
            System.out.println("Error al iniciar AdminController: " + e.getMessage());
        }
    }


    private void cambiarVentana(String fxmlPath) {
        try {
            Parent root = springFXMLLoader.load(fxmlPath);
            Stage stage = (Stage) cerrarSesionButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println("Error al cambiar de ventana: " + e.getMessage());
        }
    }


}


