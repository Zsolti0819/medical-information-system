package com.example.medis.Entities;

import javafx.scene.control.Button;

import java.time.LocalDateTime;

public class Appointment {
    private String title;
    private LocalDateTime date, updatedAt;
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
}
