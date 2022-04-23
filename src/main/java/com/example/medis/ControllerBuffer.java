package com.example.medis;

import com.example.medis.Controller.NewUser;
import com.example.medis.Controller.UserInfo;
import com.example.medis.Controller.EditUserInfo;
import com.example.medis.Controller.Users;
import com.example.medis.Controller.*;
import com.example.medis.Entity.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ControllerBuffer {
    public ControllerBuffer() {

    }


    public static void setLocale(Locale locale) {
        ControllerBuffer.locale = locale;
    }


    public static Locale getLocale() {
        return locale;
    }

    private static Locale locale;

    // Admin mode

    public void switchToUserCreation(User loggedInUser, MouseEvent event) throws IOException {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("new_user.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Medis");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        NewUser controller = loader.getController();
        controller.initData(loggedInUser);

        stage.show();
    }

    public void switchToUsers(User loggedInUser, ActionEvent event) throws IOException {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("users.fxml"));
        loader.setResources(ResourceBundle.getBundle("medis", ControllerBuffer.locale));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Medis");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        Users controller = loader.getController();
        controller.initData(loggedInUser);

        stage.show();
    }

    public void switchToUserInfo(User loggedInUser, User selectedUser,ActionEvent event) throws IOException {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("user_info.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Medis");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        UserInfo controller = loader.getController();
        controller.initData(loggedInUser, selectedUser);

        stage.show();
    }

    public void switchToUserInfoEdit(User loggedInUser, User selectedUser, ActionEvent event) throws IOException {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("edit_user_info.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Medis");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        EditUserInfo controller = loader.getController();
        controller.initData(loggedInUser, selectedUser);

        stage.show();
    }

    // Dashboard

    public void switchToPatients(User loggedInUser, ActionEvent event) throws IOException {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("patients.fxml"));
        loader.setResources(ResourceBundle.getBundle("medis", ControllerBuffer.locale));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Medis");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        Patients controller = loader.getController();
        controller.initData(loggedInUser);

        stage.show();
    }

    public void switchToLogout(User loggedInUser, ActionEvent event) throws IOException {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Medis");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        Login controller = loader.getController();
        controller.initData(loggedInUser);

        stage.show();
    }

    // Patient info

    public void switchToPatientInfo(User loggedInUser, Patient patient, ActionEvent event) throws IOException {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("patient_info.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Medis");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        PatientInfo controller = loader.getController();
        controller.initData(patient, loggedInUser);

        stage.show();
    }

    public void switchToPatientInfoEdit(User loggedInUser, Patient patient, ActionEvent event) throws IOException {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("edit_patient_info.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Edit patient info");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        EditPatientInfo controller = loader.getController();
        controller.initData(patient, loggedInUser);

        stage.show();
    }

    public void switchToPatientCreation(User loggedInUser, MouseEvent event) throws IOException {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("new_patient.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("New patient");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        NewPatient controller = loader.getController();
        controller.initData(loggedInUser);

        stage.show();
    }

    // Records

    public void switchToRecords(User loggedInUser, Patient patient, ActionEvent event) throws IOException {
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("records.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Medis");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        Records controller = loader.getController();
        controller.initData(patient, loggedInUser);

        stage.show();
    }

    public void switchToRecordDetailed(User loggedInUser, Patient patient, Record record, ActionEvent event) throws IOException {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("detailed_record.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Record details");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        DetailedRecord controller = loader.getController();
        controller.initData(patient, record, loggedInUser);

        stage.show();
    }

    public void switchToRecordEdit(User loggedInUser, Patient patient, Record record, ActionEvent event) throws IOException {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("edit_record.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Edit record");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        EditRecord controller = loader.getController();
        controller.initData(patient, record, loggedInUser);

        stage.show();
    }

    public void switchToRecordCreation(User loggedInUser, Patient patient, MouseEvent event) throws IOException {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("new_record.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("New record");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        NewRecord controller = loader.getController();
        controller.initData(patient, loggedInUser);

        stage.show();
    }

    // Appointments

    public void switchToAppointments(User loggedInUser, Patient patient, ActionEvent event) throws IOException {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("appointments.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Medis");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        Appointments controller = loader.getController();
        controller.initData(patient, loggedInUser);

        stage.show();
    }

    public void switchToAppointmentEdit(User loggedInUser, Patient patient, Appointment appointment, ActionEvent event) throws IOException {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("edit_appointment.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Edit appointment");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        EditAppointment controller = loader.getController();
        controller.initData(patient, appointment, loggedInUser);

        stage.show();
    }

    public void switchToAppointmentCreation(User loggedInUser, Patient patient, MouseEvent event) throws IOException {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("new_appointment.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("New appointment");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        NewAppointment controller = loader.getController();
        controller.initData(patient, loggedInUser);

        stage.show();
    }

    // Prescriptions

    public void switchToPrescriptions(User loggedInUser, Patient patient, ActionEvent event) throws IOException {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("prescriptions.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Medis");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        Prescriptions controller = loader.getController();
        controller.initData(patient, loggedInUser);

        stage.show();
    }

    public void switchToPrescriptionEdit(User loggedInUser, Patient patient, Prescription prescription, ActionEvent event) throws IOException {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("edit_prescription.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Edit prescription");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        EditPrescription controller = loader.getController();
        controller.initData(patient, prescription, loggedInUser);

        stage.show();
    }

    public void switchToPrescriptionCreation(User loggedInUser, Patient patient, MouseEvent event) throws IOException {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("new_prescription.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("New prescription");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        NewPrescription controller = loader.getController();
        controller.initData(patient, loggedInUser);

        stage.show();
    }
}