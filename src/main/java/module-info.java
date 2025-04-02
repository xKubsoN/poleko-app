module com.example.xkubson.demoapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.json;


    opens com.example.xkubson.demoapp to javafx.fxml;
    exports com.example.xkubson.demoapp;
    exports com.example.xkubson.demoapp.controllers;
    opens com.example.xkubson.demoapp.controllers to javafx.fxml;
}