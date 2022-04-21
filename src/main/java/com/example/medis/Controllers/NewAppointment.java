package com.example.medis.Controllers;

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

    @FXML private Label patientNameNewAppointment;
    @FXML private TextField titleData;
    @FXML private DatePicker startYmdData;
    @FXML private ComboBox<String> startHData;
    @FXML private ComboBox<String> startMinData;
    @FXML private DatePicker endYmdData;
    @FXML private ComboBox<String> endHData;
    @FXML private ComboBox<String> endMinData;
    @FXML private TextField descriptionData;
    @FXML private ComboBox <String> doctorData;

    // Create appointment button
    @FXML
    private void createAppointment(ActionEvent event) throws IOException {
        String[] doctorName = doctorData.getValue().split(" ");
        javaPostgreSql.creteAppointment(titleData.getText(), descriptionData.getText(), startYmdData.getValue().toString()+" " + startHData.getValue() + ":" + startMinData.getValue(), endYmdData.getValue().toString() + " "  + endHData.getValue()+":" + endMinData.getValue(), selectedPatient.getId(), javaPostgreSql.getUserByFirstAndLastName(doctorName[0],doctorName[1]).getId(), loggedInUser.getId());
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

    private void fillAppointmentOptions(ComboBox<String> doctorData, ComboBox<String> startHData, ComboBox<String> startMinData, ComboBox<String> endHData, ComboBox<String> endMinData) {
        doctorData.setItems(FXCollections.observableArrayList(javaPostgreSql.getUsersByPosition("doctor")));
        startHData.setItems(FXCollections.observableArrayList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17","18","19","20","21","22","23"));
        startMinData.setItems(FXCollections.observableArrayList("00", "15", "30", "45"));
        endHData.setItems(FXCollections.observableArrayList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17","18","19","20","21","22","23"));
        endMinData.setItems(FXCollections.observableArrayList("00", "15", "30", "45"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillAppointmentOptions(doctorData, startHData, startMinData, endHData, endMinData);
    }

    public void initData(Patient patient, User user) {
        selectedPatient = patient;
        loggedInUser = user;
        patientNameNewAppointment.setText(selectedPatient.getFirstName() + " " + selectedPatient.getLastName() + " - " + "New appointment");
    }
}
