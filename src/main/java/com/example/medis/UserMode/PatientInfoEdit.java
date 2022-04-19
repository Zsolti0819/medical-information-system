package com.example.medis.UserMode;

import com.example.medis.Entities.Patient;
import com.example.medis.JavaPostgreSql;
import com.example.medis.SceneController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PatientInfoEdit implements Initializable{

    private Patient selectedPatient;
    @FXML private Label name_and_surname_data;
    @FXML private TextField first_name_data;
    @FXML private TextField surname_data;
    @FXML private ComboBox<String> insurance_co_data;
    @FXML private TextField birth_ID_data;
    @FXML private ComboBox<String> sex_data;
    @FXML private ComboBox<String> blood_group_data;
    @FXML private TextField address_data;
    @FXML private TextField phone_data;
    @FXML private TextField email_data;

    @FXML
    public void switchToPatientInfo(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchToPatientInfo(selectedPatient, event);
    }

    @FXML
    public void updatePatientInfo(ActionEvent event) throws IOException {

        String birthIdText = birth_ID_data.getText();
        String birthDateFromId = Patient.getYear(birthIdText) + "-" + Patient.getMonth(birthIdText) + "-" + Patient.getDay(birthIdText);
        JavaPostgreSql.updatePatient(selectedPatient.getId(), first_name_data.getText(), surname_data.getText(), insurance_co_data.getSelectionModel().getSelectedItem(), birthDateFromId, sex_data.getSelectionModel().getSelectedItem(), blood_group_data.getSelectionModel().getSelectedItem(), address_data.getText(), phone_data.getText(), email_data.getText(), birth_ID_data.getText());
        switchToPatientInfo(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        insurance_co_data.setItems(FXCollections.observableArrayList("Union", "Dôvera", "VŠZP"));
        sex_data.setItems(FXCollections.observableArrayList("male","female"));
        blood_group_data.setItems(FXCollections.observableArrayList("A","A+","A-","B","B+","B-","AB","AB+","AB-", "0","0+","0-"));
    }

    public void initData(Patient patient) {
        selectedPatient = patient;
        name_and_surname_data.setText(selectedPatient.getFirst_name() + " " + selectedPatient.getLast_name());
        first_name_data.setText(selectedPatient.getFirst_name());
        surname_data.setText(selectedPatient.getLast_name());
        insurance_co_data.getSelectionModel().select(String.valueOf(selectedPatient.getInsurance_company()));
        birth_ID_data.setText(String.valueOf(selectedPatient.getBirth_id()));
        sex_data.getSelectionModel().select(String.valueOf(selectedPatient.getSex()));
        blood_group_data.getSelectionModel().select(String.valueOf(selectedPatient.getBlood_group()));
        address_data.setText(selectedPatient.getAddress());
        phone_data.setText(selectedPatient.getPhone());
        email_data.setText(selectedPatient.getEmail());
    }
}
