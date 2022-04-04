package com.example.medis;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PatientDetailed {

    @FXML
    private Label nameLabel;

    @FXML
    private Label birthdayLabel;

    @FXML
    private Label birthdayNumberLabel;

    @FXML
    private Label bloodGroupLabel;

    @FXML
    private Label insuranceComLabel;

    @FXML
    private Label sexLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Button editButton;

    @FXML
    private Button deletePatientButton;


    /*private void editPatientInfo(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.popUpNewPatient("patientinfo.fxml", event);
    }*/

}
