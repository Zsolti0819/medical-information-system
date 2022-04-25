package com.medis.controllers;

import com.medis.Main;
import com.medis.models.Patient;
import com.medis.models.User;
import com.medis.models.JavaPostgreSql;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Patients implements Initializable {

    private User loggedInUser;
    private Patient selectedPatient;
    private final JavaPostgreSql javaPostgreSql = new JavaPostgreSql();
    private ObservableList<Patient> allPatients;
    @FXML private TableView<Patient> patientsTable;
    @FXML private TableColumn<Patient, String> name;
    @FXML private TableColumn<Patient, String> lastName;
    @FXML private TableColumn<Patient, Long> birthId;
    @FXML private TableColumn<Patient, String> nextVisit;
    @FXML private TextField searchPatientField;
    @FXML private Label searchLabel;

    public Patients() {
        allPatients =  javaPostgreSql.getAllNotDeletedPatients();
    }

    // Open buttons
    @FXML
    private void switchToPatientInfo(ActionEvent event) throws IOException {
        Main.switchToPatientInfo(loggedInUser, selectedPatient, event);
    }

    // Log out
    @FXML
    private void userLogOut(ActionEvent event) throws IOException {
        Main.switchToLogout(null, event);
    }

    // Plus button
    @FXML
    private void switchToPatientCreation(MouseEvent event) throws IOException {
        Main.switchToPatientCreation(loggedInUser, event);
    }

    private void matchSearch(String newValue, Pattern textCols, Pattern numCols) {
        ResourceBundle bundle = ResourceBundle.getBundle("medis", Main.getLocale());
        Matcher textMatcher = textCols.matcher(newValue);
        Matcher numberMatcher = numCols.matcher(newValue);
        if (newValue.equals("")) {
            searchLabel.setText("");
            if (patientsTable.getItems().size() == 0) {
                patientsTable.setItems(allPatients);
            }
        } else {
            if (textMatcher.find()) {
                System.out.println(textMatcher.group(1) + " " + textMatcher.group());
                if (textMatcher.group(1).toLowerCase().contains("name") || textMatcher.group(1).toLowerCase().equals("meno") || textMatcher.group(1).toLowerCase().equals("priezvisko")) {
                    patientsTable.setItems(javaPostgreSql.getAllNotDeletedPatientsFiltered("first_name", textMatcher.group(2).toLowerCase()));
                }

                if (textMatcher.group(1).toLowerCase().equals("last name") || textMatcher.group(1).toLowerCase().equals("priezvisko")) {
                    patientsTable.setItems(javaPostgreSql.getAllNotDeletedPatientsFiltered("first_name", textMatcher.group(2).toLowerCase()));
                }

                searchLabel.setTextFill(Color.color(0, 0.6, 0));
                searchLabel.setText(bundle.getString("patients.search.success"));
                return;
            }

            if (numberMatcher.find()) {
                System.out.println(numberMatcher.group(1) + " " + numberMatcher.group(2));
                System.out.println();

                if (numberMatcher.group(1).toLowerCase().contains("id")) {
                    patientsTable.setItems(javaPostgreSql.getAllNotDeletedPatientsFiltered(numberMatcher.group(2)));
                    searchLabel.setTextFill(Color.color(0, 0.6, 0));
                    searchLabel.setText(bundle.getString("patients.search.success"));

                }
                return;
            }

            searchLabel.setTextFill(Color.color(0.8, 0, 0));
            searchLabel.setText(bundle.getString("patients.search.failure"));
        }

    }

    private void addButtonToTable() {
        TableColumn<Patient, Void> details  = new TableColumn<>();

        Callback<TableColumn<Patient, Void>, TableCell<Patient, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Patient, Void> call(final TableColumn<Patient, Void> param) {
                return new TableCell<>() {

                    private final Button openButton = new Button("Open");
                    {
                        openButton.setOnAction((ActionEvent event) -> {

                            selectedPatient = javaPostgreSql.getPatientById(patientsTable.getItems().get(getIndex()).getId());

                            try {
                                switchToPatientInfo(event);
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

        patientsTable.getColumns().add(details);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        name.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        birthId.setCellValueFactory(new PropertyValueFactory<>("birthId"));
        nextVisit.setCellValueFactory(new PropertyValueFactory<>("formattedNextVisit"));
        addButtonToTable();
        patientsTable.setItems(javaPostgreSql.getAllNotDeletedPatients());

        Pattern textCols = Pattern.compile("^((?i)\\bfirst name\\b|(?i)\\blast name\\b|(?i)\\bmeno\\b|(?i)\\bpriezvisko\\b|(?i)\\bfirstname\\b|(?i)\\blastname\\b):([A-Za-z0-9 ]+)$");
        Pattern numCols = Pattern.compile("^((?i)\\bid\\b|(?i)\\bbirth id\\b|(?i)\\bbirthid\\b):([1-9][0-9]{0,18})$");

        searchPatientField.textProperty().addListener((observable, oldValue, newValue) -> {
            matchSearch(newValue, textCols, numCols);

        });

        searchPatientField.focusedProperty().addListener(new ChangeListener<Boolean>() {
                  @Override
                  public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                      if (!newPropertyValue && Objects.equals(searchPatientField.getText(), "")) {
                          patientsTable.setItems(allPatients);
                      }
                  }
              }
        );

    }

    public void initData(User user) {
        loggedInUser = user;
    }
}