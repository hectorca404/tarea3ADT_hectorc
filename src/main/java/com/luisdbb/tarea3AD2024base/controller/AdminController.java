package com.luisdbb.tarea3AD2024base.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

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

    @FXML
    public void initialize() {
        try {
            crearParadaIcon.setImage(new Image(getClass().getResourceAsStream("/images/crearParada.png")));
            cerrarSesionIcon.setImage(new Image(getClass().getResourceAsStream("/images/cerrarSesion.png")));
        } catch (Exception e) {
            System.out.println("Error al inicializar AdminController: " + e.getMessage());
        }
    }

}

