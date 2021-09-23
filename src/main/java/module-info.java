module com.example.zaidimas {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.zaidimas to javafx.fxml;
    exports com.example.zaidimas;
}