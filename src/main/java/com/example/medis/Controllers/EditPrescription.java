package com.example.medis.Controllers;

import com.example.medis.Entities.Patient;
import com.example.medis.Entities.Prescription;
import com.example.medis.Entities.User;
import com.example.medis.JavaPostgreSql;
import com.example.medis.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class EditPrescription  {

    private Patient selectedPatient;
    private Prescription selectedPrescription;
    private User loggedInUser;
    private final JavaPostgreSql javaPostgreSql = new JavaPostgreSql();

    @FXML private Label patientNameAppointmentTitle;
    @FXML private TextField titleData;
    @FXML private TextArea descriptionData;
    @FXML private TextField drugData;
    @FXML private DatePicker expDateYmdData;
    @FXML private TextArea notesData;

    // Save button
    @FXML
    private void updatePrescription(ActionEvent event) throws IOException {
        javaPostgreSql.updatePrescription(selectedPrescription.getId(), titleData.getText(), descriptionData.getText(), drugData.getText(), expDateYmdData.getValue().toString(), selectedPatient.getId(), 1, notesData.getText());
        switchToPrescriptions(event);
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("Prescription was updated successfully!");
        a.show();
    }

    // Cancel button
    @FXML
    private void switchToPrescriptions(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToPrescriptions(loggedInUser, selectedPatient, event);
    }

    // Delete prescription button
    @FXML
    private void deletePrescription(ActionEvent event) throws IOException {
        javaPostgreSql.deletePrescription(selectedPrescription.getId());
        switchToPrescriptions(event);
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("Prescription was deleted successfully!");
        a.show();
    }

    public void initData(Patient patient, Prescription prescription, User user) {
        selectedPatient = patient;
        loggedInUser = user;
        selectedPrescription = prescription;
        patientNameAppointmentTitle.setText(selectedPatient.getFirstName() + " " + selectedPatient.getLastName() + " - " + selectedPrescription.getTitle());
        titleData.setText(selectedPrescription.getTitle());
        descriptionData.setText(selectedPrescription.getDescription());
        drugData.setText(selectedPrescription.getDrug());
        expDateYmdData.setValue(selectedPrescription.getExpirationDate().toLocalDate());
        notesData.setText(selectedPrescription.getNotes());
    }


}
