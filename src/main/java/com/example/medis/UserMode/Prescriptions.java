package com.example.medis.UserMode;

import com.example.medis.Entities.Patient;
import com.example.medis.Entities.Prescription;
import com.example.medis.Entities.User;
import com.example.medis.JavaPostgreSql;
import com.example.medis.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class Prescriptions implements Initializable {


    private Patient selectedPatient;
    private Prescription selectedPrescription;
    private User loggedInUser;

    @FXML private TableView<Prescription> prescriptionsTable;
    @FXML private Label patient_name_prescriptions;
    @FXML private TableColumn <Prescription, String> title;
    @FXML private TableColumn <Prescription, String>drug;
    @FXML private TableColumn <Prescription, Date> expiration_date;
    @FXML private TableColumn <Prescription, String> prescribed_by;

    @FXML
    public void switchToPatientInfo(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToPatientInfo(loggedInUser, selectedPatient, event);
    }

    @FXML
    private void switchToRecords(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToRecords(loggedInUser, selectedPatient, event);
    }

    @FXML
    private void switchToAppointments(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToAppointments(loggedInUser, selectedPatient, event);
    }

    @FXML
    public void switchToPrescriptions(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToPrescriptions(loggedInUser, selectedPatient, event);
    }

    public void switchToPrescriptionEdit(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToPrescriptionEdit(loggedInUser, selectedPatient, selectedPrescription, event);
    }

    public void switchToPrescriptionCreation(MouseEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToPrescriptionCreation(loggedInUser, selectedPatient, event);
    }

    @FXML
    public void closeCurrentWindow(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToDashboard(loggedInUser, event);
    }


    private void addButtonToTable() {
        TableColumn<Prescription, Void> details  = new TableColumn<>();

        Callback<TableColumn<Prescription, Void>, TableCell<Prescription, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Prescription, Void> call(final TableColumn<Prescription, Void> param) {
                return new TableCell<>() {

                    private final Button openButton = new Button("View");

                    {
                        openButton.setOnAction((ActionEvent event) -> {
                            selectedPrescription  = JavaPostgreSql.getPrescriptionById(prescriptionsTable.getItems().get(getIndex()).getId());
                            try {
                                switchToPrescriptionEdit(event);
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

        prescriptionsTable.getColumns().add(details);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        drug.setCellValueFactory(new PropertyValueFactory<>("drug"));
        expiration_date.setCellValueFactory(new PropertyValueFactory<>("expiration_date"));
        prescribed_by.setCellValueFactory(new PropertyValueFactory<>("doctor_id"));
        addButtonToTable();
    }

    public void initData(Patient patient, User user) {
        selectedPatient = patient;
        loggedInUser = user;
        patient_name_prescriptions.setText(selectedPatient.getFirst_name() + " " + selectedPatient.getLast_name() + "'s prescriptions");
        prescriptionsTable.setItems(JavaPostgreSql.getAllNotDeletedPrescriptionsByEntityID("patient", selectedPatient.getId()));
    }
}
