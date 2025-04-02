package com.example.xkubson.demoapp.controllers;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;

public class FileController {
    public static void main(String[] args) {
        try {
            File JSONFile = new File("src/main/java/com/example/xkubson/demoapp/controllers/example_1.json");
            String content = Files.readString(JSONFile.toPath());
            System.out.println(content);
            JSONObject jsonObject = new JSONObject(content);

            Iterator<String> keys = jsonObject.keys();
            System.out.println("Keys:");
            while (keys.hasNext()) {
                String key = keys.next();
                Object value = jsonObject.get(key);
                System.out.println(key + ": " + value);
            }
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }
}
