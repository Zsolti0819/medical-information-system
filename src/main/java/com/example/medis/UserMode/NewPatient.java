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
import javafx.scene.input.InputEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewPatient implements Initializable {

    private User loggedInUser;

    @FXML private TextField first_name;
    @FXML private TextField surname;
    @FXML private ComboBox<String> insurance_co_data;
    @FXML private TextField birth_id;
    @FXML private ComboBox<String> sex_data;
    @FXML private ComboBox<String> blood_group_data;
    @FXML private TextField address1;
    @FXML private TextField address2;
    @FXML private TextField phone;
    @FXML private TextField email;
    @FXML private Label missingValuesMsg;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        insurance_co_data.setItems(FXCollections.observableArrayList("Union", "Dôvera", "VŠZP"));
        sex_data.setItems(FXCollections.observableArrayList("Male","Female"));
        blood_group_data.setItems(FXCollections.observableArrayList("A","A+","A-","B","B+","B-","AB","AB+","AB-", "0","0+","0-"));

    }

    public boolean hasValidID(String identificationNumber) {
        long identificationNumberLong = Long.parseLong(identificationNumber);
        long date = identificationNumberLong / 10000;
        int day = (int) (date % 100);
        int month = (int) (date / 100 % 100);

        if (month >= 51 && month <= 62)
            month -= 50;

        int[] mdays = {0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        return identificationNumberLong % 11 == 0 && month > 0 && (month <= 12 || month >= 51) &&
                month <= 62 && day > 0 && day <= mdays[month];
    }

    public String getGender(String identificationNumber) {

        if (!(identificationNumber.equals(""))) {
            long identificationNumberLong = Long.parseLong(identificationNumber);
            long date = identificationNumberLong / 10000;
            int month = (int) (date / 100 % 100);
            if (month >= 51 && month <= 62)
                return "female";
            else
                return "male";
        }
        return null;
    }

    public int getYear(String identificationNumber) {
        long identificationNumberLong = Long.parseLong(identificationNumber);
        long date = identificationNumberLong / 10000;
        int year = (int) (date / 10000 % 100);

        if (year >= 20)
            year += 1900;

        else
            year += 2000;

        return year;
    }

    public int getMonth(String identificationNumber) {
        long identificationNumberLong = Long.parseLong(identificationNumber);
        long date = identificationNumberLong / 10000;
        int month = (int) (date / 100 % 100);

        if (month >= 51 && month <= 62)
            month -= 50;

        return month;
    }

    public int getDay(String identificationNumber) {
        long identificationNumberLong = Long.parseLong(identificationNumber);
        long date = identificationNumberLong / 10000;

        return (int) (date % 100);
    }

    @FXML private void createPatient() {
        String firstNameText = first_name.getText();
        String surnameText = surname.getText();
        String insuranceCompanyText = insurance_co_data.getValue();
        String birthIdText = birth_id.getText();
        String birthDateFromId = getYear(birthIdText) + "-" + getMonth(birthIdText) + "-" + getDay(birthIdText);
        String sexValue = sex_data.getValue();
        String bloodGroupValue = blood_group_data.getValue();
        String address1Text = address1.getText();
        String address2Text = address2.getText();
        String addressFull = address1Text + " " + address2Text;
        String phoneText = phone.getText();
        String emailText = email.getText();

        if (firstNameText.equals("") || surnameText.equals("") || bloodGroupValue.equals("") || sexValue.equals("") || birthIdText.equals("")) {
            missingValuesMsg.setText("Please fill in missing compulsory data!");
        }

        else {
            missingValuesMsg.setText(JavaPostgreSql.createPatient(firstNameText, surnameText, insuranceCompanyText, birthDateFromId, getGender(birthIdText), bloodGroupValue, addressFull, phoneText, emailText, birthIdText));
        }
    }

    @FXML public void switchToDashboard(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToDashboard(loggedInUser, event);
    }

}
