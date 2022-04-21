package com.example.medis.AdminMode;

import com.example.medis.Entities.User;
import com.example.medis.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class UserInfo {

    private User loggedInUser;
    private User selectedUser;


    public void initData(User user, User user2) {
        loggedInUser = user;
        selectedUser = user2;
    }

    @FXML
    public void switchToUsers(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToUsers(loggedInUser, event);
    }

    public void switchToUserInfoEdit(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToUserInfoEdit(loggedInUser, selectedUser, event);

    }
}
