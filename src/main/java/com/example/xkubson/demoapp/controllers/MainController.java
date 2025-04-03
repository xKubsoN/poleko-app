package com.example.xkubson.demoapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class MainController {
    @FXML private Label debugTest;
    @FXML private VBox buttonContainer;
    @FXML private VBox textContainer;
    @FXML private VBox textContainerChild;
    @FXML private HBox controlButtonContainer;
    @FXML private Button returnButton;
    @FXML private Button homeButton;
    @FXML private Button clearButton;
    @FXML private Button fileSelectButton;
    @FXML private ScrollPane keyScroll;
    @FXML private ScrollPane textScroll;
    @FXML private ImageView imageDisplay;

    private FileController fileController;
    private Map<String, String> keyValueMap;
    private Map<String, List<String>> keyChildrenMap;
    private Stack<String> navigationStack;

    public void initialize() {
        fileController = new FileController();
        keyValueMap = fileController.getKeyValueMap();
        keyChildrenMap = fileController.getKeyChildrenMap();
        navigationStack = new Stack<>();
        navigationStack.push("");
        showPrimaryKeys();
    }

    public boolean jsonValidation(String json) {
        try {
            new JSONObject(json);
            return true;
        } catch (JSONException e) {
            try {
                new JSONArray(json);
                return true;
            } catch (JSONException e2) {
                return false;
            }
        }
    }

    @FXML
    public void onFileSelectButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select JSON File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        Stage stage = (Stage) fileSelectButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                String content = Files.readString(selectedFile.toPath());
                if (!jsonValidation(content)) {
                    return;
                }
                fileController.loadJsonKeyValuePairs(selectedFile);
                keyValueMap = fileController.getKeyValueMap();
                keyChildrenMap = fileController.getKeyChildrenMap();
                navigationStack.clear();
                navigationStack.push("");
                showPrimaryKeys();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    public void onReturnButtonClick() {
        if (navigationStack.size() > 1) {
            navigationStack.pop();
            String parentKey = navigationStack.peek();
            showKeysForLevel(parentKey);
        } else {
            showPrimaryKeys();
        }
    }

    @FXML
    public void onHomeButtonClick() {
        navigationStack.clear();
        navigationStack.push("");
        showPrimaryKeys();
    }

    @FXML
    public void onClearButtonClick() {
        debugTest.setText("");
    }

    private void showPrimaryKeys() {
        buttonContainer.getChildren().clear();
        List<String> primaryKeys = keyChildrenMap.getOrDefault("", List.of());

        if (!primaryKeys.isEmpty()) {
            for (String key : primaryKeys) {
                addKeyButton(key);
            }
        }
    }

    private void showKeysForLevel(String parentKey) {
        buttonContainer.getChildren().clear();
        List<String> children = keyChildrenMap.getOrDefault(parentKey, List.of());

        if (children.isEmpty()) {
            debugTest.setText("No children keys for: " + parentKey);
        } else {
            for (String child : children) {
                addKeyButton(child);
            }
        }
    }

    private void addKeyButton(String key) {
        String buttonText = key.matches("\\[\\d+\\]") ? "Array item " + key : "Key: " + key;
        Button keyButton = new Button(buttonText);
        keyButton.setOnAction(event -> {
            if (keyChildrenMap.containsKey(key)) {
                navigationStack.push(key);
                showKeysForLevel(key);
                debugTest.setText(getAllContentUnderKey(key));
            } else {
                String value = keyValueMap.get(key);
                debugTest.setText("Value: " + value);
            }
        });
        buttonContainer.getChildren().add(keyButton);
    }

    private String getAllContentUnderKey(String parentKey) {
        StringBuilder content = new StringBuilder();
        Set<String> processedKeys = new HashSet<>();
        collectContent(parentKey, "", content, processedKeys);
        return content.toString();
    }

    private void collectContent(String currentKey, String indent, StringBuilder content, Set<String> processedKeys) {
        if (processedKeys.contains(currentKey)) {
            return;
        }
        processedKeys.add(currentKey);
        if (keyValueMap.containsKey(currentKey)) {
            content.append(indent).append(currentKey).append(": ").append(keyValueMap.get(currentKey)).append("\n");
        }
        List<String> children = keyChildrenMap.getOrDefault(currentKey, List.of());
        for (String child : children) {
            collectContent(child, indent + "  ", content, processedKeys);
        }
    }
}