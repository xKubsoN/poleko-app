package com.example.xkubson.demoapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    private Label debugTest;


    @FXML
    public void onHelloButtonClick() {
        welcomeText.setText("Hello World!");
    }

    @FXML
    public void onFileButtonClick() {
        debugTest.setText("Selected file: ");
    }
}