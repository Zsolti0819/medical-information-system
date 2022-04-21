package com.example.medis.AdminMode;

import com.example.medis.Entities.User;
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

public class EditUserInfo implements Initializable {

    private User loggedInUser;
    private User selectedUser;
    JavaPostgreSql javaPostgreSql = new JavaPostgreSql();

    @FXML private ComboBox<String> positionData;
    @FXML private TextField firstNameData;
    @FXML private TextField lastNameData;
    @FXML private TextField passwordData;
    @FXML private TextField usernameData;
    @FXML private TextField phoneData;
    @FXML private TextField emailData;
    @FXML private DatePicker birthdateData;

    // Save button
    @FXML
    private void updateUserInfo(ActionEvent event) throws IOException {

        System.out.println(selectedUser.getId());
        System.out.println(usernameData.getText());
        System.out.println(firstNameData.getText());
        System.out.println( lastNameData.getText());
        System.out.println(passwordData.getText());
        System.out.println(emailData.getText());
        System.out.println( phoneData.getText());
        System.out.println(String.valueOf(positionData.getSelectionModel().getSelectedItem()));
        System.out.println(birthdateData.getValue().toString());


        javaPostgreSql.updateUser(selectedUser.getId(), usernameData.getText(), firstNameData.getText(), lastNameData.getText(),  passwordData.getText(), emailData.getText(), phoneData.getText(), String.valueOf(positionData.getSelectionModel().getSelectedItem()), birthdateData.getValue().toString()  );
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
        positionData.setItems(FXCollections.observableArrayList("doctor", "nurse", "receptionist", "admin"));

    }

    public void initData(User user, User user2)  {
        loggedInUser = user;
        selectedUser = user2;
    }


}
