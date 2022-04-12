package com.example.medis.UserMode;


import com.example.medis.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

public class PatientAppointments {

    @FXML
    private void switchToRecords(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchTo("user_mode/patient_records.fxml",event);
    }

    @FXML
    private void switchToPatientsInfo(ActionEvent event) throws  IOException {
        SceneController s = new SceneController();
        s.switchTo("user_mode/patient_info.fxml",event);
    }

    @FXML
    public void closeNewPatientWindow(ActionEvent e) {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
