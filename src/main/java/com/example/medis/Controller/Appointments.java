package com.example.medis.Controller;

import com.example.medis.Entity.Appointment;
import com.example.medis.Entity.Patient;
import com.example.medis.Entity.User;
import com.example.medis.GeneralLogger;
import com.example.medis.Model.JavaPostgreSql;
import com.example.medis.ControllerBuffer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class Appointments implements Initializable {

    private Patient selectedPatient;
    private User loggedInUser;
    private Appointment selectedAppointment;
    private final JavaPostgreSql javaPostgreSql = new JavaPostgreSql();

    @FXML private TableView<Appointment> appointmentsTable;
    @FXML private Label patientNameAppointments;
    @FXML private TableColumn<Appointment, String> title;
    @FXML private TableColumn<Appointment, Long> doctor;
    @FXML private TableColumn<Appointment, LocalDateTime> startTime;
    @FXML private TableColumn<Appointment, LocalDateTime> endTime;

    // Patient info
    @FXML
    private void switchToPatientInfo(ActionEvent event) throws IOException {

        ControllerBuffer s = new ControllerBuffer();
        s.switchToPatientInfo(loggedInUser, selectedPatient, event);
    }

    // Records
    @FXML
    private void switchToRecords(ActionEvent event) throws IOException {
        if (!loggedInUser.getPosition().equals("receptionist")) {
            ControllerBuffer s = new ControllerBuffer();
            s.switchToRecords(loggedInUser, selectedPatient, event);
        }
        else{
            GeneralLogger.log(Level.WARNING, "ACCESS | DENIED | RECORDS: Denied for " + loggedInUser.getId() );
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("You don't have permissions to show Records!");
            a.show();
        }
    }

    // Edit buttons
    @FXML
    private void switchToAppointmentEdit(ActionEvent event) throws IOException {
        ControllerBuffer s = new ControllerBuffer();
        s.switchToAppointmentEdit(loggedInUser, selectedPatient, selectedAppointment, event);

    }

    // Plus button
    @FXML
    private void switchToAppointmentCreation(MouseEvent event) throws IOException {
        ControllerBuffer s = new ControllerBuffer();
        s.switchToAppointmentCreation(loggedInUser, selectedPatient, event);
    }

    // Prescriptions
    @FXML
    private void switchToPrescriptions(ActionEvent event) throws IOException {
        if (loggedInUser.getPosition().equals("doctor")) {
            ControllerBuffer s = new ControllerBuffer();
            s.switchToPrescriptions(loggedInUser, selectedPatient, event);
        }
        else{
            GeneralLogger.log(Level.WARNING, "ACCESS | DENIED | PRESCRIPTIONS: Denied for " + loggedInUser.getId() );
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("You don't have permissions to show Prescriptions!");
            a.show();
        }
    }

    // Close window button
    @FXML
    private void switchToPatients(ActionEvent event) throws IOException {
        ControllerBuffer s = new ControllerBuffer();
        s.switchToPatients(loggedInUser, event);
    }

    private void addButtonToTable() {
        TableColumn<Appointment, Void> details  = new TableColumn<>();

        Callback<TableColumn<Appointment, Void>, TableCell<Appointment, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Appointment, Void> call(final TableColumn<Appointment, Void> param) {
                return new TableCell<>() {

                    private final Button openButton = new Button("Edit");

                    {
                        openButton.setOnAction((ActionEvent event) -> {

                            selectedAppointment = javaPostgreSql.getAppointment(appointmentsTable.getItems().get(getIndex()).getId());
                            try {
                                switchToAppointmentEdit(event);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(openButton);
                        }
                    }
                };
            }
        };

        details.setCellFactory(cellFactory);

        appointmentsTable.getColumns().add(details);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        doctor.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
        startTime.setCellValueFactory(new PropertyValueFactory<>("startTimeFormatted"));
        endTime.setCellValueFactory(new PropertyValueFactory<>("endTimeFormatted"));
        addButtonToTable();
    }

    public void initData(Patient patient, User user) {
        loggedInUser = user;
        selectedPatient = patient;
        patientNameAppointments.setText(selectedPatient.getFirstName() + " " + selectedPatient.getLastName() + "'s appointments");
        appointmentsTable.setItems(javaPostgreSql.getAllNotDeletedAppointmentsByPatientId(selectedPatient.getId()));

    }


}
