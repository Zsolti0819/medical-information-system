package com.example.medis.Controller;

import com.example.medis.Entity.Patient;
import com.example.medis.Entity.User;
import com.example.medis.Model.JavaPostgreSql;
import com.example.medis.ControllerBuffer;
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

        String titleText = titleData.getText();
        java.time.LocalDate dateText = dateData.getValue();
        String descriptionText = descriptionData.getText();

        if (!titleText.isEmpty() && dateText!=null && !descriptionText.isEmpty()){
            javaPostgreSql.createRecord(titleData.getText(), descriptionData.getText(), dateData.getValue().toString(), notesData.getText(), selectedPatient.getId(), loggedInUser.getId());
            switchToRecords(event);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Record was created successfully!");
            a.show();
        } else {
            missingValuesMsg.setText("Please fill in missing compulsory data!");
        }

    }

    // Cancel button
    @FXML
    private void switchToRecords(ActionEvent event) throws IOException {
        ControllerBuffer s = new ControllerBuffer();
        s.switchToRecords(loggedInUser, selectedPatient, event);
    }

    public void initData(Patient patient, User user) {
        selectedPatient = patient;
        loggedInUser = user;
        patientNameNewRecord.setText(selectedPatient.getFirstName() + " " + selectedPatient.getLastName() + " - " + "New record");
    }
}
