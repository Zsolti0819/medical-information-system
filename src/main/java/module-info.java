module com.example.medis {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;


    opens com.example.medis to javafx.fxml;
    exports com.example.medis.Entity;
    opens com.example.medis.Entity to javafx.fxml;
    exports com.example.medis;
    exports com.example.medis.Controller;
    opens com.example.medis.Controller to javafx.fxml;
    exports com.example.medis.Model;
    opens com.example.medis.Model to javafx.fxml;
}