package com.example.medis.Entities;

import javafx.scene.control.Button;

import java.sql.Date;
import java.time.LocalDateTime;

public class Patient {
    private long id;
    private String first_name, surname;
    private String phone;
    private Date birth_date;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private boolean deleted;
    private long birth_number;
    private LocalDateTime last_visit, next_visit;
    private Button patient_info;

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

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
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

    public long getBirth_number() {
        return birth_number;
    }

    public void setBirth_number(long birth_number) {
        this.birth_number = birth_number;
    }

    public LocalDateTime getLast_visit() {
        return last_visit;
    }

    public void setLast_visit(LocalDateTime last_visit) {
        this.last_visit = last_visit;
    }

    public LocalDateTime getNext_visit() {
        return next_visit;
    }

    public void setNext_visit(LocalDateTime next_visit) {
        this.next_visit = next_visit;
    }

    public Button getPatient_info() {
        return patient_info;
    }

    public void setPatient_info(Button patient_info) {
        this.patient_info = patient_info;
    }
}
