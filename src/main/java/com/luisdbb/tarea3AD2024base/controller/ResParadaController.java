package com.luisdbb.tarea3AD2024base.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

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

    @FXML
    public void initialize() {
        try {
            exportarIcon.setImage(new Image(getClass().getResourceAsStream("/images/exportar.png")));
            sellarIcon.setImage(new Image(getClass().getResourceAsStream("/images/sellar.png")));
            cerrarSesionIcon.setImage(new Image(getClass().getResourceAsStream("/images/cerrarSesion.png")));
        } catch (Exception e) {
            System.out.println("Error al inicializar el ResParadaController: " + e.getMessage());
        }
    }

}
