package com.luisdbb.tarea3AD2024base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.SpringFXMLLoader;
import com.luisdbb.tarea3AD2024base.modelo.Credenciales;
import com.luisdbb.tarea3AD2024base.modelo.Perfil;
import com.luisdbb.tarea3AD2024base.services.CredencialesService;

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
    private ImageView ojoIcon;

    @FXML
    private Button logButton;

    @FXML
    private Hyperlink forgotPass;

    @FXML
    private Hyperlink regisLink;

    private boolean contraseñaVisible = false;
    
    @Autowired
    private CredencialesService credencialesService;
    
    @Autowired
    private SpringFXMLLoader springFXMLLoader;

    @FXML
    public void initialize() {
        try {
            logo.setImage(new Image(getClass().getResourceAsStream("/images/logo2.png")));
            ojoIcon.setImage(new Image(getClass().getResourceAsStream("/images/ceerrado.png")));

            passButton.setOnAction(event -> visualizarContraseña());
            forgotPass.setOnAction(event -> cambiarVentana("/fxml/ForgotPass.fxml"));
            regisLink.setOnAction(event -> cambiarVentana("/fxml/RegPeregrino.fxml"));
            
            logButton.setOnAction(event -> iniciarSesion());

        } catch (Exception e) {
            System.out.println("Error al iniciar el PrncipalController: " + e.getMessage());
        }
    }
    
    
    private void iniciarSesion() {
        String username = userLogField.getText();
        String password = passField.getText();

        try {
            Credenciales credenciales = credencialesService.validarCredenciales(username, password);
            vistaSegunRol(credenciales.getPerfil());
        } catch (IllegalArgumentException e) {
            mostrarError("Credenciales invalidas");
        }
    }
    
    
    private void vistaSegunRol(Perfil perfil) {
        switch (perfil) {
            case ADMINISTRADOR -> cambiarVentana("/fxml/Admin.fxml");
            case PEREGRINO -> cambiarVentana("/fxml/Peregrino.fxml");
            case PARADA -> cambiarVentana("/fxml/ResParada.fxml");
            default -> mostrarError("usuario no existe");
        }
    }
    
    private void mostrarError(String mensaje) {
    	System.out.println(mensaje);
    }

    private void visualizarContraseña() {
        contraseñaVisible = !contraseñaVisible;

        if (contraseñaVisible) {
            passField.setPromptText(passField.getText());
            passField.clear();
            ojoIcon.setImage(new Image(getClass().getResourceAsStream("/images/abierto.png")));
        } else {
            passField.setPromptText("Introduce tu contraseña");
            ojoIcon.setImage(new Image(getClass().getResourceAsStream("/images/ceerrado.png")));
        }
    }

    private void cambiarVentana(String fxmlPath) {
        try {
            Parent root = springFXMLLoader.load(fxmlPath);

            Stage stage = (Stage) logButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println("Error al cambiar de ventana: " + e.getMessage());
        }
    }
}

