package com.example.medis;

import java.io.IOException;
import java.util.Objects;

import com.example.medis.Entities.Patient;
import com.example.medis.UserMode.NewPatient;
import com.example.medis.UserMode.PatientInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SceneController extends NewPatient {

    private Parent root;

    public void switchTo(String fxml, ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Medis");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void newWindow(String fxml) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setAlwaysOnTop(true);
        stage.toFront();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void newWindowCustom(Patient patient) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("user_mode/patient_info.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        PatientInfo controller = loader.getController();
        controller.initData(patient);
        stage.setAlwaysOnTop(true);
        stage.toFront();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
}