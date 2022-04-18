package com.example.medis.UserMode;

import com.example.medis.Entities.Patient;
import com.example.medis.Entities.Record;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PatientInfoEdit {

    private Patient selectedPatient;
    private Record selectedRecord;

    @FXML
    public void closeCurrentWindow(ActionEvent e) {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }




}
