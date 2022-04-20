package com.example.medis.UserMode;

import com.example.medis.Entities.Patient;
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

public class NewPrescription {

    private Patient selectedPatient;
    private User loggedInUser;

    @FXML private Label patient_name_prescription_title;
    @FXML private TextField title_data;
    @FXML private TextArea description_data;
    @FXML private TextField drug_data;
    @FXML private DatePicker exp_date_ymd_data;
    @FXML private TextArea notes_data;

    // Create prescription button
    @FXML
    private void createPrescription(ActionEvent event) throws IOException {
        JavaPostgreSql.createPrescription(title_data.getText(), description_data.getText(), drug_data.getText(), exp_date_ymd_data.getValue().toString(), selectedPatient.getId(), loggedInUser.getId(), notes_data.getText());
        switchToPrescriptions(event);
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("Prescription was created successfully!");
        a.show();
    }

    // Cancel button
    @FXML
    private void switchToPrescriptions(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToPrescriptions(loggedInUser, selectedPatient, event);
    }

    public void initData(Patient patient, User user) {
        selectedPatient = patient;
        loggedInUser = user;
        patient_name_prescription_title.setText(selectedPatient.getFirst_name() + " " + selectedPatient.getLast_name() + " - " + "New prescription");
    }
}
