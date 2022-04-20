module com.example.medis {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;


    opens com.example.medis to javafx.fxml;
    exports com.example.medis.Entities;
    opens com.example.medis.Entities to javafx.fxml;
    exports com.example.medis.UserMode;
    opens com.example.medis.UserMode to javafx.fxml;
    exports com.example.medis;
    exports com.example.medis.AdminMode;
    opens com.example.medis.AdminMode to javafx.fxml;
}