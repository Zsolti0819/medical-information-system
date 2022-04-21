package com.example.medis.UserMode;

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

    @FXML private Label name_and_last_name_data;
    @FXML private Label insurance_co_data;
    @FXML private Label birth_ID_data;
    @FXML private Label birth_date_data;
    @FXML private Label sex_data;
    @FXML private Label blood_group_data;
    @FXML private Label address_data;
    @FXML private Label phone_data;
    @FXML private Label email_data;

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

    @FXML
    private void closeCurrentWindow(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToDashboard(loggedInUser, event);
    }

    // Delete patient button
    @FXML
    private void deletePatient(ActionEvent event) throws IOException {
        if (!loggedInUser.getPosition().equals("receptionist")) {
            javaPostgreSql.deletePatient(selectedPatient.getId());
            closeCurrentWindow(event);
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

    public void initData(Patient patient, User user) {
        selectedPatient = javaPostgreSql.getPatient(patient.getId());
        loggedInUser = user;
        name_and_last_name_data.setText(selectedPatient.getFirst_name() + " " + selectedPatient.getLast_name());
        insurance_co_data.setText(selectedPatient.getInsurance_company());
        birth_ID_data.setText(String.valueOf(selectedPatient.getBirth_id()));
        birth_date_data.setText(String.valueOf(selectedPatient.getBirth_date()));
        sex_data.setText(selectedPatient.getSex());
        blood_group_data.setText(selectedPatient.getBlood_group());
        address_data.setText(selectedPatient.getAddress());
        phone_data.setText(selectedPatient.getPhone());
        email_data.setText(selectedPatient.getEmail());
    }
}
