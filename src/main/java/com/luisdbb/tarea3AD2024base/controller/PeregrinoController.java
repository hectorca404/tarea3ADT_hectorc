package com.luisdbb.tarea3AD2024base.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

@Controller
public class PeregrinoController {

    @FXML
    private Button exportarCarnetButton;

    @FXML
    private Button editarContactoButton;

    @FXML
    private Button cerrarSesionButton;

    @FXML
    private ImageView exportarIcon;

    @FXML
    private ImageView editarContactoIcon;

    @FXML
    private ImageView cerrarSesionIcon;

    @FXML
    public void initialize() {
        try {
            exportarIcon.setImage(new Image(getClass().getResourceAsStream("/images/exportar.png")));
            editarContactoIcon.setImage(new Image(getClass().getResourceAsStream("/images/editarUsuario.png")));
            cerrarSesionIcon.setImage(new Image(getClass().getResourceAsStream("/images/cerrarSesion.png")));
            
        } catch (Exception e) {
            System.out.println("Error al inicializar PeregrinoController: " + e.getMessage());
        }
    }


}
