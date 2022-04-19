package com.example.medis.UserMode;

import com.example.medis.Entities.Patient;
import com.example.medis.JavaPostgreSql;
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
        patient_name_new_record.setText(selectedPatient.getFirst_name() + " " + selectedPatient.getLast_name() + " - " + "New record");
    }

    @FXML
    public void createRecord(ActionEvent event) {
//        JavaPostgreSql.createRecord();
    }

    @FXML
    public void createPrescription(ActionEvent event) throws IOException {
//        JavaPostgreSql.createRecord()
        SceneController s = new SceneController();
        s.switchToPrescriptions(selectedPatient, event);
    }
}
