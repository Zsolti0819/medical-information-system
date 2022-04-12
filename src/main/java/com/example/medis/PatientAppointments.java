package com.example.medis;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class PatientAppointments {

    @FXML
    private void switchToRecords(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchTo("user_mode_patient_records.fxml",event);
    }

    @FXML
    private void switchToPatientsInfo(ActionEvent event) throws  IOException {
        SceneController s = new SceneController();
        s.switchTo("user_mode_patient_info.fxml",event);
    }

    @FXML
    public void closeNewPatientWindow(ActionEvent e) {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
