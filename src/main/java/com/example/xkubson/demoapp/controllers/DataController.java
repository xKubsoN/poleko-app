package com.example.xkubson.demoapp.controllers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class DataController {
    private Map<String, String> keyValueMap = new HashMap<>();
    private Map<String, List<String>> keyChildrenMap = new HashMap<>();

    public Map<String, String> getKeyValueMap() {
        return keyValueMap;
    }

    public Map<String, List<String>> getKeyChildrenMap() {
        return keyChildrenMap;
    }

    public void loadJsonKeyValuePairs() {
        keyValueMap.clear();
        keyChildrenMap.clear();

        try {
            File JSONFile = new File("src/main/java/com/example/xkubson/demoapp/controllers/example_1.json");
            String content = Files.readString(JSONFile.toPath());
//            System.out.println("JSON Content: " + content); //debug

            Object root = new org.json.JSONTokener(content).nextValue();
            if (root instanceof JSONArray) {
                processJsonArray((JSONArray) root, "");
            } else if (root instanceof JSONObject) {
                processJsonObject((JSONObject) root, "");
                List<String> rootChildren = new ArrayList<>();
                Iterator<String> keys = ((JSONObject) root).keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    rootChildren.add(key);
                }
                if (!rootChildren.isEmpty()) {
                    keyChildrenMap.put("", rootChildren);
                }
            }

//            System.out.println("After processing - KeyValueMap: " + keyValueMap); //debug
//            System.out.println("After processing - KeyChildrenMap: " + keyChildrenMap); //debug

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void processJsonObject(JSONObject jsonObject, String parentKey) {
        Iterator<String> keys = jsonObject.keys();
        List<String> children = new ArrayList<>();

        while (keys.hasNext()) {
            String key = keys.next();
            String fullKey = parentKey.isEmpty() ? key : parentKey + "." + key;
            Object value = jsonObject.get(key);

            if (value instanceof JSONObject) {
                children.add(fullKey);
                processJsonObject((JSONObject) value, fullKey);
            } else if (value instanceof JSONArray) {
                children.add(fullKey);
                processJsonArray((JSONArray) value, fullKey);
            } else {
                keyValueMap.put(fullKey, value.toString());
                children.add(fullKey);
            }
        }

        if (!children.isEmpty() && !parentKey.isEmpty()) {
            keyChildrenMap.put(parentKey, children);
        }
    }

    private void processJsonArray(JSONArray jsonArray, String parentKey) {
        List<String> children = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            String arrayKey = parentKey.isEmpty() ? "[" + i + "]" : parentKey + "[" + i + "]";
            Object element = jsonArray.get(i);

            if (element instanceof JSONObject) {
                children.add(arrayKey);
                processJsonObject((JSONObject) element, arrayKey);
            } else if (element instanceof JSONArray) {
                children.add(arrayKey);
                processJsonArray((JSONArray) element, arrayKey);
            } else {
                keyValueMap.put(arrayKey, element.toString());
                children.add(arrayKey);
            }
        }

        if (!children.isEmpty()) {
            keyChildrenMap.put(parentKey.isEmpty() ? "" : parentKey, children);
        }
    }
}