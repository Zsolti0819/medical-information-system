package com.example.medis.Entities;

import javafx.scene.control.Button;

import java.time.LocalDateTime;

public class Patient {
    private long id;
    private String first_name, surname;
    private String phone;
    private LocalDateTime birthdate;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private boolean deleted;
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

    public Patient() {

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDateTime birthdate) {
        this.birthdate = birthdate;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
