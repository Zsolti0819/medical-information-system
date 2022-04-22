package com.example.medis.Controller;

import com.example.medis.Entity.Patient;
import com.example.medis.Entity.Record;
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

public class Records implements Initializable {

    private Patient selectedPatient;
    private Record selectedRecord;
    private User loggedInUser;
    private final JavaPostgreSql javaPostgreSql = new JavaPostgreSql();

    @FXML private TableView<Record> recordsTable;
    @FXML private Label patientNameRecords;
    @FXML private TableColumn<Record, String> title;
    @FXML private TableColumn<Record, String> description;
    @FXML private TableColumn<Record, LocalDateTime> createdAt;

    @FXML
    private void switchToPatientInfo(ActionEvent event) throws IOException  {
        ControllerBuffer s = new ControllerBuffer();
        s.switchToPatientInfo(loggedInUser, selectedPatient, event);
    }

    @FXML
    private void switchToRecordDetailed(ActionEvent event) throws IOException {
        ControllerBuffer s = new ControllerBuffer();
        s.switchToRecordDetailed(loggedInUser, selectedPatient, selectedRecord, event);
    }

    @FXML
    private void switchToAppointments(ActionEvent event) throws IOException {
        ControllerBuffer s = new ControllerBuffer();
        s.switchToAppointments(loggedInUser, selectedPatient, event);
    }

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

    @FXML
    private void switchToPatients(ActionEvent event) throws IOException {
        ControllerBuffer s = new ControllerBuffer();
        s.switchToPatients(loggedInUser, event);
    }

    @FXML
    private void switchToRecordCreation(MouseEvent event) throws IOException {
        if (loggedInUser.getPosition().equals("doctor")) {
            ControllerBuffer s = new ControllerBuffer();
            s.switchToRecordCreation(loggedInUser, selectedPatient, event);
        }
        else{
            GeneralLogger.log(Level.WARNING, "ACCESS | DENIED | RECORDS: Denied for " + loggedInUser.getId() );
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("You don't have permissions to create Record!");
            a.show();
        }
    }

    private void addButtonToTable() {
        TableColumn<Record, Void> details  = new TableColumn<>();

        Callback<TableColumn<Record, Void>, TableCell<Record, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Record, Void> call(final TableColumn<Record, Void> param) {
                return new TableCell<>() {

                    private final Button openButton = new Button("View");

                    {
                        openButton.setOnAction((ActionEvent event) -> {
                            selectedRecord  = javaPostgreSql.getRecord(recordsTable.getItems().get(getIndex()).getId());
                            try {
                                switchToRecordDetailed(event);
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

        recordsTable.getColumns().add(details);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        createdAt.setCellValueFactory(new PropertyValueFactory<>("createdAtFormatted"));
        addButtonToTable();
    }

    public void initData(Patient patient, User user) {
        selectedPatient = patient;
        loggedInUser = user;
        patientNameRecords.setText(selectedPatient.getFirstName() + " " + selectedPatient.getLastName() + "'s records");
        recordsTable.setItems(javaPostgreSql.getAllNotDeletedRecordsByPatientId(selectedPatient.getId()));
    }
}
