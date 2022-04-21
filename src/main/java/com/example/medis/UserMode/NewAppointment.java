package com.example.medis.UserMode;

import com.example.medis.Entities.Patient;
import com.example.medis.Entities.User;
import com.example.medis.JavaPostgreSql;
import com.example.medis.SceneController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewAppointment implements Initializable {

    private Patient selectedPatient;
    private User loggedInUser;
    private final JavaPostgreSql javaPostgreSql = new JavaPostgreSql();

    @FXML private Label patient_name_new_appointment;
    @FXML private TextField title_data;
    @FXML private DatePicker start_ymd_data;
    @FXML private ComboBox<String> start_h_data;
    @FXML private ComboBox<String> start_min_data;
    @FXML private DatePicker end_ymd_data;
    @FXML private ComboBox<String> end_h_data;
    @FXML private ComboBox<String> end_min_data;
    @FXML private TextField description_data;
    @FXML private ComboBox <String> doctor_data;

    // Create appointment button
    @FXML
    private void createAppointment(ActionEvent event) throws IOException {
        String[] doctor_name = doctor_data.getValue().split(" ");
        javaPostgreSql.creteAppointment(title_data.getText(), description_data.getText(), start_ymd_data.getValue().toString()+" " + start_h_data.getValue() + ":" + start_min_data.getValue(), end_ymd_data.getValue().toString() + " "  + end_h_data.getValue()+":" + end_min_data.getValue(), selectedPatient.getId(), javaPostgreSql.getUserByFirstAndLastName(doctor_name[0],doctor_name[1]).getId(), loggedInUser.getId());
        switchToAppointments(event);
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("Appointment was created successfully!");
        a.show();
    }

    // Cancel button
    @FXML
    private void switchToAppointments(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToAppointments(loggedInUser, selectedPatient, event);
    }

    private void fillAppointmentOptions(ComboBox<String> doctor_data, ComboBox<String> start_h_data, ComboBox<String> start_min_data, ComboBox<String> end_h_data, ComboBox<String> end_min_data) {
        doctor_data.setItems(FXCollections.observableArrayList(javaPostgreSql.getUsersByPosition("doctor")));
        start_h_data.setItems(FXCollections.observableArrayList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17","18","19","20","21","22","23"));
        start_min_data.setItems(FXCollections.observableArrayList("00", "15", "30", "45"));
        end_h_data.setItems(FXCollections.observableArrayList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17","18","19","20","21","22","23"));
        end_min_data.setItems(FXCollections.observableArrayList("00", "15", "30", "45"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillAppointmentOptions(doctor_data, start_h_data, start_min_data, end_h_data, end_min_data);
    }

    public void initData(Patient patient, User user) {
        selectedPatient = patient;
        loggedInUser = user;
        patient_name_new_appointment.setText(selectedPatient.getFirst_name() + " " + selectedPatient.getLast_name() + " - " + "New appointment");
    }
}
