package com.example.medis;

import com.example.medis.AdminMode.NewUser;
import com.example.medis.AdminMode.UserInfo;
import com.example.medis.AdminMode.UserInfoEdit;
import com.example.medis.AdminMode.Users;
import com.example.medis.Entities.*;
import com.example.medis.UserMode.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {

    public void switchToUserCreation(User loggedInUser, MouseEvent event) throws IOException {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("admin_mode/new_user.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Medis");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        NewUser controller = loader.getController();
        controller.initData(loggedInUser);

        stage.show();
    }

    public void switchToUsers(User loggedInUser, ActionEvent event) throws IOException {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("admin_mode/users.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Medis");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        Users controller = loader.getController();
        controller.initData(loggedInUser);

        stage.show();
    }

    public void switchToUserInfo(User loggedInUser, User selectedUser,ActionEvent event) throws IOException {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("admin_mode/user_info.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Medis");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        UserInfo controller = loader.getController();
        controller.initData(loggedInUser, selectedUser);

        stage.show();
    }

    public void switchToUserInfoEdit(User loggedInUser, User selectedUser, ActionEvent event) throws IOException {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("admin_mode/user_info_edit.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Medis");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        UserInfoEdit controller = loader.getController();
        controller.initData(loggedInUser, selectedUser);

        stage.show();
    }

    // Dashboard

    public void switchToDashboard(User loggedInUser, ActionEvent event) throws IOException {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("user_mode/dashboard.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Medis");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        Dashboard controller = loader.getController();
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

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("user_mode/patient_info.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Medis");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        PatientInfo controller = loader.getController();
        controller.initData(patient, loggedInUser);

        stage.show();
    }

    public void switchToPatientInfoEdit(User loggedInUser, Patient patient, ActionEvent event) throws IOException {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("user_mode/edit_patient_info.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Edit patient info");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        EditPatientInfo controller = loader.getController();
        controller.initData(patient, loggedInUser);

        stage.show();
    }

    public void switchToPatientCreation(User loggedInUser, MouseEvent event) throws IOException {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("user_mode/new_patient.fxml"));
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
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("user_mode/records.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Medis");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        Records controller = loader.getController();
        controller.initData(patient, loggedInUser);

        stage.show();
    }

    public void switchToRecordDetailed(User loggedInUser, Patient patient, Record record, ActionEvent event) throws IOException {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("user_mode/detailed_record.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Record details");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        DetailedRecord controller = loader.getController();
        controller.initData(patient, record, loggedInUser);

        stage.show();
    }

    public void switchToRecordEdit(User loggedInUser, Patient patient, Record record, ActionEvent event) throws IOException {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("user_mode/edit_record.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Edit record");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        EditRecord controller = loader.getController();
        controller.initData(patient, record, loggedInUser);

        stage.show();
    }

    public void switchToRecordCreation(User loggedInUser, Patient patient, MouseEvent event) throws IOException {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("user_mode/new_record.fxml"));
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

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("user_mode/appointments.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Medis");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        Appointments controller = loader.getController();
        controller.initData(patient, loggedInUser);

        stage.show();
    }

    public void switchToAppointmentEdit(User loggedInUser, Patient patient, Appointment appointment, ActionEvent event) throws IOException {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("user_mode/edit_appointment.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Edit appointment");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        EditAppointment controller = loader.getController();
        controller.initData(patient, appointment, loggedInUser);

        stage.show();
    }

    public void switchToAppointmentCreation(User loggedInUser, Patient patient, MouseEvent event) throws IOException {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("user_mode/new_appointment.fxml"));
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

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("user_mode/prescriptions.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Medis");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        Prescriptions controller = loader.getController();
        controller.initData(patient, loggedInUser);

        stage.show();
    }

    public void switchToPrescriptionEdit(User loggedInUser, Patient patient, Prescription prescription, ActionEvent event) throws IOException {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("user_mode/edit_prescription.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Edit prescription");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        EditPrescription controller = loader.getController();
        controller.initData(patient, prescription, loggedInUser);

        stage.show();
    }

    public void switchToPrescriptionCreation(User loggedInUser, Patient patient, MouseEvent event) throws IOException {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("user_mode/new_prescription.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("New prescription");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        NewPrescription controller = loader.getController();
        controller.initData(patient, loggedInUser);

        stage.show();
    }
}