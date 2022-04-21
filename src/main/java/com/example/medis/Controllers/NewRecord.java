package com.example.medis.Controllers;

import com.example.medis.Entities.Patient;
import com.example.medis.Entities.User;
import com.example.medis.JavaPostgreSql;
import com.example.medis.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class NewRecord {

    private Patient selectedPatient;
    private User loggedInUser;
    private final JavaPostgreSql javaPostgreSql = new JavaPostgreSql();

    @FXML private Label patientNameNewRecord;
    @FXML private TextField titleData;
    @FXML private DatePicker dateData;
    @FXML private TextArea descriptionData;
    @FXML private TextArea notesData;
    @FXML private Label missingValuesMsg;

    // Create record button
    @FXML
    private void createRecord(ActionEvent event) throws IOException {
        javaPostgreSql.createRecord(titleData.getText(), descriptionData.getText(), dateData.getValue().toString(), notesData.getText(), selectedPatient.getId(), loggedInUser.getId());
        switchToRecords(event);
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("Record was created successfully!");
        a.show();
    }

    // Cancel button
    @FXML
    private void switchToRecords(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToRecords(loggedInUser, selectedPatient, event);
    }

    public void initData(Patient patient, User user) {
        selectedPatient = patient;
        loggedInUser = user;
        patientNameNewRecord.setText(selectedPatient.getFirstName() + " " + selectedPatient.getLastName() + " - " + "New record");
    }
}
