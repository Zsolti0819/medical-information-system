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
    Button [] button = new Button[3];

    private void handleButtonAction (ActionEvent event)  {
        if (event.getSource() == button[0]) {
            System.out.println("This button should open a new window (user_mode_patient_info.fxml)");
        }
    }


    @FXML
    private void userLogOut(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchTo("login.fxml",event);

    }

    @FXML
    private void showPatients(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchTo("user_mode.fxml",event);
    }

    @FXML
    private void addPatient() throws IOException {
        SceneController s = new SceneController();
        s.popUpNewPatient("newPatient.fxml");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        nameAndSurname.setCellValueFactory(new PropertyValueFactory<>("nameAndSurname"));
        birthNumber.setCellValueFactory(new PropertyValueFactory<>("birthNumber"));
        lastVisit.setCellValueFactory(new PropertyValueFactory<>("lastVisit"));
        nextVisit.setCellValueFactory(new PropertyValueFactory<>("nextVisit"));
        patientInfo.setCellValueFactory(new PropertyValueFactory<>("patientInfo"));


        for (int i = 0; i < button.length; i++) {
            button[i] = new Button();
            button[i].setOnAction(this::handleButtonAction);
        }
        patientsTable.setItems(getPatients());
    }

    private ObservableList<Patient> getPatients() {
        ObservableList<Patient> patients = FXCollections.observableArrayList();
        patients.add(new Patient("Title", "Description", LocalDateTime.now(), LocalDateTime.now(), button[0]));

        return patients;
    }

}