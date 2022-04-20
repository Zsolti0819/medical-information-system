package com.example.medis.AdminMode;

import com.example.medis.Entities.User;
import com.example.medis.SceneController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Users implements Initializable {

    private User loggedInUser;



    public void addUser(MouseEvent event) {
    }

    // Log out
    @FXML
    private void userLogOut(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToLogout(null, event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void initData(User user) {
        loggedInUser = user;
    }
}
