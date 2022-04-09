package com.example.medis;

import com.example.medis.Record;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

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
        titleCol.setCellValueFactory(new PropertyValueFactory<Record, String>("Title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Record, String>("Description"));
        createdAtCol.setCellValueFactory(new PropertyValueFactory<Record, LocalDateTime>("createdAt"));

        recordsTable.setItems(getRecords());
    }

    private ObservableList<Record> getRecords() {
        ObservableList<Record> records = FXCollections.observableArrayList();
        records.add(new Record("Title", "Description", LocalDateTime.now()));

        return records;
    }


}
