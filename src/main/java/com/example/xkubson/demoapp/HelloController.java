package com.example.xkubson.demoapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import java.io.File;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private Label debugTest;

    private FileChooser fileChooser;

    public HelloController() {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json")
        );
    }

    @FXML
    public void onHelloButtonClick() {
        welcomeText.setText("Hello World!");
    }

    @FXML
    public void onFileButtonClick() {
        try {
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                debugTest.setText("Selected file: " + selectedFile.getName());
            } else {
                debugTest.setText("No file selected");
            }
        } catch (Exception e) {
            debugTest.setText("Error: " + e.getMessage());
        }
    }
}