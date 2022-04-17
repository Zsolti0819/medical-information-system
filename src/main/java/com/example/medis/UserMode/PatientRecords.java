package com.example.medis.UserMode;

import com.example.medis.Entities.Patient;
import com.example.medis.Entities.Record;
import com.example.medis.SceneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
    @FXML private TableColumn<Record, Button> recordInfo;
    Button [] button = new Button[3];


    private void handleButtonAction (ActionEvent event)  {
        if (event.getSource() == button[0]) {
            showRecord();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        createdAtCol.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        recordInfo.setCellValueFactory(new PropertyValueFactory<>("recordInfo"));

        for (int i = 0; i < button.length; i++) {
            button[i] = new Button();
            button[i].setOnAction(this::handleButtonAction);
        }

        recordsTable.setItems(getRecords());
    }

    private ObservableList<Record> getRecords() {
        ObservableList<Record> records = FXCollections.observableArrayList();
        records.add(new Record("Title", "Description", LocalDateTime.now(), button[0]));

        return records;
    }


    @FXML
    public void switchToPatientInfo(ActionEvent event) throws IOException  {
        SceneController s = new SceneController();
        try {
            s.switchToPatientInfo(selectedPatient, event);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToAppointments(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        try {
            s.switchToAppointments(selectedPatient, event);

        } catch (IOException e) {
            e.printStackTrace();
        }
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
    }
}
