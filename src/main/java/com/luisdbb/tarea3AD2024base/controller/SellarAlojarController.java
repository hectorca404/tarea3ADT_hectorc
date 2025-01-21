package com.luisdbb.tarea3AD2024base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

@Controller
public class SellarAlojarController {

    @FXML
    private ComboBox<String> peregrinoComboBox;

    @FXML
    private CheckBox alojarCheckBox;

    @FXML
    private CheckBox vipCheckBox;

    @FXML
    private Button confirmarButton;

    @FXML
    private Button limpiarButton;

    @FXML
    private Button volverMenuButton;
    
    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    public void initialize() {
        alojarCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            vipCheckBox.setDisable(!newValue);
            if (!newValue) {
                vipCheckBox.setSelected(false);
            }
        });
        volverMenuButton.setOnAction(event -> volverLogin());

        limpiarButton.setOnAction(event -> limpiarFormulario());
    }
    
    private void volverLogin(){
    	stageManager.switchScene(FxmlView.RESPARADA);
    }

    private void limpiarFormulario() {
        peregrinoComboBox.getSelectionModel().clearSelection();
        alojarCheckBox.setSelected(false);
        vipCheckBox.setSelected(false);
    }
}
