package com.example.medis.UserMode;

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
    @FXML private TextField surname_data;
    @FXML private ComboBox<String> insurance_co_data;
    @FXML private TextField birth_ID_data;
    @FXML private ComboBox<String> sex_data;
    @FXML private ComboBox<String> blood_group_data;
    @FXML private TextField address_data;
    @FXML private TextField phone_data;
    @FXML private TextField email_data;
    @FXML private Label missing_values_msg;

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

        System.out.println(year);
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
        String firstNameText = first_name_data.getText();
        String surnameText = surname_data.getText();
        String insuranceCompanyText = insurance_co_data.getValue();
        String birthIdText = birth_ID_data.getText();
        String birthDateFromId = getYear(birthIdText) + "-" + getMonth(birthIdText) + "-" + getDay(birthIdText);
        String sexValue = sex_data.getValue();
        String bloodGroupValue = blood_group_data.getValue();
        String addressText = address_data.getText();
        String phoneText = phone_data.getText();
        String emailText = email_data.getText();

        System.out.println(firstNameText);
        System.out.println(surnameText);
        System.out.println(insuranceCompanyText);
        System.out.println(birthIdText);
        System.out.println(birthDateFromId);
        System.out.println(sexValue);
        System.out.println( bloodGroupValue);
        System.out.println(addressText);
        System.out.println(phoneText);
        System.out.println( emailText);

        if (firstNameText.equals("") || surnameText.equals("") || bloodGroupValue.equals("") || sexValue.equals("") || birthIdText.isEmpty()) {
            missing_values_msg.setText("Please fill in missing compulsory data!");
        }

        else {
            missing_values_msg.setText(JavaPostgreSql.createPatient(firstNameText, surnameText, insuranceCompanyText, birthDateFromId, getGender(birthIdText), bloodGroupValue, addressText, phoneText, emailText, birthIdText));
        }
    }

    @FXML public void switchToDashboard(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToDashboard(loggedInUser, event);
    }

}
