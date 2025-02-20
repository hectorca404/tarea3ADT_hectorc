package com.luisdbb.tarea3AD2024base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.view.FxmlView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@Controller
public class EditarServicioController {

    @FXML
    private ComboBox<String> servicioComboBox;
    
    @FXML 
    private TextField nombreServicioField;
    
    @FXML 
    private TextField precioField;
    
    @FXML 
    private ComboBox<String> paradasComboBox;
    
    @FXML 
    private Label numParadasLabel;
    
    @FXML
    private Button guardarButton;
    
    @FXML 
    private Button cancelarButton;
    
    @FXML 
    private Button ayudaButton;
    
    @FXML
    private ImageView ayudaIcon;

    @Lazy
    @Autowired private StageManager stageManager;

    @FXML
    public void initialize() {
        ayudaIcon.setImage(new Image(getClass().getResourceAsStream("/images/help.png")));
        
        cancelarButton.setOnAction(event -> cancelar());

    }


   
    private void cancelar() {
        stageManager.switchScene(FxmlView.MENUSERVICIOS);
    }

   
}
