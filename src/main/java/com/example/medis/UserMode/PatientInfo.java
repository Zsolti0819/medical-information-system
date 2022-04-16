package com.example.medis.UserMode;

import com.example.medis.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PatientInfo implements Initializable {



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
            s.newWindow("user_mode/patient_info_edit.fxml");
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
