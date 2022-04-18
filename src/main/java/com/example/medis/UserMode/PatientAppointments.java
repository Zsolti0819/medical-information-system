package com.example.medis.UserMode;


import com.example.medis.Entities.Appointment;
import com.example.medis.Entities.Patient;
import com.example.medis.JavaPostgreSql;
import com.example.medis.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class PatientAppointments implements Initializable {

    private Patient selectedPatient;
    private Appointment selectedAppointment;

    @FXML private TableView<Appointment> appointmentsTable;
    @FXML private Label patient_name_appointments;
    @FXML private TableColumn<Appointment, String> title;
    @FXML private TableColumn<Appointment, LocalDateTime> start_time;
    @FXML private TableColumn<Appointment, LocalDateTime> end_time;

    private void addButtonToTable() {
        TableColumn<Appointment, Void> details  = new TableColumn<>();
        details.setText("Details");

        Callback<TableColumn<Appointment, Void>, TableCell<Appointment, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Appointment, Void> call(final TableColumn<Appointment, Void> param) {
                return new TableCell<>() {

                    private final Button openButton = new Button("Open");

                    {
                        openButton.setOnAction((ActionEvent event) -> {

                            selectedAppointment = JavaPostgreSql.getAppointment(appointmentsTable.getItems().get(getIndex()).getId());
                            try {
                                showAppointmentInfo(event);
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

    @FXML
    public void showAppointmentInfo(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToOneAppointment(selectedPatient, selectedAppointment, event, "user_mode/patient_appointment_edit.fxml");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        start_time.setCellValueFactory(new PropertyValueFactory<>("start_time"));
        end_time.setCellValueFactory(new PropertyValueFactory<>("end_time"));
        addButtonToTable();

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
    public void closeCurrentWindow(ActionEvent event) {
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void addAppointment(MouseEvent event)  {
        SceneController s = new SceneController();
        try {
            s.switchToAppointmentCreation(selectedPatient, event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initData(Patient patient) {
        selectedPatient = patient;
        patient_name_appointments.setText(selectedPatient.getFirst_name() + " " + selectedPatient.getSurname() + "'s appointments");
        appointmentsTable.setItems(JavaPostgreSql.getAllAppointmentsByPatientId(selectedPatient.getId()));

    }
}
