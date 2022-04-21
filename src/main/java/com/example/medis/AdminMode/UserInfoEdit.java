package com.example.medis.AdminMode;

import com.example.medis.Entities.Patient;
import com.example.medis.Entities.User;
import com.example.medis.GeneralLogger;
import com.example.medis.JavaPostgreSql;
import com.example.medis.SceneController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class UserInfoEdit implements Initializable {

    private User loggedInUser;
    private User selectedUser;

    @FXML private ComboBox position_data;
    @FXML private TextField first_name_data;
    @FXML private TextField last_name_data;
    @FXML private TextField password_data;
    @FXML private TextField address_data;
    @FXML private TextField username_data;
    @FXML private TextField phone_data;
    @FXML private TextField email_data;
    @FXML private DatePicker birthdate_data;


    JavaPostgreSql javaPostgreSql = new JavaPostgreSql();

    @FXML
    private void updateUserInfo(ActionEvent event) throws IOException {

        //String birthIdText = birth_ID_data.getText();
        //String birthDateFromId = Patient.getYear(birthIdText) + "-" + Patient.getMonth(birthIdText) + "-" + Patient.getDay(birthIdText);
        System.out.println(selectedUser.getId());
        System.out.println(username_data.getText());
        System.out.println(first_name_data.getText());
        System.out.println( last_name_data.getText());
        System.out.println(password_data.getText());
        System.out.println(email_data.getText());
        System.out.println( phone_data.getText());
        System.out.println(String.valueOf(position_data.getSelectionModel().getSelectedItem()));
        System.out.println(birthdate_data.getValue().toString());


        javaPostgreSql.updateUser(selectedUser.getId(), username_data.getText(), first_name_data.getText(), last_name_data.getText(),  password_data.getText(), email_data.getText(), phone_data.getText(), String.valueOf(position_data.getSelectionModel().getSelectedItem()), birthdate_data.getValue().toString()  );
        switchToUserInfo(event);
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("Patient info was updated successfully!");
        a.show();
    }

    //Delete
    @FXML
    private void deleteUser(ActionEvent event) throws IOException {
            javaPostgreSql.deletePatient(selectedUser.getId());
            switchToUsers(event);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("User was deleted successfully!");
            a.show();


    }

    private void switchToUsers(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToUsers(loggedInUser, event);
    }

    // Cancel button
    @FXML
    private void switchToUserInfo(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToUserInfo(loggedInUser, selectedUser, event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        position_data.setItems(FXCollections.observableArrayList("doctor", "nurse", "receptionist", "admin"));

    }

    public void initData(User user, User user2)  {
        loggedInUser = user;
        selectedUser = user2;
    }


}
