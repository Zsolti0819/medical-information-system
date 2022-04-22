package com.example.medis.Controller;

import com.example.medis.Entity.Patient;
import com.example.medis.Entity.Record;
import com.example.medis.Entity.User;
import com.example.medis.Model.JavaPostgreSql;
import com.example.medis.ControllerBuffer;
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
    @FXML private Label missingValuesMsg;

    // Save record button
    @FXML
    private void updateRecord(ActionEvent event) throws IOException {

        String titleText = titleData.getText();
        java.time.LocalDate dateText = dateData.getValue();
        String descriptionText = descriptionData.getText();

        if (!titleText.isEmpty() && dateText!=null && !descriptionText.isEmpty()){
            System.out.println("Logged in user: " + loggedInUser.getId());
            javaPostgreSql.updateRecord(selectedRecord.getId(), titleData.getText(), descriptionData.getText(), dateData.getValue().toString(), notesData.getText(), selectedPatient.getId(), loggedInUser.getId());
            switchToRecordDetailed(event);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Record was updated successfully!");
            a.show();
        }else {
            missingValuesMsg.setText("Please fill in missing compulsory data!");
        }

    }

    // Cancel button
    @FXML
    private void switchToRecordDetailed(ActionEvent event) throws IOException {
        ControllerBuffer s = new ControllerBuffer();
        s.switchToRecordDetailed(loggedInUser, selectedPatient, selectedRecord, event);
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
