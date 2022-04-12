package com.example.medis.UserMode;


import com.example.medis.Entities.Appointment;
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

public class PatientAppointments implements Initializable {

    @FXML private TableView<Appointment> appointmentsTable;
    @FXML private TableColumn<Appointment, String> titleCol;
    @FXML private TableColumn<Appointment, LocalDateTime> dateCol;
    @FXML private TableColumn<Appointment, LocalDateTime> updatedAtCol;
    @FXML private TableColumn<Appointment, Button> appointmentInfo;
    Button [] button = new Button[3];


    private void handleButtonAction (ActionEvent event)  {
        if (event.getSource() == button[0]) {
            showAppointments();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));
        updatedAtCol.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));
        appointmentInfo.setCellValueFactory(new PropertyValueFactory<>("appointmentInfo"));

        for (int i = 0; i < button.length; i++) {
            button[i] = new Button();
            button[i].setOnAction(this::handleButtonAction);
        }

        appointmentsTable.setItems(getAppointments());
    }

    private ObservableList<Appointment> getAppointments() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        appointments.add(new Appointment("Title", LocalDateTime.now(), LocalDateTime.now(), button[0]));

        return appointments;
    }

    @FXML
    private void switchToRecords(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchTo("user_mode/patient_records.fxml",event);
    }

    @FXML
    private void switchToPatientsInfo(ActionEvent event) throws  IOException {
        SceneController s = new SceneController();
        s.switchTo("user_mode/patient_info.fxml",event);
    }

    @FXML
    public void closeCurrentWindow(ActionEvent e) {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void showAppointments()  {
        SceneController s = new SceneController();
        try {
            s.popUpNewPatient("user_mode/patient_appointment_edit.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
