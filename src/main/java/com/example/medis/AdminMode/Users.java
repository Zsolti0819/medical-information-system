package com.example.medis.AdminMode;

import com.example.medis.Entities.Patient;
import com.example.medis.Entities.User;
import com.example.medis.JavaPostgreSql;
import com.example.medis.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class Users implements Initializable {

    private User loggedInUser;
    private User selectedUser;
    private final JavaPostgreSql javaPostgreSql = new JavaPostgreSql();

    @FXML private TableView<User> usersTable;
    @FXML private TableColumn<User, String> first_name;
    @FXML private TableColumn<User, String> last_name;
    @FXML private TableColumn<User, String> position;
    @FXML private TableColumn<User, Boolean> deleted;
    @FXML private TableColumn<User, Long> id;
    @FXML private TextField searchUserfield;


    public void addUser(MouseEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToUserCreation(loggedInUser, event);
    }

    // Log out
    @FXML
    private void userLogOut(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToLogout(null, event);
    }



    @FXML
    private void switchToUserInfo(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToUserInfo(loggedInUser, selectedUser, event);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        first_name.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        last_name.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        addButtonToTable();
        usersTable.setItems(javaPostgreSql.getAllUsers());

        /*searchUserfield.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() >= 3) {
                System.out.println();
               // usersTable.setItems(javaPostgreSql.filterPatients(newValue.replaceAll("[^a-zA-Z\\d]", "").toLowerCase()));
                addButtonToTable();
            }
            if (newValue.equals("")) {
                addButtonToTable();
                usersTable.setItems(javaPostgreSql.getAllNotDeletedPatients());
            }
        });*/



    }

    private void addButtonToTable() {

        TableColumn<User, Void> details  = new TableColumn<>();

        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                return new TableCell<>() {

                    private final Button openButton = new Button("Open");
                    {
                        openButton.setOnAction((ActionEvent event) -> {

                            selectedUser = javaPostgreSql.getUser(usersTable.getItems().get(getIndex()).getId());

                            try {
                                switchToUserInfo(event);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(openButton);
                        }
                    }
                };
            }
        };

        details.setCellFactory(cellFactory);

        usersTable.getColumns().add(details);


    }

    public void initData(User user) {
        loggedInUser = user;
    }
}
