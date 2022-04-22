package com.example.medis.Controller;

import com.example.medis.Entity.Patient;
import com.example.medis.Entity.User;
import com.example.medis.Model.JavaPostgreSql;
import com.example.medis.ControllerBuffer;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
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
    private final JavaPostgreSql javaPostgreSql = new JavaPostgreSql();

    @FXML private TextField firstNameData;
    @FXML private TextField lastNameData;
    @FXML private ComboBox<String> insuranceCoData;
    @FXML private TextField birthIdData;
    @FXML private ComboBox<String> sexData;
    @FXML private ComboBox<String> bloodGroupData;
    @FXML private TextField addressData;
    @FXML private TextField phoneData;
    @FXML private TextField emailData;
    @FXML private Label missingValuesMsg;

    // Create patient button
    @FXML
    private void createPatient(ActionEvent event) throws IOException {

        String birthDateFromId = "";

        String firstNameText = firstNameData.getText();
        String lastNameText = lastNameData.getText();
        String insuranceCompanyText = insuranceCoData.getValue();
        String birthIdText = birthIdData.getText();
        String sexValue = sexData.getValue();
        String bloodGroupValue = bloodGroupData.getValue();
        String addressText = addressData.getText();
        String phoneText = phoneData.getText();
        String emailText = emailData.getText();


        if(!firstNameText.isEmpty() && !lastNameText.isEmpty() && insuranceCompanyText!=null && !birthIdText.isEmpty() && sexValue!=null && bloodGroupValue!=null && !addressText.isEmpty() && !phoneText.isEmpty() && !emailText.isEmpty()){
            if (validationBirthID(birthIdText)){
                birthDateFromId = Patient.getYear(birthIdText) + "-" + Patient.getMonth(birthIdText) + "-" + Patient.getDay(birthIdText);
                if (validationPhone(phoneText)){
                    if (validationEmail(emailText)){
                        missingValuesMsg.setText(javaPostgreSql.createPatient(firstNameText, lastNameText, insuranceCompanyText, birthDateFromId, Patient.getGender(birthIdText), bloodGroupValue, addressText, phoneText, emailText, birthIdText));
                        switchToPatients(event);
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setContentText("Patient "+ firstNameText + " " + lastNameText + " was created successfully!");
                        a.show();
                    }
                    else{
                        missingValuesMsg.setText("Please enter valid email!");
                    }
                }
                else{
                    missingValuesMsg.setText("Please enter valid phone number!");
                }
            }else{

                missingValuesMsg.setText("Please enter valid birth id!");
            }
        }
        else {
            missingValuesMsg.setText("Please fill in missing compulsory data!");
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
    private void switchToPatients(ActionEvent event) throws IOException {
        ControllerBuffer s = new ControllerBuffer();
        s.switchToPatients(loggedInUser, event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        insuranceCoData.setItems(FXCollections.observableArrayList("Union", "Dôvera", "VŠZP"));
        sexData.setItems(FXCollections.observableArrayList("male","female"));
        bloodGroupData.setItems(FXCollections.observableArrayList("A","A+","A-","B","B+","B-","AB","AB+","AB-", "O","O+","O-"));

    }
    public void initData(User user) {
        loggedInUser = user;
    }
}
