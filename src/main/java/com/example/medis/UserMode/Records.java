package com.example.medis.UserMode;

import com.example.medis.Entities.Patient;
import com.example.medis.Entities.Record;
import com.example.medis.Entities.User;
import com.example.medis.GeneralLogger;
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
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class Records implements Initializable {

    private Patient selectedPatient;
    private Record selectedRecord;
    private User loggedInUser;
    @FXML private TableView<Record> recordsTable;
    @FXML private Label patient_name_records;
    @FXML private TableColumn<Record, String> title;
    @FXML private TableColumn<Record, String> description;
    @FXML private TableColumn<Record, LocalDateTime> created_at;

    @FXML
    private void switchToPatientInfo(ActionEvent event) throws IOException  {
        SceneController s = new SceneController();
        s.switchToPatientInfo(loggedInUser, selectedPatient, event);
    }

    @FXML
    private void switchToRecordDetailed(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToRecordDetailed(loggedInUser, selectedPatient, selectedRecord, event);
    }

    @FXML
    private void switchToAppointments(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToAppointments(loggedInUser, selectedPatient, event);
    }

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

    @FXML
    private void closeCurrentWindow(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToDashboard(loggedInUser, event);
    }

    @FXML
    private void switchToRecordCreation(MouseEvent event) throws IOException {
        if (loggedInUser.getPosition().equals("doctor")) {
            SceneController s = new SceneController();
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
                            selectedRecord  = JavaPostgreSql.getRecord(recordsTable.getItems().get(getIndex()).getId());
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
        created_at.setCellValueFactory(new PropertyValueFactory<>("created_at_formatted"));
        addButtonToTable();
    }

    public void initData(Patient patient, User user) {
        selectedPatient = patient;
        loggedInUser = user;
        patient_name_records.setText(selectedPatient.getFirst_name() + " " + selectedPatient.getLast_name() + "'s records");
        recordsTable.setItems(JavaPostgreSql.getAllNotDeletedRecordsByPatientId(selectedPatient.getId()));
    }
}
