package com.example.medis.Entities;

import javafx.scene.control.Button;

import java.time.LocalDateTime;

public class Patient {
    private long id;
    private String first_name, surname;
    private String nameAndSurname, identificationNumber;
    private LocalDateTime lastVisit, nextVisit;
    private Button patientInfo;

    public Patient(String nameAndSurname, String identificationNumber, LocalDateTime lastVisit, LocalDateTime nextVisit, Button patientInfo) {
        this.nameAndSurname = nameAndSurname;
        this.identificationNumber = identificationNumber;
        this.lastVisit = lastVisit;
        this.nextVisit = nextVisit;
        this.patientInfo = patientInfo;
        this.patientInfo.setText("Open");
    }

    public String getNameAndSurname() {
        return nameAndSurname;
    }

    public void setNameAndSurname(String nameAndSurname) {
        this.nameAndSurname = nameAndSurname;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public LocalDateTime getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(LocalDateTime lastVisit) {
        this.lastVisit = lastVisit;
    }

    public LocalDateTime getNextVisit() {
        return nextVisit;
    }

    public void setNextVisit(LocalDateTime nextVisit) {
        this.nextVisit = nextVisit;
    }

    public Button getPatientInfo() {
        return patientInfo;
    }

    public void setPatientInfo(Button patientInfo) {
        this.patientInfo = patientInfo;
    }
}
