package com.example.medis.UserMode;

import com.example.medis.JavaPostgreSql;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NewPatient implements Initializable {

    @FXML
    private TextField first_name;

    @FXML
    private TextField surname;

    @FXML
    private ComboBox<String> insurance_company;

    @FXML
    private TextField birth_id;


    @FXML
    private ComboBox<String> sex;

    @FXML
    private ComboBox<String> blood_group;

    @FXML
    private TextField address1;

    @FXML
    private TextField address2;

    @FXML
    private TextField phone;

    @FXML
    private TextField email;

    @FXML
    Label missingValuesMsg;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        insurance_company.setItems(FXCollections.observableArrayList("Union", "Dôvera", "VŠZP"));
        sex.setItems(FXCollections.observableArrayList("Male","Female"));
        blood_group.setItems(FXCollections.observableArrayList("A","A+","A-","B","B+","B-","AB","AB+","AB-", "0","0+","0-"));

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

    String getGender(String identificationNumber) {

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

    int getYear(String identificationNumber) {
        long identificationNumberLong = Long.parseLong(identificationNumber);
        long date = identificationNumberLong / 10000;
        int year = (int) (date / 10000 % 100);

        if (year >= 20)
            year += 1900;

        else
            year += 2000;

        return year;
    }

    int getMonth(String identificationNumber) {
        long identificationNumberLong = Long.parseLong(identificationNumber);
        long date = identificationNumberLong / 10000;
        int month = (int) (date / 100 % 100);

        if (month >= 51 && month <= 62)
            month -= 50;

        return month;
    }

    int getDay(String identificationNumber) {
        long identificationNumberLong = Long.parseLong(identificationNumber);
        long date = identificationNumberLong / 10000;

        return (int) (date % 100);
    }
    @FXML
    private void fetchData() {
        String firstNameText = first_name.getText();
        String surnameText = surname.getText();
        String insuranceCompanyText = insurance_company.getValue();
        String birthIdText = birth_id.getText();
//        String birthDateValue = String.valueOf(birthDate.getValue());
        String birthDateFromId = getYear(birthIdText) + "-" + getMonth(birthIdText) + "-" + getDay(birthIdText);
        String sexValue = sex.getValue();
        String bloodGroupValue = blood_group.getValue();
        String address1Text = address1.getText();
        String address2Text = address2.getText();
        String addressFull = address1Text + " " + address2Text;
        String phoneText = phone.getText();
        String emailText = email.getText();


//        JavaPostgreSql.createPatient(nameAndSurnameText[0], nameAndSurnameText[1], insuranceCompanyText, birthDateFromId, getGender(birthNumberValue), bloodGroupValue, addressFull, phoneText, emailText);
//        System.out.println(nameAndSurnameText);
//        System.out.println(insuranceCompanyText);
//        System.out.println(birthNumberValue);
//        System.out.println(birthDateValue);
//        System.out.println(sexValue);
//        System.out.println(bloodGroupValue);
//        System.out.println(address1Text);
//        System.out.println(address2Text);
//        System.out.println(phoneText);
//        System.out.println(emailText);

        // TO DO

        if (firstNameText.equals("") || surnameText.equals("") || bloodGroupValue.equals("") || sexValue.equals("") || birthIdText.equals("")) {
            missingValuesMsg.setText("Please fill in missing compulsory data!");
        }

        else {
            missingValuesMsg.setText(JavaPostgreSql.createPatient(firstNameText, surnameText, insuranceCompanyText, birthDateFromId, getGender(birthIdText), bloodGroupValue, addressFull, phoneText, emailText, birthIdText));
        }
    }

    @FXML public void closeCurrentWindow(InputEvent e) {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
