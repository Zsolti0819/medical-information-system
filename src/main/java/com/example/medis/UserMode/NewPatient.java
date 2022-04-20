package com.example.medis.UserMode;

import com.example.medis.Entities.Patient;
import com.example.medis.Entities.User;
import com.example.medis.JavaPostgreSql;
import com.example.medis.SceneController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
    @FXML private Button createPatientButton;

    // Create patient button
    @FXML
    private void createPatient(ActionEvent event) throws IOException {
        String firstNameText = first_name_data.getText();
        String lastNameText = last_name_data.getText();
        String insuranceCompanyText = insurance_co_data.getValue();
        String birthIdText = birth_ID_data.getText();
        String sexValue = sex_data.getValue();
        String birthDateFromId = "";
        String bloodGroupValue = blood_group_data.getValue();
        String addressText = address_data.getText();
        String phoneText = phone_data.getText();
        String emailText = email_data.getText();


        if (!(firstNameText.equals("") || lastNameText.equals("") || insuranceCompanyText.equals("") | bloodGroupValue.equals("") || sexValue.equals("") || addressText.equals("") || emailText.equals("") || phoneText.equals("") || birthIdText.equals(""))){
            if (validationBirthID(birthIdText)){
                birthDateFromId = Patient.getYear(birthIdText) + "-" + Patient.getMonth(birthIdText) + "-" + Patient.getDay(birthIdText);
                if (validationPhone(phoneText)){
                    if (validationEmail(emailText)){
                        missing_values_msg.setText(JavaPostgreSql.createPatient(firstNameText, lastNameText, insuranceCompanyText, birthDateFromId, Patient.getGender(birthIdText), bloodGroupValue, addressText, phoneText, emailText, birthIdText));
                        switchToDashboard(event);
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setContentText("Patient "+ firstNameText + " " + lastNameText + " was created successfully!");
                        a.show();
                    }
                    else{
                        missing_values_msg.setText("Please enter valid email!");
                    }
                }
                else{
                    missing_values_msg.setText("Please enter valid phone number!");
                }
            }else{

                missing_values_msg.setText("Please enter valid birth id!");
            }
        }
        else {

            missing_values_msg.setText("Please fill in missing compulsory data!");
        }
    }


    public boolean validationEmail(String emailText){
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(emailText);
        return matcher.matches();
    }

    public boolean validationPhone(String phoneText){
        String regex = "^[0-9]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneText);
        return matcher.matches();
    }

    public boolean validationBirthID(String birthId){
        String regex = "^[0-9]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(birthId);
        if (matcher.matches()){
            if (Patient.hasValidID(birthId)) {
                return true;
            }
            return false;
        }
        else {
            return false;
        }
    }

    // Cancel button
    @FXML
    private void switchToDashboard(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToDashboard(loggedInUser, event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        insurance_co_data.setItems(FXCollections.observableArrayList("Union", "Dôvera", "VŠZP"));
        sex_data.setItems(FXCollections.observableArrayList("male","female"));
        blood_group_data.setItems(FXCollections.observableArrayList("A","A+","A-","B","B+","B-","AB","AB+","AB-", "O","O+","O-"));

    }
    public void initData(User user) {
        loggedInUser = user;
    }
}
