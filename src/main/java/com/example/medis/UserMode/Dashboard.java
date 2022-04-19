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

    public TableView<Patient> getPatientsTable() {
        return patientsTable;
    }

    @FXML private TableView<Patient> patientsTable;
    @FXML private TableColumn<Patient, String> name;
    @FXML private TableColumn<Patient, String> last_name;
    @FXML private TableColumn<Patient, Long> birth_id;
    @FXML private TableColumn<Patient, LocalDateTime> last_visit;
    @FXML private TableColumn<Patient, LocalDateTime> next_visit;
    @FXML private TextField searchPatientField;

    private User loggedInUser;
    private Patient selectedPatient;

    // Patients
    @FXML
    private void showPatients(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchTo("user_mode/dashboard.fxml",event);
    }

    // Open buttons
    @FXML
    public void newWindowWithPatient() throws IOException {
        SceneController s = new SceneController();
        s.newWindowWithPatient(selectedPatient);
    }

    // Log out
    @FXML
    private void userLogOut(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchTo("login.fxml",event);

    }

    // Plus button
    @FXML
    private void switchToPatientCreation(MouseEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToPatientCreation(event);
    }

    private void addButtonToTable() {
        TableColumn<Patient, Void> details  = new TableColumn<>();
        details.setText("Details");

        Callback<TableColumn<Patient, Void>, TableCell<Patient, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Patient, Void> call(final TableColumn<Patient, Void> param) {
                return new TableCell<>() {

                    private final Button openButton = new Button("Open");
                    {
                        openButton.setOnAction((ActionEvent event) -> {

                            selectedPatient = JavaPostgreSql.getPatient(getPatientsTable().getItems().get(getIndex()).getId());

                            try {
                                newWindowWithPatient();
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
        name.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        last_name.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        birth_id.setCellValueFactory(new PropertyValueFactory<>("birth_id"));
        last_visit.setCellValueFactory(new PropertyValueFactory<>("last_visit"));
        next_visit.setCellValueFactory(new PropertyValueFactory<>("next_visit"));
        addButtonToTable();
        patientsTable.setItems(JavaPostgreSql.getAllNotDeletedPatients());

        searchPatientField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() >= 3) {
                System.out.println();
                patientsTable.setItems(JavaPostgreSql.filterPatients(newValue.replaceAll("[^a-zA-Z0-9]", "").toLowerCase()));
                addButtonToTable();
            }
            if (newValue.equals("")) {
                addButtonToTable();
                patientsTable.setItems(JavaPostgreSql.getAllNotDeletedPatients());
            }
        });


    }

    public void initData(User user) {
        loggedInUser = user;
    }
}