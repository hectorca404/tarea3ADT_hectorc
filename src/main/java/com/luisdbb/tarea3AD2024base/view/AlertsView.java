package com.luisdbb.tarea3AD2024base.view;

import org.springframework.stereotype.Component;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

@Component
public class AlertsView {
	


	public void mostrarInfo(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public void mostrarError(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText("Error!!");
		alert.setContentText(message);
		alert.showAndWait();
	}

	public void mostrarAdvertencia(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText("Advertencia");
		alert.setContentText(message);
		alert.showAndWait();
	}

	public boolean mostrarConfirmacion(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText("Confirmación");
		alert.setContentText(message);

		return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
	}
}
