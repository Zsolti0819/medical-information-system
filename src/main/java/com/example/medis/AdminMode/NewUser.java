package com.example.medis.AdminMode;

import com.example.medis.Entities.Patient;
import com.example.medis.Entities.User;
import com.example.medis.JavaPostgreSql;
import com.example.medis.SceneController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewUser implements Initializable {

    private User loggedInUser;
    private final JavaPostgreSql javaPostgreSql = new JavaPostgreSql();

    @FXML
    private TextField first_name_data;
    @FXML private TextField last_name_data;
    @FXML private ComboBox<String> positionData;
    @FXML private TextField birth_ID_data;
    @FXML private TextField address_data;
    @FXML private TextField phone_data;
    @FXML private TextField email_data;
    @FXML private TextField passwordData;
    @FXML private DatePicker birthdayData;
    @FXML private Label missing_values_msg;
    @FXML private Button createUserButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        positionData.setItems(FXCollections.observableArrayList("doctor", "nurse", "recepcionist", "admin"));

    }


    //Cancel button
    @FXML
    private void switchToUsers(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToUsers(loggedInUser, event);
    }


    public void initData(User user) {
        loggedInUser = user;

    }

    // Add user button
    @FXML
    private void createUser(ActionEvent event) throws IOException {

        String birthDateFromId = "";

        String firstNameText = first_name_data.getText();
        String lastNameText = last_name_data.getText();
        String positionText = positionData.getValue();
        String addressText = address_data.getText();
        String phoneText = phone_data.getText();
        String emailText = email_data.getText();
        String passwordText = passwordData.getText();
        LocalDate birthdayText = birthdayData.getValue();



        if(!firstNameText.isEmpty() && !lastNameText.isEmpty() && positionText!=null && !passwordText.isEmpty() &&  !addressText.isEmpty() && !phoneText.isEmpty() && !emailText.isEmpty() && birthdayText != null){
          //  if (validationBirthID(birthIdText)){
              //  birthDateFromId = Patient.getYear(birthIdText) + "-" + Patient.getMonth(birthIdText) + "-" + Patient.getDay(birthIdText);
                if (validationPhone(phoneText)){
                    if (validationEmail(emailText)){
                      //  missing_values_msg.setText(javaPostgreSql.createUser(firstNameText, lastNameText,positionText ,addressText, phoneText, emailText, passwordText, birthdayText.toString()));
                        switchToUsers(event);
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setContentText("User "+ firstNameText + " " + lastNameText + " was created successfully!");
                        a.show();
                    }
                    else{
                        missing_values_msg.setText("Please enter valid email!");
                    }
                }
                else{
                    missing_values_msg.setText("Please enter valid phone number!");
                }
          //  }
            /*else{

                missing_values_msg.setText("Please enter valid birth id!");
            }*/
        }
        else {
            missing_values_msg.setText("Please fill in missing compulsory data!");
        }

    }

    public static boolean validator(String... strings) {
        for (String s : strings)
            if (s == null || s.isEmpty())
                return true;
        return false;
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

   /* public boolean validationBirthID(String birthId){
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

    }*/
}
