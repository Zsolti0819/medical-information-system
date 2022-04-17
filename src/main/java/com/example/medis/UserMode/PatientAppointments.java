package com.example.medis.UserMode;


import com.example.medis.Entities.Appointment;
import com.example.medis.Entities.Patient;
import com.example.medis.JavaPostgreSql;
import com.example.medis.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class PatientAppointments implements Initializable {

    private Patient selectedPatient;

    @FXML private TableView<Appointment> appointmentsTable;
    @FXML private TableColumn<Appointment, String> title;
    @FXML private TableColumn<Appointment, LocalDateTime> start_time;
    @FXML private TableColumn<Appointment, LocalDateTime> end_time;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        start_time.setCellValueFactory(new PropertyValueFactory<>("start_time"));
        end_time.setCellValueFactory(new PropertyValueFactory<>("end_time"));

    }

    @FXML
    private void switchToRecords(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToRecords(selectedPatient, event);
    }

    @FXML
    private void switchToPatientsInfo(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToPatientInfo(selectedPatient, event);
    }

    @FXML
    public void closeCurrentWindow(ActionEvent e) {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void initData(Patient patient) {
        selectedPatient = patient;
        appointmentsTable.setItems(JavaPostgreSql.getAllAppointmentsByPatientId(selectedPatient.getId()));

    }
}
