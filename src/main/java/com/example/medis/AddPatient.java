package com.example.medis;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPatient implements Initializable {

    @FXML
    private ComboBox<String> bloodGroup;

    @FXML
    private ComboBox<String> sex;

    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    private TextField phone;

    @FXML
    private TextField birthMonth;

    @FXML
    private TextField birthDay;

    @FXML
    private TextField birthYear;

    @FXML
    private Button confirmData;

    @FXML
    private Button cancel;

    @FXML
    private TextField birthnumber;

    @FXML
    private TextField email;

    @FXML
    Label missingValuesMsg;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        sex.setItems(FXCollections.observableArrayList("Male","Female"));

        bloodGroup.setItems(FXCollections.observableArrayList("A","A+","A-","B","B+","B-",
                                                                            "AB","AB+","AB-",
                                                                            "0","0+","0-"));
    }

    @FXML
    private void fetchData(ActionEvent event) throws IOException {
        String fname = name.getText();
        String fsurname = surname.getText();
        String fbloodGroup = bloodGroup.getValue();
        String fsex = sex.getValue();
        String fphone = phone.getText();
        String fbirtnumber = birthnumber.getText();
        String birhtday = birthDay.getText() ;
        String birthmonth = birthMonth.getText() ;
        String birthyear =  birthYear.getText();
        String femail = email.getText();

        System.out.println(fbloodGroup);
        System.out.println(fsex);

        if (fname.toString().equals("") || fsurname.toString().equals("") || fbloodGroup.toString().equals("") ||
                fsex.toString().equals("") || fbirtnumber.toString().equals("") || birhtday.length() != 2 ||
                birthmonth.length() != 2 || birthyear.length() != 4){
            missingValuesMsg.setText("Please fill in missing compulsory data!");
        }
    }



}
