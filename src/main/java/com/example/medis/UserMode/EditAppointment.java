package com.example.medis.UserMode;

import com.example.medis.Entities.Appointment;
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

public class EditAppointment implements Initializable {

    private Patient selectedPatient;
    private User loggedInUser;
    private Appointment selectedAppointment;

    @FXML private Label patient_name_appointment_title;
    @FXML private TextField title_data;
    @FXML private DatePicker start_ymd_data;
    @FXML private ComboBox<String> start_h_data;
    @FXML private ComboBox<String> start_min_data;
    @FXML private DatePicker end_ymd_data;
    @FXML private ComboBox<String> end_h_data;
    @FXML private ComboBox<String> end_min_data;
    @FXML private TextField description_data;
    @FXML private ComboBox <String> doctor_data;

    // Save button
    @FXML
    private void updateAppointment(ActionEvent event) throws IOException {
        String[] doctor_name = doctor_data.getValue().split(" ");
        System.out.println(JavaPostgreSql.getUserByFirstAndLastName(doctor_name[0],doctor_name[1]).getId()+ " " + doctor_name[0] + " " + doctor_name[1]);
        JavaPostgreSql.updateAppointment(
                selectedAppointment.getId(),
                title_data.getText(),
                description_data.getText(),
                start_ymd_data.getValue().toString()+" " + start_h_data.getValue() + ":" + start_min_data.getValue(),
                end_ymd_data.getValue().toString() + " "  + end_h_data.getValue()+":" + end_min_data.getValue(),
                selectedAppointment.getPatient_id(), 1);

        switchToAppointments(event);
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("Appointment was updated successfully!");
        a.show();
    }

    // Cancel button
    @FXML
    private void switchToAppointments(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToAppointments(loggedInUser, selectedPatient, event);
    }

    // Delete button
    @FXML
    private void deleteAppointment(ActionEvent event) throws IOException {
        JavaPostgreSql.deleteAppointment(selectedAppointment.getId());
        SceneController s = new SceneController();
        s.switchToAppointments(loggedInUser, selectedPatient, event);
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("Appointment was deleted successfully!");
        a.show();
    }

    static void fillAppointmentOptions(ComboBox<String> doctor_data, ComboBox<String> start_h_data, ComboBox<String> start_min_data, ComboBox<String> end_h_data, ComboBox<String> end_min_data) {
        doctor_data.setItems(FXCollections.observableArrayList(JavaPostgreSql.getUsersByPosition("doctor")));
        start_h_data.setItems(FXCollections.observableArrayList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17","18","19","20","21","22","23"));
        start_min_data.setItems(FXCollections.observableArrayList("00", "15", "30", "45"));
        end_h_data.setItems(FXCollections.observableArrayList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17","18","19","20","21","22","23"));
        end_min_data.setItems(FXCollections.observableArrayList("00", "15", "30", "45"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillAppointmentOptions(doctor_data, start_h_data, start_min_data, end_h_data, end_min_data);
    }

    public void initData(Patient patient, Appointment appointment, User user) {
        selectedPatient = patient;
        loggedInUser = user;
        selectedAppointment = appointment;
        patient_name_appointment_title.setText(selectedPatient.getFirst_name() + " " + selectedPatient.getLast_name() + " - " + selectedAppointment.getTitle());
        title_data.setText(selectedAppointment.getTitle());
        description_data.setText(selectedAppointment.getDescription());
        doctor_data.getSelectionModel().select(JavaPostgreSql.getUser(selectedAppointment.getDoctor_id()).getFirst_name() + " " + JavaPostgreSql.getUser(selectedAppointment.getDoctor_id()).getLast_name());
        start_ymd_data.setValue(selectedAppointment.getStart_time().toLocalDate());
        end_ymd_data.setValue(selectedAppointment.getEnd_time().toLocalDate());
        start_h_data.getSelectionModel().select(String.valueOf(selectedAppointment.getStart_hour()));
        end_h_data.getSelectionModel().select(String.valueOf(selectedAppointment.getEnd_hour()));
        start_min_data.getSelectionModel().select(String.valueOf(selectedAppointment.getStart_min()));
        end_min_data.getSelectionModel().select(String.valueOf(selectedAppointment.getEnd_min()));

    }
}
