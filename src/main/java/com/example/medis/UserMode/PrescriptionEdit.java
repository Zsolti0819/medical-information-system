package com.example.medis.UserMode;

import com.example.medis.Entities.Patient;
import com.example.medis.Entities.Prescription;
import com.example.medis.JavaPostgreSql;
import com.example.medis.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrescriptionEdit implements Initializable {

    private Patient selectedPatient;
    private Prescription selectedPrescription;

    @FXML private Label patient_name_appointment_title;
    @FXML private TextField title_data;
    @FXML private TextField description_data;
    @FXML private TextField drug_data;
    @FXML private DatePicker exp_date_ymd_data;
    @FXML private TextField notes_data;

    public void updatePrescription(ActionEvent event) {
        JavaPostgreSql.updatePrescription(selectedPrescription.getId(), title_data.getText(), description_data.getText(), drug_data.getText(), exp_date_ymd_data.getValue().toString(), selectedPatient.getId(), 1, notes_data.getText());
    }

    public void switchToPrescriptions(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToPrescriptions(selectedPatient, event);
    }

    public void deletePrescription(ActionEvent event) throws IOException {
        JavaPostgreSql.deletePrescription(selectedPrescription.getId());
        switchToPrescriptions(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void initData(Patient patient, Prescription prescription) {
        selectedPatient = patient;
        selectedPrescription = prescription;
        patient_name_appointment_title.setText(selectedPatient.getFirst_name() + " " + selectedPatient.getLast_name() + " - " + selectedPrescription.getTitle());
    }


}
