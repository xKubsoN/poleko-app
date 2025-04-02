package com.example.xkubson.demoapp.controllers;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileController {
    public List<String> getJsonKeys() {
        List<String> keysList = new ArrayList<>();
        List<String> valueList = new ArrayList<>();
        try {
            File JSONFile = new File("src/main/java/com/example/xkubson/demoapp/controllers/example_1.json");
            String content = Files.readString(JSONFile.toPath());
//            System.out.println(content); //DEBUG
            JSONObject jsonObject = new JSONObject(content);

            Iterator<String> keys = jsonObject.keys();
//            System.out.println("Keys:"); //DEBUG
            while (keys.hasNext()) {
                String key = keys.next();
                keysList.add(key);
//                Object value = jsonObject.get(key); //DEBUG
//                System.out.println(key); //DEBUG
            }
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }
        return keysList;
    }
}
