package com.example.medis.UserMode;

import com.example.medis.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

public class PatientInfo {



    @FXML
    private void switchToRecords(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchTo("user_mode/patient_records.fxml",event);
   }

   @FXML
   private void switchToAppointments(ActionEvent event) throws  IOException {
        SceneController s = new SceneController();
        s.switchTo("user_mode/patient_appointments.fxml",event);
   }

    @FXML
    private void OpenEditPatientData() {
        SceneController s = new SceneController();
        try {
            s.popUpNewPatient("user_mode/patient_info_edit.fxml");
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
