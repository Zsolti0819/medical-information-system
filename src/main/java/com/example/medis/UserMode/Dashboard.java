package com.example.medis.UserMode;

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

public class Dashboard implements Initializable {

    private User loggedInUser;
    private Patient selectedPatient;
    private final JavaPostgreSql javaPostgreSql = new JavaPostgreSql();

    @FXML private TableView<Patient> patientsTable;
    @FXML private TableColumn<Patient, String> name;
    @FXML private TableColumn<Patient, String> lastName;
    @FXML private TableColumn<Patient, Long> birthId;
    @FXML private TableColumn<Patient, LocalDateTime> nextVisit;
    @FXML private TextField searchPatientField;

    // Open buttons
    @FXML
    private void switchToPatientInfo(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToPatientInfo(loggedInUser, selectedPatient, event);
    }

    // Log out
    @FXML
    private void userLogOut(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToLogout(null, event);
    }

    // Plus button
    @FXML
    private void switchToPatientCreation(MouseEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToPatientCreation(loggedInUser, event);
    }

    private void addButtonToTable() {
        TableColumn<Patient, Void> details  = new TableColumn<>();

        Callback<TableColumn<Patient, Void>, TableCell<Patient, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Patient, Void> call(final TableColumn<Patient, Void> param) {
                return new TableCell<>() {

                    private final Button openButton = new Button("Open");
                    {
                        openButton.setOnAction((ActionEvent event) -> {

                            selectedPatient = javaPostgreSql.getPatient(patientsTable.getItems().get(getIndex()).getId());

                            try {
                                switchToPatientInfo(event);
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

        patientsTable.getColumns().add(details);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        name.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        birthId.setCellValueFactory(new PropertyValueFactory<>("birthId"));
        nextVisit.setCellValueFactory(new PropertyValueFactory<>("nextVisit"));
        addButtonToTable();
        patientsTable.setItems(javaPostgreSql.getAllNotDeletedPatients());

        searchPatientField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() >= 3) {
                System.out.println();
                patientsTable.setItems(javaPostgreSql.filterPatients(newValue.replaceAll("[^a-zA-Z\\d]", "").toLowerCase()));
                addButtonToTable();
            }
            if (newValue.equals("")) {
                addButtonToTable();
                patientsTable.setItems(javaPostgreSql.getAllNotDeletedPatients());
            }
        });


    }

    public void initData(User user) {
        loggedInUser = user;
    }
}