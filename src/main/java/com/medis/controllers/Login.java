package com.medis.controllers;
import com.medis.Main;
import com.medis.models.User;
import com.medis.models.JavaPostgreSql;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class Login implements Initializable {

    private User loggedInUser;
    private final JavaPostgreSql javaPostgreSql = new JavaPostgreSql();

    @FXML private Label loginMsg;
    @FXML private TextField loginEmail;
    @FXML private PasswordField loginPassword;
    @FXML private ComboBox<String> localeComboBox;
    // Login button
    @FXML
    private void userLogIn(ActionEvent event) throws IOException {
        checkLogin(event);
    }

    private void checkLogin(ActionEvent event) throws IOException {

        Locale locale = new Locale(localeComboBox.getValue());
        Main.setLocale(locale);

        if (!loginEmail.getText().equals("") && !loginPassword.getText().equals("")) {
            if (javaPostgreSql.checkUser(loginEmail.getText(), loginPassword.getText())) {
                loggedInUser = javaPostgreSql.getUserByEmail(loginEmail.getText());
                if (loggedInUser.getPosition().equals("admin")) {
                    Main.switchToUsers(loggedInUser, event);
                }
                else {
                    Main.switchToPatients(loggedInUser, event);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        localeComboBox.getItems().addAll("EN", "SK");
        localeComboBox.setValue("EN");
    }

    public void initData(User user) {
        loggedInUser = user;
    }
}