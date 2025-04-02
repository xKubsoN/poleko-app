package com.example.xkubson.demoapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class MainController {
    @FXML
    private Label debugTest;

    @FXML private VBox buttonContainer;

    private List<String> jsonKeys;

    public void initialize() {
        FileController fileController = new FileController();
        jsonKeys = fileController.getJsonKeys();
    }

    @FXML
    public void onFileButtonClick() {
//        System.out.println(jsonKeys); //DEBUG
        if (jsonKeys != null && !jsonKeys.isEmpty()) {
            debugTest.setText("Keys: " + String.join(", ", jsonKeys));
            buttonContainer.getChildren().clear();
            for (String key : jsonKeys) {
                Button keyButton = new Button(key);
                keyButton.setOnAction(actionEvent -> {
                    debugTest.setText("Clicked button for key: " + key);
                });
                buttonContainer.getChildren().add(keyButton);
            }
        } else {
            debugTest.setText("0 Keys found");
        }
    }
}