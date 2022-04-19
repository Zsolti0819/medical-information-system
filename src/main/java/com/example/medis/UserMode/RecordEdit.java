package com.example.medis.UserMode;

import com.example.medis.Entities.Patient;
import com.example.medis.Entities.Record;
import com.example.medis.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RecordEdit implements Initializable {

    private Patient selectedPatient;
    private Record selectedRecord;

    @FXML private Label patient_name_record_title;
    @FXML private TextField title_data;
    @FXML private TextField date_data;
    @FXML private TextArea description_data;
    @FXML private TextArea notes_data;

    @FXML
    public void switchToRecordDetailed(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToRecordDetailed(selectedPatient, selectedRecord, event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void initData(Patient patient, Record record) {
        selectedPatient = patient;
        selectedRecord = record;
        patient_name_record_title.setText(selectedPatient.getFirst_name() + " " + selectedPatient.getSurname() + " - " + selectedRecord.getTitle());
        title_data.setText(selectedRecord.getTitle());
        date_data.setText(selectedRecord.getDate_executed().toString());
        description_data.setText(selectedRecord.getDescription());
        notes_data.setText(selectedRecord.getNotes());
    }
}
