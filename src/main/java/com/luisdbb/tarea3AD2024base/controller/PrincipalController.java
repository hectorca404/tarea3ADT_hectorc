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
public class PrincipalController {

    @FXML
    private ImageView logo;

    @FXML
    private TextField userLogField;

    @FXML
    private PasswordField passField;

    @FXML
    private Button passButton;

    @FXML
    private ImageView togglePasswordIcon;

    @FXML
    private Button logButton;

    @FXML
    private Hyperlink forgotPass;

    @FXML
    private Hyperlink regisLink;

    private boolean contraseñaVisible = false;

    @FXML
    public void initialize() {
        try {
            logo.setImage(new Image(getClass().getResourceAsStream("/images/logo2.png")));
            togglePasswordIcon.setImage(new Image(getClass().getResourceAsStream("/images/ceerrado.png")));

            passButton.setOnAction(event -> visualizarContraseña());
            forgotPass.setOnAction(event -> cambiarVentana("/fxml/ForgotPass.fxml"));
            regisLink.setOnAction(event -> cambiarVentana("/fxml/RegPeregrino.fxml"));

        } catch (Exception e) {
            System.out.println("Error al inicializar el controlador: " + e.getMessage());
        }
    }

    private void visualizarContraseña() {
        contraseñaVisible = !contraseñaVisible;

        if (contraseñaVisible) {
            passField.setPromptText(passField.getText());
            passField.clear();
            togglePasswordIcon.setImage(new Image(getClass().getResourceAsStream("/images/abierto.png")));
        } else {
            passField.setPromptText("Introduce tu contraseña");
            togglePasswordIcon.setImage(new Image(getClass().getResourceAsStream("/images/ceerrado.png")));
        }
    }

    private void cambiarVentana(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) logButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println("Error al cambiar de ventana: " + e.getMessage());
        }
    }
}

