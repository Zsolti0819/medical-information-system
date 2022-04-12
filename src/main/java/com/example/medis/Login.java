package com.example.medis;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.io.IOException;

public class Login {

    @FXML
    private Label loginMsg;
    @FXML
    private TextField loginEmail;
    @FXML
    private PasswordField loginPassword;

    public void userLogIn(ActionEvent event) throws IOException {
        checkLogin(event);

    }
//////////////////////
    private void checkLogin(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        if (loginEmail.getText().equals("") && loginPassword.getText().equals("")) {
            loginMsg.setText("Success!");

            s.switchTo("user_mode/after_login.fxml",event);

        }

        else if(loginEmail.getText().isEmpty() && loginPassword.getText().isEmpty()) {
            loginMsg.setText("Please enter your email and password.");
        }


        else {
            loginMsg.setText("Username or password are not valid!");
        }
    }


}