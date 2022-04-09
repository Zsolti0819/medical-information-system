package com.example.medis;

import javafx.scene.control.Button;

import java.time.LocalDateTime;

public class Patient {
    private String nameAndSurname, birthNumber;
    private LocalDateTime lastVisit, nextVisit;
    private Button patientInfo;

    public Patient(String nameAndSurname, String birthNumber, LocalDateTime lastVisit, LocalDateTime nextVisit) {
        this.nameAndSurname = nameAndSurname;
        this.birthNumber = birthNumber;
        this.lastVisit = lastVisit;
        this.nextVisit = nextVisit;
        this.patientInfo = new Button("Open");
    }

    public String getNameAndSurname() {
        return nameAndSurname;
    }

    public void setNameAndSurname(String nameAndSurname) {
        this.nameAndSurname = nameAndSurname;
    }

    public String getBirthNumber() {
        return birthNumber;
    }

    public void setBirthNumber(String birthNumber) {
        this.birthNumber = birthNumber;
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
