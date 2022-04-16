package com.example.medis.UserMode;

import com.example.medis.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

public class PatientRecordDetailed {

    @FXML
    private void OpenEditPatientRecord() {
        SceneController s = new SceneController();
        try {
            s.newWindow("user_mode/patient_record_edit.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void closeCurrentWindow(ActionEvent e) {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
