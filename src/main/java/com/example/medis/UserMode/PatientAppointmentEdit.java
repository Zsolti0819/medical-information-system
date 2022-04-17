package com.example.medis.UserMode;

import com.example.medis.Entities.Appointment;
import com.example.medis.JavaPostgreSql;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PatientAppointmentEdit implements Initializable {

    Appointment selectedAppointment;

    @FXML private TextField title_data;
    @FXML private DatePicker start_ymd_data;
    @FXML private DatePicker test;
    @FXML private DatePicker start_min_data;
    @FXML private DatePicker end_ymd_data;
    @FXML private DatePicker end_h_data;
    @FXML private DatePicker end_min_data;
    @FXML private TextField description_data;
    @FXML private ComboBox <String> doctor_data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        doctor_data.setItems(FXCollections.observableArrayList(JavaPostgreSql.getUsersByPosition("doctor")));
    }


    @FXML
    public void closeCurrentWindow(ActionEvent e) {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void initData(Appointment appointment) {
        selectedAppointment = appointment;
        title_data.setText(selectedAppointment.getTitle());
        description_data.setText(selectedAppointment.getDescription());
        doctor_data.getSelectionModel().select(JavaPostgreSql.getUser(selectedAppointment.getDoctor_id()).getFullname());
        start_ymd_data.setValue(selectedAppointment.getStart_time().toLocalDate());
        end_ymd_data.setValue(selectedAppointment.getEnd_time().toLocalDate());

    }

}
