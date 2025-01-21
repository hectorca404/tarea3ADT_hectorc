package com.luisdbb.tarea3AD2024base.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.SpringFXMLLoader;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

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
    
    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    public void initialize() {
        try {
            exportarIcon.setImage(new Image(getClass().getResourceAsStream("/images/exportar.png")));
            sellarIcon.setImage(new Image(getClass().getResourceAsStream("/images/sellar.png")));
            cerrarSesionIcon.setImage(new Image(getClass().getResourceAsStream("/images/cerrarSesion.png")));
            
            cerrarSesionButton.setOnAction(event -> volverLogin());
            sellarAlojarButton.setOnAction(event -> sellarAlojar());
        } catch (Exception e) {
            System.out.println("Error al inicializar el ResParadaController: " + e.getMessage());
        }
    }
    
    
    private void volverLogin(){
    	stageManager.switchScene(FxmlView.PRINCIPAL);
    }
    
    private void sellarAlojar(){
    	stageManager.switchScene(FxmlView.SELLARALOJAR);
    }
    

}
