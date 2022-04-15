package com.example.medis.UserMode;

import com.example.medis.Entities.Patient;
import com.example.medis.JavaPostgreSql;
import com.example.medis.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AfterLogin implements Initializable {

    @FXML private TableView<Patient> patientsTable;
    @FXML private TableColumn<Patient, String> name;
    @FXML private TableColumn<Patient, String> surname;
    @FXML private TableColumn<Patient, String> birth_number;
    @FXML private TableColumn<Patient, LocalDateTime> last_visit;
    @FXML private TableColumn<Patient, LocalDateTime> next_visit;
    @FXML private TableColumn<Patient, Button> patient_info;


    @FXML
    private void userLogOut(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchTo("login.fxml",event);

    }

    @FXML
    private void showPatients(ActionEvent event) throws IOException {
        SceneController s = new SceneController();
        s.switchTo("user_mode/after_login.fxml",event);
    }

    @FXML
    private void addPatient()  {
        SceneController s = new SceneController();
        try {
            s.popUpNewPatient("user_mode/new_patient.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public static void showPatient()  {
        SceneController s = new SceneController();
        try {
            s.popUpNewPatient("user_mode/patient_info.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        name.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        birth_number.setCellValueFactory(new PropertyValueFactory<>("birth_number"));
        last_visit.setCellValueFactory(new PropertyValueFactory<>("last_visit"));
        next_visit.setCellValueFactory(new PropertyValueFactory<>("next_visit"));
        patient_info.setCellValueFactory(new PropertyValueFactory<>("patient_info"));
        patientsTable.setItems(JavaPostgreSql.getAllPatients());
    }

}