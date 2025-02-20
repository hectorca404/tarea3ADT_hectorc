package com.luisdbb.tarea3AD2024base.utils;

import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;

public class UIUtils {

    public static void setEstadoCamposEnvio(boolean activar, TextField pesoField, TextField volumenX, TextField volumenY, TextField volumenZ, TextField direccionField, TextField localidadField, CheckBox urgenteCheckBox) {
    	
        pesoField.setDisable(!activar);
        volumenX.setDisable(!activar);
        volumenY.setDisable(!activar);
        volumenZ.setDisable(!activar);
        direccionField.setDisable(!activar);
        localidadField.setDisable(!activar);
        urgenteCheckBox.setDisable(!activar);
    }

    public static void configurarServiciosComboBox(ComboBox<String> serviciosComboBox, ObservableList<String> listaServicios) {
        serviciosComboBox.setItems(listaServicios);

        serviciosComboBox.setCellFactory(param -> new ListCell<>() {
            private final CheckBox checkBox = new CheckBox();

            {
                checkBox.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_PRESSED, event -> {
                    event.consume();
                    checkBox.setSelected(!checkBox.isSelected());
                });
            }

            @Override
            protected void updateItem(String servicio, boolean empty) {
                super.updateItem(servicio, empty);
                if (empty || servicio == null) {
                    setGraphic(null);
                } else {
                    checkBox.setText(servicio);
                    setGraphic(checkBox);
                }
            }
        });

        serviciosComboBox.getSelectionModel().clearSelection();
        serviciosComboBox.setPromptText("Selecciona un servicio");
    }


}




