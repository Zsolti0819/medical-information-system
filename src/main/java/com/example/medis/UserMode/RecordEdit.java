package com.example.medis.UserMode;

import com.example.medis.Entities.Patient;
import com.example.medis.Entities.Record;
import com.example.medis.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class RecordEdit {

    private Patient selectedPatient;
    private Record selectedRecord;

    @FXML private Label patient_name_record_title;

    @FXML
    public void switchToRecordDetailed(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToRecordDetailed(selectedPatient, selectedRecord, event);
    }

    public void initData(Patient patient, Record record) {
        selectedPatient = patient;
        selectedRecord = record;
        patient_name_record_title.setText(selectedPatient.getFirst_name() + " " + selectedPatient.getSurname() + " - " + selectedRecord.getTitle());
    }
}
