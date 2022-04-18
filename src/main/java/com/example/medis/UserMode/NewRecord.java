package com.example.medis.UserMode;

import com.example.medis.Entities.Patient;
import com.example.medis.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class NewRecord {

    private Patient selectedPatient;

    @FXML private Label patient_name_new_record;

    @FXML
    public void switchToRecords(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToRecords(selectedPatient, event);
    }

    public void initData(Patient patient) {
        selectedPatient = patient;
        patient_name_new_record.setText(selectedPatient.getFirst_name() + " " + selectedPatient.getSurname() + " - " + "New record");
    }
}
