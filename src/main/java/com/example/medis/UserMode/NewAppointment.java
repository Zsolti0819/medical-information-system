package com.example.medis.UserMode;

import com.example.medis.Entities.Patient;
import com.example.medis.JavaPostgreSql;
import com.example.medis.SceneController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewAppointment implements Initializable {

    private Patient selectedPatient;

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

    // created_by is constant, needs to be implemented later

    @FXML
    private void fetchData(ActionEvent event) throws IOException {
        JavaPostgreSql.creteAppointment(
                title_data.getText(),
                description_data.getText(),
                start_ymd_data.getValue().toString()+" " + start_h_data.getValue() + ":" + start_min_data.getValue(),
                end_ymd_data.getValue().toString() + " "  + end_h_data.getValue()+":" + end_min_data.getValue(),
                selectedPatient.getId(),
                JavaPostgreSql.getUserByFullname(doctor_data.getValue()).getId(), 1
                );

        SceneController s = new SceneController();
        s.switchToAppointments(selectedPatient, event);
    }

    @FXML
    public void closeCurrentWindow(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToAppointments(selectedPatient, event);
    }

    public void initData(Patient patient) {
        selectedPatient = patient;
    }
}
