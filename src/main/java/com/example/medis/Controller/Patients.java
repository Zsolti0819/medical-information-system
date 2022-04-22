package com.example.medis.Controller;

import com.example.medis.Entity.Patient;
import com.example.medis.Entity.User;
import com.example.medis.Model.JavaPostgreSql;
import com.example.medis.ControllerBuffer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Patients implements Initializable {

    private User loggedInUser;
    private Patient selectedPatient;
    private final JavaPostgreSql javaPostgreSql = new JavaPostgreSql();

    @FXML private TableView<Patient> patientsTable;
    @FXML private TableColumn<Patient, String> name;
    @FXML private TableColumn<Patient, String> lastName;
    @FXML private TableColumn<Patient, Long> birthId;
    @FXML private TableColumn<Patient, String> nextVisit;
    @FXML private TextField searchPatientField;

    // Open buttons
    @FXML
    private void switchToPatientInfo(ActionEvent event) throws IOException {
        ControllerBuffer s = new ControllerBuffer();
        s.switchToPatientInfo(loggedInUser, selectedPatient, event);
    }

    // Log out
    @FXML
    private void userLogOut(ActionEvent event) throws IOException {
        ControllerBuffer s = new ControllerBuffer();
        s.switchToLogout(null, event);
    }

    // Plus button
    @FXML
    private void switchToPatientCreation(MouseEvent event) throws IOException {
        ControllerBuffer s = new ControllerBuffer();
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
        nextVisit.setCellValueFactory(new PropertyValueFactory<>("formattedNextVisit"));
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