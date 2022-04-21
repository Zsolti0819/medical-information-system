package com.example.medis.Controllers;

import com.example.medis.Entities.Patient;
import com.example.medis.Entities.Record;
import com.example.medis.Entities.User;
import com.example.medis.JavaPostgreSql;
import com.example.medis.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class EditRecord  {

    private Patient selectedPatient;
    private Record selectedRecord;
    private User loggedInUser;
    private final JavaPostgreSql javaPostgreSql = new JavaPostgreSql();

    @FXML private Label patientNameRecordTitle;
    @FXML private TextField titleData;
    @FXML private DatePicker dateData;
    @FXML private TextArea descriptionData;
    @FXML private TextArea notesData;

    // Save record button
    @FXML
    private void updateRecord(ActionEvent event) throws IOException {
        System.out.println("Logged in user: " + loggedInUser.getId());
        javaPostgreSql.updateRecord(selectedRecord.getId(), titleData.getText(), descriptionData.getText(), dateData.getValue().toString(), notesData.getText(), selectedPatient.getId(), loggedInUser.getId());
        switchToRecordDetailed(event);
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("Record was updated successfully!");
        a.show();
    }

    // Cancel button
    @FXML
    private void switchToRecordDetailed(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToRecordDetailed(loggedInUser, selectedPatient, selectedRecord, event);
    }

    // Delete record button
    @FXML
    private void deleteRecord(ActionEvent event) throws IOException {
        javaPostgreSql.deleteRecord(selectedRecord.getId());
        switchToRecords(event);
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("Record was deleted successfully!");
        a.show();
    }

    @FXML
    private void switchToRecords(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToRecords(loggedInUser, selectedPatient, event);
    }

    public void initData(Patient patient, Record record, User user) {
        selectedPatient = patient;
        loggedInUser = user;
        selectedRecord = record;
        patientNameRecordTitle.setText(selectedPatient.getFirstName() + " " + selectedPatient.getLastName() + " - " + selectedRecord.getTitle());
        titleData.setText(selectedRecord.getTitle());
        dateData.setValue(selectedRecord.getDateExecuted().toLocalDate());
        descriptionData.setText(selectedRecord.getDescription());
        notesData.setText(selectedRecord.getNotes());
    }
}
