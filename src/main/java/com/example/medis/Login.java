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
    @FXML private Label loginMsg;
    @FXML private TextField loginEmail;
    @FXML private PasswordField loginPassword;

    public void userLogIn(ActionEvent event) throws IOException, InterruptedException {
        checkLogin(event);
    }

    private void checkLogin(ActionEvent event) throws IOException, InterruptedException {
        SceneController s = new SceneController();
        // create user function to creating users in db.
        //JavaPostgreSql.createUser("Tamás Szakal", "xszakal12", "qwe123", "xszasadkal@stubs.sk", "0900000000", "doctor",  "2022-01-01");

        if (!loginEmail.getText().equals("") && !loginPassword.getText().equals("")) {
//            loginMsg.setText("Success!");
            if (JavaPostgreSql.checkUser(loginEmail.getText(), loginPassword.getText())) {
                loggedInUser = JavaPostgreSql.getUserByEmail(loginEmail.getText());
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
//        loginMsg.setText(JavaPostgreSql.updateUser( 2,"xszakal", "Tamás Szakal","12345", "xszakal@stubs.sk", "0900000000", "doctor",  "2022-01-01"));
//        loginMsg.setText(JavaPostgreSql.deleteUser(8));
    }


    public void initData(User user) {
        loggedInUser = user;
    }
}