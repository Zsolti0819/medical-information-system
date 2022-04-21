package com.example.medis.UserMode;

import com.example.medis.Entities.Patient;
import com.example.medis.Entities.Record;
import com.example.medis.Entities.User;
import com.example.medis.JavaPostgreSql;
import com.example.medis.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditRecord  {

    private Patient selectedPatient;
    private Record selectedRecord;
    private User loggedInUser;
    private final JavaPostgreSql javaPostgreSql = new JavaPostgreSql();

    @FXML private Label patient_name_record_title;
    @FXML private TextField title_data;
    @FXML private DatePicker date_data;
    @FXML private TextArea description_data;
    @FXML private TextArea notes_data;

    // Save record button
    @FXML
    private void updateRecord(ActionEvent event) throws IOException {
        System.out.println("Logged in user: " + loggedInUser.getId());
        javaPostgreSql.updateRecord(selectedRecord.getId(), title_data.getText(), description_data.getText(), date_data.getValue().toString(), notes_data.getText(), selectedPatient.getId(), loggedInUser.getId());
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
        patient_name_record_title.setText(selectedPatient.getFirst_name() + " " + selectedPatient.getLast_name() + " - " + selectedRecord.getTitle());
        title_data.setText(selectedRecord.getTitle());
        date_data.setValue(selectedRecord.getDate_executed().toLocalDate());
        description_data.setText(selectedRecord.getDescription());
        notes_data.setText(selectedRecord.getNotes());
    }
}
