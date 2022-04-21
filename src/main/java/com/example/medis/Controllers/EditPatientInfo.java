package com.example.medis.Controllers;

import com.example.medis.Entities.Patient;
import com.example.medis.Entities.User;
import com.example.medis.JavaPostgreSql;
import com.example.medis.SceneController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditPatientInfo implements Initializable{


    private Patient selectedPatient;
    private User loggedInUser;
    private final JavaPostgreSql javaPostgreSql = new JavaPostgreSql();

    @FXML private Label firstNameAndLastName;
    @FXML private TextField firstNameData;
    @FXML private TextField lastNameData;
    @FXML private ComboBox<String> insuranceCoData;
    @FXML private TextField birthIdData;
    @FXML private ComboBox<String> sexData;
    @FXML private ComboBox<String> bloodGroupData;
    @FXML private TextField addressData;
    @FXML private TextField phoneData;
    @FXML private TextField emailData;
    public Label missingValuesMsg;

    // Save button
    @FXML
    private void updatePatientInfo(ActionEvent event) throws IOException {

        String birthIdText = birthIdData.getText();
        String birthDateFromId = Patient.getYear(birthIdText) + "-" + Patient.getMonth(birthIdText) + "-" + Patient.getDay(birthIdText);
        javaPostgreSql.updatePatient(selectedPatient.getId(), firstNameData.getText(), lastNameData.getText(), insuranceCoData.getSelectionModel().getSelectedItem(), birthDateFromId, sexData.getSelectionModel().getSelectedItem(), bloodGroupData.getSelectionModel().getSelectedItem(), addressData.getText(), phoneData.getText(), emailData.getText(), birthIdData.getText());
        switchToPatientInfo(event);
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("Patient info was updated successfully!");
        a.show();
    }

    // Cancel button
    @FXML
    private void switchToPatientInfo(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToPatientInfo(loggedInUser, selectedPatient, event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        insuranceCoData.setItems(FXCollections.observableArrayList("Union", "Dôvera", "VŠZP"));
        sexData.setItems(FXCollections.observableArrayList("male","female"));
        bloodGroupData.setItems(FXCollections.observableArrayList("A","A+","A-","B","B+","B-","AB","AB+","AB-", "O","O+","O-"));
    }

    public void initData(Patient patient, User user) {
        selectedPatient = patient;
        loggedInUser = user;
        firstNameAndLastName.setText(selectedPatient.getFirstName() + " " + selectedPatient.getLastName());
        firstNameData.setText(selectedPatient.getFirstName());
        lastNameData.setText(selectedPatient.getLastName());
        insuranceCoData.getSelectionModel().select(String.valueOf(selectedPatient.getInsuranceCompany()));
        birthIdData.setText(String.valueOf(selectedPatient.getBirthId()));
        sexData.getSelectionModel().select(String.valueOf(selectedPatient.getSex()));
        bloodGroupData.getSelectionModel().select(String.valueOf(selectedPatient.getBloodGroup()));
        addressData.setText(selectedPatient.getAddress());
        phoneData.setText(selectedPatient.getPhone());
        emailData.setText(selectedPatient.getEmail());
    }
}
