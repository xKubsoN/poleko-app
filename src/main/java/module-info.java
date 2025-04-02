module com.example.xkubson.demoapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.xkubson.demoapp to javafx.fxml;
    exports com.example.xkubson.demoapp;
}