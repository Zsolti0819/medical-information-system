module com.example.medis {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.medis to javafx.fxml;
    exports com.example.medis;
}