package com.example.medis.UserMode;

import com.example.medis.JavaPostgreSql;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NewAppointment implements Initializable {

    @FXML private TextField title_data;
    @FXML private DatePicker start_ymd_data;
    @FXML private ComboBox<String> start_h_data;
    @FXML private ComboBox<String> start_min_data;
    @FXML private DatePicker end_ymd_data;
    @FXML private ComboBox<String> end_h_data;
    @FXML private ComboBox<String> end_min_data;
    @FXML private TextField description_data;
    @FXML private ComboBox <String> doctor_data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        doctor_data.setItems(FXCollections.observableArrayList(JavaPostgreSql.getUsersByPosition("doctor")));
        start_h_data.setItems(FXCollections.observableArrayList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17","18","19","20","21","22","23"));
        start_min_data.setItems(FXCollections.observableArrayList("00", "15", "30", "45"));
        end_h_data.setItems(FXCollections.observableArrayList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17","18","19","20","21","22","23"));
        end_min_data.setItems(FXCollections.observableArrayList("00", "15", "30", "45"));

    }

    @FXML
    private void fetchData() {
        // TO DO
    }

    @FXML
    public void closeCurrentWindow(InputEvent e) {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
