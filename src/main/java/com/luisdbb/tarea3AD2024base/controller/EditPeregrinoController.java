package com.luisdbb.tarea3AD2024base.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

@Controller
public class EditPeregrinoController {
	@FXML
	private Button volverMenuButton;
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	@FXML
    public void initialize() {
		volverMenuButton.setOnAction(event -> volverMenu());
    }
	
	private void volverMenu(){
    	stageManager.switchScene(FxmlView.PEREGRINO);
    }
    
}
