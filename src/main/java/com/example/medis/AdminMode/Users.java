package com.example.medis.AdminMode;

import com.example.medis.Entities.User;
import com.example.medis.JavaPostgreSql;
import com.example.medis.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Users implements Initializable {

    @FXML private TableView<User> usersTable;
    @FXML private TableColumn<User, String> id;
    @FXML private TableColumn<User, String> first_name;
    @FXML private TableColumn<User, String> last_name;
    @FXML private TableColumn<User, String> position;
    @FXML private TableColumn<User, String> deleted;
    @FXML private TextField searchPatientField;
    @FXML private Pagination pagination;

    private final JavaPostgreSql javaPostgreSql = new JavaPostgreSql();
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
        int usersCount = javaPostgreSql.getUsersCount();
    }

    public void initData(User user) {
        loggedInUser = user;
    }
}
