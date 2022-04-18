package com.example.medis.UserMode;

import com.example.medis.Entities.Patient;
import com.example.medis.Entities.Record;
import com.example.medis.JavaPostgreSql;
import com.example.medis.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class Records implements Initializable {

    private Patient selectedPatient;
    private Record selectedRecord;

    @FXML private TableView<Record> recordsTable;
    @FXML private Label patient_name_records;
    @FXML private TableColumn<Record, String> title;
    @FXML private TableColumn<Record, String> description;
    @FXML private TableColumn<Record, LocalDateTime> created_at;

    @FXML
    public void switchToPatientInfo(ActionEvent event) throws IOException  {
        SceneController s = new SceneController();
        s.switchToPatientInfo(selectedPatient, event);
    }

    @FXML
    private void switchToRecordDetailed(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToRecordDetailed(selectedPatient, selectedRecord, event);
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

    private void addButtonToTable() {
        TableColumn<Record, Void> details  = new TableColumn<>();
        details.setText("Details");

        Callback<TableColumn<Record, Void>, TableCell<Record, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Record, Void> call(final TableColumn<Record, Void> param) {
                return new TableCell<>() {

                    private final Button openButton = new Button("View");

                    {
                        openButton.setOnAction((ActionEvent event) -> {
                            selectedRecord  = JavaPostgreSql.getRecord(recordsTable.getItems().get(getIndex()).getId());
                            try {
                                switchToRecordDetailed(event);
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

        recordsTable.getColumns().add(details);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        created_at.setCellValueFactory(new PropertyValueFactory<>("created_at"));
        addButtonToTable();
    }

    public void initData(Patient patient) {
        selectedPatient = patient;
        patient_name_records.setText(selectedPatient.getFirst_name() + " " + selectedPatient.getSurname() + "'s records");
        recordsTable.setItems(JavaPostgreSql.getAllRecordsByPatientId(selectedPatient.getId()));
    }
}
