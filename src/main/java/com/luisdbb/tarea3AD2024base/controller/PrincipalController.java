package com.luisdbb.tarea3AD2024base.controller;

import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

@Controller
public class PrincipalController {

    @FXML
    private ImageView logo;

    @FXML
    private Button loginBoton;

    @FXML
    private Button regisBoton;

    @FXML
    public void initialize() {
        // Configurar el logo
        logo.setImage(new Image(getClass().getResource("/images/logo.png").toExternalForm()));

        loginBoton.setOnAction(event -> cambiarPantalla("Login.fxml"));

        //regisBoton.setOnAction(event -> cambiarPantalla("RegPeregrino.fxml")));
    }

    
    private void cambiarPantalla(String nombreFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + nombreFXML));
            Parent root = loader.load();

            Stage stage = (Stage) loginBoton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println("No se pudo cargar la pantalla: " + nombreFXML);
            e.printStackTrace();
        }
    }

}

