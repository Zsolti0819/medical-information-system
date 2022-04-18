package com.example.medis.UserMode;

import com.example.medis.Entities.Appointment;
import com.example.medis.Entities.Patient;
import com.example.medis.Entities.Record;
import com.example.medis.JavaPostgreSql;
import com.example.medis.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RecordDetailed implements Initializable {

    private Patient selectedPatient;
    private Record selectedRecord;

    @FXML private Label patient_name_record_title;
    @FXML private Label title_data;
    @FXML private Label date_data;
    @FXML private Label description_data;
    @FXML private Label notes_data;
    @FXML private Label medicines_data;

    @FXML
    private void switchToRecordEdit(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToRecordEdit(selectedPatient, selectedRecord,event);
    }

    @FXML
    public void switchToRecords(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToRecords(selectedPatient, event);
    }

    public void initData(Patient patient, Record record) {
        selectedPatient = patient;
        selectedRecord = record;
        patient_name_record_title.setText(selectedPatient.getFirst_name() + " " + selectedPatient.getSurname() + " - " + selectedRecord.getTitle());
        title_data.setText(selectedRecord.getTitle());
        date_data.setText(selectedRecord.getDate_executed().toString());
        description_data.setText(selectedRecord.getDescription());
        notes_data.setText(selectedRecord.getNotes());
        medicines_data.setText("Not implemented");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
