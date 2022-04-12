package com.example.medis;

import com.example.medis.Record;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class PatientRecords implements Initializable {

    @FXML private TableView<Record> recordsTable;
    @FXML private TableColumn<Record, String> titleCol;
    @FXML private TableColumn<Record, String> descriptionCol;
    @FXML private TableColumn<Record, LocalDateTime> createdAtCol;
    @FXML private TableColumn<Record, String> detailsCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        createdAtCol.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        recordsTable.setItems(getRecords());
    }

    private ObservableList<Record> getRecords() {
        ObservableList<Record> records = FXCollections.observableArrayList();
        records.add(new Record("Title", "Description", LocalDateTime.now()));

        return records;
    }

    @FXML
    private void switchToPatientInfo(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchTo("user_mode_patient_info.fxml",event);
    }

    @FXML
    private void switchToAppointments(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchTo("user_mode_patient_appointments.fxml",event);
    }

    @FXML
        public void closeNewPatientWindow(ActionEvent e) {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
