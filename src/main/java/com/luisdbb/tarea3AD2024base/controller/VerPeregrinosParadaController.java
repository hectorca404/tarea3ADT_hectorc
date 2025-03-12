package com.luisdbb.tarea3AD2024base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@Controller
public class VerPeregrinosParadaController {

    @FXML
    private Hyperlink volverMenulink;

    @FXML
    private ImageView ayudaIcon;

    @FXML
    private Button ayudaButton;

    @FXML
    private TableView<Peregrino> perTableView;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    public void initialize() {
        ayudaIcon.setImage(new Image(getClass().getResourceAsStream("/images/help.png")));

        volverMenulink.setOnAction(event -> volverMenu());
;
    }

    private void volverMenu() {
        stageManager.switchScene(FxmlView.RESPARADA);
    }

 
}