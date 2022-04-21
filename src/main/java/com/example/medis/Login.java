package com.example.medis;
import com.example.medis.Entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.io.IOException;

public class Login {

    private User loggedInUser;
    private final JavaPostgreSql javaPostgreSql = new JavaPostgreSql();

    @FXML private Label loginMsg;
    @FXML private TextField loginEmail;
    @FXML private PasswordField loginPassword;

    // Login button
    @FXML
    private void userLogIn(ActionEvent event) throws IOException {
        checkLogin(event);
    }

    private void checkLogin(ActionEvent event) throws IOException {
        SceneController s = new SceneController();

        if (!loginEmail.getText().equals("") && !loginPassword.getText().equals("")) {
            if (javaPostgreSql.checkUser(loginEmail.getText(), loginPassword.getText())) {
                loggedInUser = javaPostgreSql.getUserByEmail(loginEmail.getText());
                if (loggedInUser.getPosition().equals("admin")) {
                    s.switchToUsers(loggedInUser, event);
                }
                else {
                    s.switchToDashboard(loggedInUser, event);
                }
            }


            else
                loginMsg.setText("Username or password are not valid!");
        }

        else if(loginEmail.getText().isEmpty() && loginPassword.getText().isEmpty()) {
            loginMsg.setText("Please enter your email and password.");
        }
        else {
            loginMsg.setText("Username or password are not valid!");
        }
    }

    public void initData(User user) {
        loggedInUser = user;
    }
}