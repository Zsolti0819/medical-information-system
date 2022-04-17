package com.example.medis.UserMode;

import com.example.medis.Entities.Appointment;
import com.example.medis.Entities.Patient;
import com.example.medis.Entities.Record;
import com.example.medis.JavaPostgreSql;
import com.example.medis.SceneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class PatientRecords implements Initializable {

    private Patient selectedPatient;

    @FXML private TableView<Record> recordsTable;
    @FXML private TableColumn<Record, String> titleCol;
    @FXML private TableColumn<Record, String> descriptionCol;
    @FXML private TableColumn<Record, LocalDateTime> createdAtCol;

    private void addButtonToTable() {
        TableColumn<Record, Void> details  = new TableColumn<>();
        details.setText("Details");

        Callback<TableColumn<Record, Void>, TableCell<Record, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Record, Void> call(final TableColumn<Record, Void> param) {
                return new TableCell<>() {

                    private final Button openButton = new Button("Open");

                    {
                        openButton.setOnAction((ActionEvent event) -> {
                            Record selectedRecord = recordsTable.getItems().get(getIndex());
                            System.out.println("selectedAppointment ID: " + selectedRecord.getId());
//                            setSelectedPatient(JavaPostgreSql.getPatient(selectedAppointment.getId()));
//                            showPatientInfo();
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

        recordsTable.getColumns().add(details);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        createdAtCol.setCellValueFactory(new PropertyValueFactory<>("created_at"));
        addButtonToTable();

    }


    @FXML
    public void switchToPatientInfo(ActionEvent event) throws IOException  {
        SceneController s = new SceneController();
        s.switchToPatientInfo(selectedPatient, event);
    }

    @FXML
    private void switchToAppointments(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToAppointments(selectedPatient, event);
    }


    @FXML
    public void closeCurrentWindow(ActionEvent e) {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void showRecord()  {
        SceneController s = new SceneController();
        try {
            s.newWindow("user_mode/patient_record_detailed.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void initData(Patient patient) {
        selectedPatient = patient;
        recordsTable.setItems(JavaPostgreSql.getAllRecordsByPatientId(selectedPatient.getId()));
    }
}
