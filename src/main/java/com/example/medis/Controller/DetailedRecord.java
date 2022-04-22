package com.example.medis.Controller;

import com.example.medis.Entity.Patient;
import com.example.medis.Entity.Record;
import com.example.medis.Entity.User;
import com.example.medis.Model.JavaPostgreSql;
import com.example.medis.ControllerBuffer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DetailedRecord implements Initializable {

    private Patient selectedPatient;
    private Record selectedRecord;
    private User loggedInUser;
    private final JavaPostgreSql javaPostgreSql = new JavaPostgreSql();

    @FXML private Label patientNameRecordTitle;
    @FXML private Label titleData;
    @FXML private Label dateData;
    @FXML private Label descriptionData;
    @FXML private Label notesData;

    @FXML
    private void switchToRecords(ActionEvent event) throws IOException {
        ControllerBuffer s = new ControllerBuffer();
        s.switchToRecords(loggedInUser, selectedPatient, event);
    }

    @FXML
    private void switchToRecordEdit(ActionEvent event) throws IOException {
        ControllerBuffer s = new ControllerBuffer();
        s.switchToRecordEdit(loggedInUser, selectedPatient, selectedRecord,event);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void initData(Patient patient, Record record, User user) {
        selectedPatient = patient;
        selectedRecord = javaPostgreSql.getRecord(record.getId());
        loggedInUser = user;
        patientNameRecordTitle.setText(selectedPatient.getFirstName() + " " + selectedPatient.getLastName() + " - " + selectedRecord.getTitle());
        titleData.setText(selectedRecord.getTitle());
        dateData.setText(selectedRecord.getDateExecuted().toString());
        descriptionData.setText(selectedRecord.getDescription());
        notesData.setText(selectedRecord.getNotes());

    }
}
