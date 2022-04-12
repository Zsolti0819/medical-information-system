package com.example.medis.UserMode;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NewPatient implements Initializable {

    @FXML
    private TextField nameAndSurname;

    @FXML
    private TextField insuranceCompany;

    @FXML
    private TextField birthNumber;

    @FXML
    private DatePicker birthDate;

    @FXML
    private ComboBox<String> sex;

    @FXML
    private ComboBox<String> bloodGroup;

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

        sex.setItems(FXCollections.observableArrayList("Male","Female"));

        bloodGroup.setItems(FXCollections.observableArrayList("A","A+","A-","B","B+","B-","AB","AB+","AB-", "0","0+","0-"));

    }

    @FXML
    private void fetchData() {
        String nameAndSurnameText = nameAndSurname.getText();
        String insuranceCompanyText = insuranceCompany.getText();
        String birthNumberText = birthNumber.getText();
        String birthDateValue = String.valueOf(birthDate.getValue());
        String sexValue = sex.getValue();
        String bloodGroupValue = bloodGroup.getValue();
        String address1Text = address1.getText();
        String address2Text = address2.getText();
        String phoneText = phone.getText();
        String emailText = email.getText();

        System.out.println(nameAndSurnameText);
        System.out.println(insuranceCompanyText);
        System.out.println(birthNumberText);
        System.out.println(birthDateValue);
        System.out.println(sexValue);
        System.out.println(bloodGroupValue);
        System.out.println(address1Text);
        System.out.println(address2Text);
        System.out.println(phoneText);
        System.out.println(emailText);

        // TO DO

        if (nameAndSurnameText.equals("") || bloodGroupValue.equals("") || sexValue.equals("") || birthNumberText.equals("")) {
            missingValuesMsg.setText("Please fill in missing compulsory data!");
        }

        else {
            missingValuesMsg.setText("Success!");
        }
    }

    @FXML public void closeCurrentWindow(InputEvent e) {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
