package com.example.medis.Controllers;

import com.example.medis.Entities.Patient;
import com.example.medis.Entities.User;
import com.example.medis.GeneralLogger;
import com.example.medis.JavaPostgreSql;
import com.example.medis.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.logging.Level;

public class PatientInfo {

    private Patient selectedPatient;
    private User loggedInUser;
    private final JavaPostgreSql javaPostgreSql = new JavaPostgreSql();

    @FXML private Label patientNameData;
    @FXML private Label insuranceCoData;
    @FXML private Label birthIdData;
    @FXML private Label birthDateData;
    @FXML private Label sexData;
    @FXML private Label bloodGroupData;
    @FXML private Label addressData;
    @FXML private Label phoneData;
    @FXML private Label emailData;

    // Edit patient info button
    @FXML
    private void switchToPatientInfoEdit(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToPatientInfoEdit(loggedInUser, selectedPatient, event);
    }

    // Records
    @FXML
    private void switchToRecords(ActionEvent event) throws IOException {
        if (!loggedInUser.getPosition().equals("receptionist")){
            SceneController s = new SceneController();
            s.switchToRecords(loggedInUser, selectedPatient, event);
        }
        else{
            GeneralLogger.log(Level.WARNING, "ACCESS | DENIED | RECORDS: Denied for " + loggedInUser.getId() );
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("You don't have permissions to show Records!");
            a.show();
        }
    }

    // Appointments
    @FXML
    private void switchToAppointments(ActionEvent event) throws  IOException {
        SceneController s = new SceneController();
        s.switchToAppointments(loggedInUser, selectedPatient, event);
    }

    // Prescriptions
    @FXML
    private void switchToPrescriptions(ActionEvent event) throws IOException {
        if (loggedInUser.getPosition().equals("doctor")) {
            SceneController s = new SceneController();
            s.switchToPrescriptions(loggedInUser, selectedPatient, event);
        }
        else{
            GeneralLogger.log(Level.WARNING, "ACCESS | DENIED | PRESCRIPTIONS: Denied for " + loggedInUser.getId() );
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("You don't have permissions to show Prescriptions!");
            a.show();
        }
    }

    // Delete patient button
    @FXML
    private void deletePatient(ActionEvent event) throws IOException {
        if (!loggedInUser.getPosition().equals("receptionist")) {
            javaPostgreSql.deletePatient(selectedPatient.getId());
            switchToDashboard(event);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Patient was deleted successfully!");
            a.show();
        }
        else{
            GeneralLogger.log(Level.WARNING, "DELETE | DENIED | PATIENT: Denied for " + loggedInUser.getId() );
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("You don't have permissions to delete Patient!");
            a.show();
        }
    }

    @FXML
    private void switchToDashboard(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToDashboard(loggedInUser, event);
    }

    public void initData(Patient patient, User user) {
        selectedPatient = javaPostgreSql.getPatient(patient.getId());
        loggedInUser = user;
        patientNameData.setText(selectedPatient.getFirstName() + " " + selectedPatient.getLastName());
        insuranceCoData.setText(selectedPatient.getInsuranceCompany());
        birthIdData.setText(String.valueOf(selectedPatient.getBirthId()));
        birthDateData.setText(String.valueOf(selectedPatient.getBirthDate()));
        sexData.setText(selectedPatient.getSex());
        bloodGroupData.setText(selectedPatient.getBloodGroup());
        addressData.setText(selectedPatient.getAddress());
        phoneData.setText(selectedPatient.getPhone());
        emailData.setText(selectedPatient.getEmail());
    }
}
