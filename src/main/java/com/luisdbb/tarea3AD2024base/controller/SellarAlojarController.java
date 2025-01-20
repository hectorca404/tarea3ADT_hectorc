package com.luisdbb.tarea3AD2024base.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

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

    @FXML
    public void initialize() {
        alojarCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            vipCheckBox.setDisable(!newValue);
            if (!newValue) {
                vipCheckBox.setSelected(false);
            }
        });

        limpiarButton.setOnAction(event -> limpiarFormulario());
    }

    private void limpiarFormulario() {
        peregrinoComboBox.getSelectionModel().clearSelection();
        alojarCheckBox.setSelected(false);
        vipCheckBox.setSelected(false);
    }
}
