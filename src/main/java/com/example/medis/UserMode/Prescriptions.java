package com.example.medis.UserMode;

import com.example.medis.Entities.Patient;
import com.example.medis.Entities.Prescription;
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

    @FXML private TableView<Prescription> prescriptionsTable;
    @FXML private Label patient_name_prescriptions;
    @FXML private TableColumn <Prescription, String> title;
    @FXML private TableColumn <Prescription, String>drug;
    @FXML private TableColumn <Prescription, Date> expiration_date;
    @FXML private TableColumn <Prescription, Long> prescribed_by;

    @FXML
    public void switchToPatientInfo(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToPatientInfo(selectedPatient, event);
    }

    @FXML
    private void switchToRecords(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToRecords(selectedPatient, event);
    }

    @FXML
    private void switchToAppointments(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToAppointments(selectedPatient, event);
    }

    @FXML
    public void switchToPrescriptions(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToPrescriptions(selectedPatient, event);
    }

    public void switchToPrescriptionEdit(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToPrescriptionEdit(selectedPatient, selectedPrescription, event);
    }

    public void switchToPrescriptionCreation(MouseEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToPrescriptionCreation(selectedPatient, event);
    }

    @FXML
    public void closeCurrentWindow(ActionEvent e) {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }


    private void addButtonToTable() {
        TableColumn<Prescription, Void> details  = new TableColumn<>();
        details.setText("Details");

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
        drug.setCellValueFactory(new PropertyValueFactory<>("description"));
        expiration_date.setCellValueFactory(new PropertyValueFactory<>("created_at"));
        prescribed_by.setCellValueFactory(new PropertyValueFactory<>("created_at"));
        addButtonToTable();
    }

    public void initData(Patient patient) {
        selectedPatient = patient;
        patient_name_prescriptions.setText(selectedPatient.getFirst_name() + " " + selectedPatient.getLast_name() + "'s prescriptions");
        prescriptionsTable.setItems(JavaPostgreSql.getAllNotDeletedPrescriptionsByPatientId("patient", selectedPatient.getId()));
    }
}
