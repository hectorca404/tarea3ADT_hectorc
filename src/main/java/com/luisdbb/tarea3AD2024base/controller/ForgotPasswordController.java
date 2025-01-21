package com.luisdbb.tarea3AD2024base.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.SpringFXMLLoader;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

@Controller
public class ForgotPasswordController {

    @FXML
    private TextField userField;

    @FXML
    private Button recuperarButton;

    @FXML
    private Hyperlink volverLogin;
    
    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    public void initialize() {
        volverLogin.setOnAction(event -> volverLogin());
    }

    private void volverLogin(){
    	stageManager.switchScene(FxmlView.PRINCIPAL);
    }
}
