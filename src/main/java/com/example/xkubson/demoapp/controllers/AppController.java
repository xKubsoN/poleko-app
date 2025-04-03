package com.example.xkubson.demoapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AppController {
    @FXML private Label debugTest;
    @FXML public VBox buttonContainer;

    private DataController dataController;
    private Map<String, String> keyValueMap;
    private Map<String, List<String>> keyChildrenMap;

    public void initialize() {
        dataController = new DataController();
        dataController.loadJsonKeyValuePairs();
        keyValueMap = dataController.getKeyValueMap();
        keyChildrenMap = dataController.getKeyChildrenMap();

//        System.out.println("Initialized - KeyValueMap size: " + keyValueMap.size()); //debug
//        System.out.println("Initialized - KeyChildrenMap size: " + keyChildrenMap.size()); //debug
//        System.out.println("KeyValueMap: " + keyValueMap); //debug
//        System.out.println("KeyChildrenMap: " + keyChildrenMap); //debug

        showPrimaryKeys();
    }

//    @FXML
//    public void onFileButtonClick() {
//        System.out.println("onFileButtonClick called"); //debug
//        buttonContainer.getChildren().clear();
//        debugTest.setText("Reset to top-level keys");
//        showPrimaryKeys();
//    }

    private void showPrimaryKeys() {
        buttonContainer.getChildren().clear();
        List<String> primaryKeys = keyChildrenMap.getOrDefault("", List.of()).stream().collect(Collectors.toList());

//        System.out.println("Top-level keys found: " + topLevelKeys); //debug
        if (primaryKeys.isEmpty()) {
            debugTest.setText("No primary keys found");
        }

        for (String key : primaryKeys) {
            addKeyButton(key);
        }
    }

    private void addKeyButton(String key) {
        Button keyButton = new Button(key);
        keyButton.setOnAction(event -> {
//            System.out.println("Clicked key: " + key); //debug
            if (keyChildrenMap.containsKey(key)) {
                buttonContainer.getChildren().clear();
                debugTest.setText("Key: " + key + " (has nested keys)");
                List<String> children = keyChildrenMap.get(key);
//                System.out.println("Children for " + key + ": " + children); //debug
                if (children.isEmpty()) {
                    debugTest.setText("Key: " + key + " (no children found)");
                } else {
                    for (String child : children) {
                        addKeyButton(child);
                    }
                }
            } else {
                String value = keyValueMap.get(key);
                debugTest.setText("Key: " + key + ", Value: " + value);
                buttonContainer.getChildren().clear();
                Button valueButton = new Button(value);
                buttonContainer.getChildren().add(valueButton);
            }
        });
        buttonContainer.getChildren().add(keyButton);
    }
}