package com.example.medis.UserMode;

import com.example.medis.Entities.Patient;
import com.example.medis.JavaPostgreSql;
import com.example.medis.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PatientInfo implements Initializable {

    private Patient selectedPatient;

    @FXML private Label name_and_last_name_data;
    @FXML private Label insurance_co_data;
    @FXML private Label birth_ID_data;
    @FXML private Label birth_date_data;
    @FXML private Label sex_data;
    @FXML private Label blood_group_data;
    @FXML private Label address1_data;
    @FXML private Label phone_data;
    @FXML private Label email_data;

    @FXML
    private void switchToPatientInfoEdit(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToPatientInfoEdit(selectedPatient, event);
    }

    @FXML
    private void switchToRecords(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToRecords(selectedPatient, event);
    }

   @FXML
   private void switchToAppointments(ActionEvent event) throws  IOException {
       SceneController s = new SceneController();
       s.switchToAppointments(selectedPatient, event);

   }


    @FXML
    public void switchToPrescriptions(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToPrescriptions(selectedPatient, event);
    }

    @FXML
    public void closeCurrentWindow(ActionEvent event) {
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void initData(Patient patient) {
        selectedPatient = JavaPostgreSql.getPatient(patient.getId());
        name_and_last_name_data.setText(selectedPatient.getFirst_name() + " " + selectedPatient.getLast_name());
        insurance_co_data.setText(selectedPatient.getInsurance_company());
        birth_ID_data.setText(String.valueOf(selectedPatient.getBirth_id()));
        birth_date_data.setText(String.valueOf(selectedPatient.getBirth_date()));
        sex_data.setText(selectedPatient.getSex());
        blood_group_data.setText(selectedPatient.getBlood_group());
        address1_data.setText(selectedPatient.getAddress());
        phone_data.setText(selectedPatient.getPhone());
        email_data.setText(selectedPatient.getEmail());

    }

    public void deletePatient(ActionEvent event) {
        JavaPostgreSql.deletePatient(selectedPatient.getId());
        closeCurrentWindow(event);
    }


}
