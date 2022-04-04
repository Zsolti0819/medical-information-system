package com.example.medis;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class Doctor {


    @FXML
    private void userLogOut(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToLogin("login.fxml",event);

    }

    @FXML
    private void showPatients(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToDoctor("doctor.fxml",event);
    }

    @FXML
    private void addPatient(MouseEvent event) throws IOException {
        SceneController s = new SceneController();
        s.popUpNewPatient("NewPatient.fxml", event);

    }


}