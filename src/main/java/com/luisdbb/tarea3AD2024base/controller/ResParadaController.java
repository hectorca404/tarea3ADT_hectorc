package com.luisdbb.tarea3AD2024base.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.SpringFXMLLoader;

@Controller
public class ResParadaController {

    @FXML
    private Button exportarDatosButton;

    @FXML
    private Button sellarAlojarButton;

    @FXML
    private Button cerrarSesionButton;

    @FXML
    private ImageView exportarIcon;

    @FXML
    private ImageView sellarIcon;

    @FXML
    private ImageView cerrarSesionIcon;
    
    @Autowired
    private SpringFXMLLoader springFXMLLoader;

    @FXML
    public void initialize() {
        try {
            exportarIcon.setImage(new Image(getClass().getResourceAsStream("/images/exportar.png")));
            sellarIcon.setImage(new Image(getClass().getResourceAsStream("/images/sellar.png")));
            cerrarSesionIcon.setImage(new Image(getClass().getResourceAsStream("/images/cerrarSesion.png")));
            
            cerrarSesionButton.setOnAction(event -> cambiarVentana("/fxml/Principa.fxml"));
        } catch (Exception e) {
            System.out.println("Error al inicializar el ResParadaController: " + e.getMessage());
        }
    }
    
    private void cambiarVentana(String fxmlPath) {
        try {
            Parent root = springFXMLLoader.load(fxmlPath);
            Stage stage = (Stage) cerrarSesionButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println("Error al cerrar sesion: " + e.getMessage());
        }
    }

}
