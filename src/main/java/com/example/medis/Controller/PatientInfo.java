package com.example.medis.Controller;

import com.example.medis.Entity.Patient;
import com.example.medis.Entity.User;
import com.example.medis.GeneralLogger;
import com.example.medis.Model.JavaPostgreSql;
import com.example.medis.ControllerBuffer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
        ControllerBuffer s = new ControllerBuffer();
        s.switchToPatientInfoEdit(loggedInUser, selectedPatient, event);
    }

    // Records
    @FXML
    private void switchToRecords(ActionEvent event) throws IOException {
        if (!loggedInUser.getPosition().equals("receptionist")){
            ControllerBuffer s = new ControllerBuffer();
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
        ControllerBuffer s = new ControllerBuffer();
        s.switchToAppointments(loggedInUser, selectedPatient, event);
    }

    // Prescriptions
    @FXML
    private void switchToPrescriptions(ActionEvent event) throws IOException {
        if (loggedInUser.getPosition().equals("doctor")) {
            ControllerBuffer s = new ControllerBuffer();
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
    private void deletePatient(ActionEvent event) {
        if (!loggedInUser.getPosition().equals("receptionist")) {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setContentText("Are you sure you want to delete patient " + selectedPatient.getFirstName() + " " + selectedPatient.getLastName() + "?");
            a.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    javaPostgreSql.deletePatient(selectedPatient.getId());
                    ControllerBuffer s = new ControllerBuffer();
                    try {
                        switchToPatients(event);
                        Alert b = new Alert(Alert.AlertType.INFORMATION);
                        b.setContentText("Patient "+ selectedPatient.getFirstName() + " " + selectedPatient.getLastName() + " was deleted successfully!");
                        b.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            });
        }
        else{
            GeneralLogger.log(Level.WARNING, "DELETE | DENIED | PATIENT: Denied for " + loggedInUser.getId() );
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("You don't have permissions to delete Patient!");
            a.show();
        }
    }



    @FXML
    private void switchToPatients(ActionEvent event) throws IOException {
        ControllerBuffer s = new ControllerBuffer();
        s.switchToPatients(loggedInUser, event);
    }

    public void initData(Patient patient, User user) {
        loggedInUser = user;
        selectedPatient = javaPostgreSql.getPatient(patient.getId());
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
