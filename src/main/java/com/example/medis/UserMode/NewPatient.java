package com.example.medis.UserMode;

import com.example.medis.Entities.Patient;
import com.example.medis.Entities.User;
import com.example.medis.JavaPostgreSql;
import com.example.medis.SceneController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewPatient implements Initializable {

    private User loggedInUser;

    @FXML private TextField first_name_data;
    @FXML private TextField last_name_data;
    @FXML private ComboBox<String> insurance_co_data;
    @FXML private TextField birth_ID_data;
    @FXML private ComboBox<String> sex_data;
    @FXML private ComboBox<String> blood_group_data;
    @FXML private TextField address_data;
    @FXML private TextField phone_data;
    @FXML private TextField email_data;
    @FXML private Label missing_values_msg;


    // Create patient button
    @FXML private void createPatient(ActionEvent event) throws IOException {
        String firstNameText = first_name_data.getText();
        String lastNameText = last_name_data.getText();
        String insuranceCompanyText = insurance_co_data.getValue();
        String birthIdText = birth_ID_data.getText();
        String birthDateFromId = Patient.getYear(birthIdText) + "-" + Patient.getMonth(birthIdText) + "-" + Patient.getDay(birthIdText);
        String sexValue = sex_data.getValue();
        String bloodGroupValue = blood_group_data.getValue();
        String addressText = address_data.getText();
        String phoneText = phone_data.getText();
        String emailText = email_data.getText();

        System.out.println(firstNameText);
        System.out.println(lastNameText);
        System.out.println(insuranceCompanyText);
        System.out.println(birthIdText);
        System.out.println(birthDateFromId);
        System.out.println(sexValue);
        System.out.println( bloodGroupValue);
        System.out.println(addressText);
        System.out.println(phoneText);
        System.out.println( emailText);

        if (firstNameText.equals("") || lastNameText.equals("") || bloodGroupValue.equals("") || sexValue.equals("") || birthIdText.isEmpty()) {
            missing_values_msg.setText("Please fill in missing compulsory data!");
        }

        else {
            missing_values_msg.setText(JavaPostgreSql.createPatient(firstNameText, lastNameText, insuranceCompanyText, birthDateFromId, Patient.getGender(birthIdText), bloodGroupValue, addressText, phoneText, emailText, birthIdText));
            switchToDashboard(event);
        }
    }

    // Cancel button
    @FXML public void switchToDashboard(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToDashboard(loggedInUser, event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        insurance_co_data.setItems(FXCollections.observableArrayList("Union", "Dôvera", "VŠZP"));
        sex_data.setItems(FXCollections.observableArrayList("male","female"));
        blood_group_data.setItems(FXCollections.observableArrayList("A","A+","A-","B","B+","B-","AB","AB+","AB-", "O","O+","O-"));

    }
}
