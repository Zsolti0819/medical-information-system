package com.example.medis.UserMode;

import com.example.medis.Entities.Patient;
import com.example.medis.Entities.User;
import com.example.medis.JavaPostgreSql;
import com.example.medis.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class NewRecord {

    private Patient selectedPatient;
    private User loggedInUser;
    @FXML private Label patient_name_new_record;
    @FXML private TextField title_data;
    @FXML private DatePicker date_data;
    @FXML private TextArea description_data;
    @FXML private TextArea notes_data;

    // Create record button
    @FXML
    private void createRecord(ActionEvent event) throws IOException {
        JavaPostgreSql.createRecord(title_data.getText(), description_data.getText(), date_data.getValue().toString(), notes_data.getText(), selectedPatient.getId(), loggedInUser.getId());
        switchToRecords(event);
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("Record was created successfully!");
        a.show();
    }

    // Cancel button
    @FXML
    private void switchToRecords(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToRecords(loggedInUser, selectedPatient, event);
    }

    public void initData(Patient patient, User user) {
        selectedPatient = patient;
        loggedInUser = user;
        patient_name_new_record.setText(selectedPatient.getFirst_name() + " " + selectedPatient.getLast_name() + " - " + "New record");
    }
}
