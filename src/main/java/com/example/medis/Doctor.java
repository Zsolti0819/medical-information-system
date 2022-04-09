package com.example.medis;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class Doctor implements Initializable {

    @FXML private TableView<Patient> patientsTable;
    @FXML private TableColumn<Patient, String> nameAndSurname;
    @FXML private TableColumn<Patient, String> birthNumber;
    @FXML private TableColumn<Patient, LocalDateTime> lastVisit;
    @FXML private TableColumn<Patient, LocalDateTime> nextVisit;
    @FXML private TableColumn<Patient, Button> patientInfo;


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
        s.popUpNewPatient("newPatient.fxml", event);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameAndSurname.setCellValueFactory(new PropertyValueFactory<Patient, String>("nameAndSurname"));
        birthNumber.setCellValueFactory(new PropertyValueFactory<Patient, String>("birthNumber"));
        lastVisit.setCellValueFactory(new PropertyValueFactory<Patient, LocalDateTime>("lastVisit"));
        nextVisit.setCellValueFactory(new PropertyValueFactory<Patient, LocalDateTime>("nextVisit"));
        patientInfo.setCellValueFactory(new PropertyValueFactory<Patient, Button>("patientInfo"));
        patientsTable.setItems(getPatients());
    }

    private ObservableList<Patient> getPatients() {
        ObservableList<Patient> patients = FXCollections.observableArrayList();
        patients.add(new Patient("Title", "Description", LocalDateTime.now(), LocalDateTime.now()));

        return patients;
    }
}