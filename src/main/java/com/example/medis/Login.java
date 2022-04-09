package com.example.medis;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class Login {

    @FXML
    private Label wrongLoginMsg;
    @FXML
    private TextField loginEmail;
    @FXML
    private PasswordField loginPassword;

    public void userLogIn(ActionEvent event) throws IOException {
        checkLogin(event);

    }

    private void checkLogin(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        if (loginEmail.getText().equals("") && loginPassword.getText().equals("")) {
            wrongLoginMsg.setText("Success!");

            s.switchTo("user_mode.fxml",event);

        }

        else if(loginEmail.getText().isEmpty() && loginPassword.getText().isEmpty()) {
            wrongLoginMsg.setText("Please enter your email and password.");
        }


        else {
            wrongLoginMsg.setText("Username or password are not valid!");
        }
    }


}