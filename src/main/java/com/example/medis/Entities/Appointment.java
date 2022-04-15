package com.example.medis.Entities;

import javafx.scene.control.Button;

import java.time.LocalDateTime;

public class Appointment {
    private long id;
    private String title;
    private String description;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private LocalDateTime date, updatedAt;
    private long patient_id;
    private long doctor_id;
    private boolean deleted;
    private Button appointmentInfo;

    public Appointment(String title, LocalDateTime date, LocalDateTime updatedAt, Button appointmentInfo) {
        this.title = title;
        this.date = date;
        this.updatedAt = updatedAt;
        this.appointmentInfo = appointmentInfo;
        this.appointmentInfo.setText("Open");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Button getAppointmentInfo() {
        return appointmentInfo;
    }

    public void setAppointmentInfo(Button appointmentInfo) {
        this.appointmentInfo = appointmentInfo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStart_time() {
        return start_time;
    }

    public void setStart_time(LocalDateTime start_time) {
        this.start_time = start_time;
    }

    public LocalDateTime getEnd_time() {
        return end_time;
    }

    public void setEnd_time(LocalDateTime end_time) {
        this.end_time = end_time;
    }

    public long getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(long patient_id) {
        this.patient_id = patient_id;
    }

    public long getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(long doctor_id) {
        this.doctor_id = doctor_id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
