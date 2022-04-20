package com.example.medis.UserMode;

import com.example.medis.Entities.Appointment;
import com.example.medis.Entities.Patient;
import com.example.medis.Entities.User;
import com.example.medis.GeneralLogger;
import com.example.medis.JavaPostgreSql;
import com.example.medis.SceneController;
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

    @FXML private TableView<Appointment> appointmentsTable;
    @FXML private Label patient_name_appointments;
    @FXML private TableColumn<Appointment, String> title;
    @FXML private TableColumn<Appointment, Long> doctor;
    @FXML private TableColumn<Appointment, LocalDateTime> start_time;
    @FXML private TableColumn<Appointment, LocalDateTime> end_time;

    // Patient info
    @FXML
    private void switchToPatientInfo(ActionEvent event) throws IOException {

        SceneController s = new SceneController();
        s.switchToPatientInfo(loggedInUser, selectedPatient, event);
    }

    // Records
    @FXML
    private void switchToRecords(ActionEvent event) throws IOException {
        if (!loggedInUser.getPosition().equals("receptionist")) {
            SceneController s = new SceneController();
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
        SceneController s = new SceneController();
        s.switchToAppointmentEdit(loggedInUser, selectedPatient, selectedAppointment, event);

    }

    // Plus button
    @FXML
    private void switchToAppointmentCreation(MouseEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToAppointmentCreation(loggedInUser, selectedPatient, event);
    }

    // Prescriptions
    @FXML
    private void switchToPrescriptions(ActionEvent event) throws IOException {
        if (loggedInUser.getPosition().equals("doctor")) {
            SceneController s = new SceneController();
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
    private void closeCurrentWindow(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToDashboard(loggedInUser, event);
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

                            selectedAppointment = JavaPostgreSql.getAppointment(appointmentsTable.getItems().get(getIndex()).getId());
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
        doctor.setCellValueFactory(new PropertyValueFactory<>("doctor_name"));
        start_time.setCellValueFactory(new PropertyValueFactory<>("start_time_formatted"));
        end_time.setCellValueFactory(new PropertyValueFactory<>("end_time_formatted"));
        addButtonToTable();
    }

    public void initData(Patient patient, User user) {
        loggedInUser = user;
        selectedPatient = patient;
        patient_name_appointments.setText(selectedPatient.getFirst_name() + " " + selectedPatient.getLast_name() + "'s appointments");
        appointmentsTable.setItems(JavaPostgreSql.getAllNotDeletedAppointmentsByPatientId(selectedPatient.getId()));

    }


}
