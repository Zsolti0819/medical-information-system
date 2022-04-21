package com.example.medis.Controllers;

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

    @FXML private TextField firstNameData;
    @FXML private TextField lastNameData;
    @FXML private TextField passwordData;
    @FXML private TextField usernameData;
    @FXML private TextField phoneData;
    @FXML private TextField emailData;
    @FXML private ComboBox<String> positionData;
    @FXML private DatePicker birthDateData;

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
        System.out.println(positionData.getSelectionModel().getSelectedItem());
        System.out.println(birthDateData.getValue().toString());

        javaPostgreSql.updateUser(selectedUser.getId(), usernameData.getText(), firstNameData.getText(), lastNameData.getText(),  passwordData.getText(), emailData.getText(), phoneData.getText(), String.valueOf(positionData.getSelectionModel().getSelectedItem()), birthDateData.getValue().toString());
        switchToUserInfo(event);
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("Patient info was updated successfully!");
        a.show();
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
        firstNameData.setText(selectedUser.getFirstName());
        lastNameData.setText(selectedUser.getLastName());
        usernameData.setText(selectedUser.getUsername());
        phoneData.setText(selectedUser.getPhone());
        emailData.setText(selectedUser.getEmail());
        positionData.getSelectionModel().select(String.valueOf(selectedUser.getPosition()));
        birthDateData.setValue(selectedUser.getBirthDate().toLocalDate());

    }
}
