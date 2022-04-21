package com.example.medis.Controllers;

import com.example.medis.Entities.Patient;
import com.example.medis.Entities.User;
import com.example.medis.JavaPostgreSql;
import com.example.medis.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.io.IOException;

public class UserInfo {

    private User loggedInUser;
    private User selectedUser;
    JavaPostgreSql javaPostgreSql = new JavaPostgreSql();

    @FXML private Label userNameData;
    @FXML private Label lastNameData;
    @FXML private Label usernameData;
    @FXML private Label emailData;
    @FXML private Label phoneData;
    @FXML private Label positionData;
    @FXML private Label birthDateData;
    @FXML private Label firstNameData;


    // Edit user info button
    @FXML
    public void switchToUserInfoEdit(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToUserInfoEdit(loggedInUser, selectedUser, event);

    }

    //Delete user button
    @FXML
    private void deleteUser(ActionEvent event) throws IOException {
//        javaPostgreSql.(selectedUser.getId());
        switchToUsers(event);
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("User was deleted successfully!");
        a.show();
    }

    @FXML
    public void switchToUsers(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToUsers(loggedInUser, event);
    }

    public void initData(User user, User user2) {
        loggedInUser = user;
        selectedUser = user2;
        userNameData.setText(selectedUser.getUsername());
        firstNameData.setText(selectedUser.getFirstName());
        lastNameData.setText(selectedUser.getLastName());
        usernameData.setText(selectedUser.getUsername());
        emailData.setText(selectedUser.getEmail());
        phoneData.setText(selectedUser.getPhone());
        positionData.setText(selectedUser.getPosition());
        birthDateData.setText(String.valueOf(selectedUser.getBirthDate()));
    }
}
