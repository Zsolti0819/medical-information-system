package com.example.medis.Entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

import java.time.LocalDateTime;

public class Record {
    private String title, description;
    private LocalDateTime createdAt;
    private Button recordInfo;

    public Record(String title, String description, LocalDateTime createdAt, Button recordInfo) {
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.recordInfo = recordInfo;
        this.recordInfo.setText("Open");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Button getRecordInfo() {
        return recordInfo;
    }

    public void setRecordInfo(Button recordInfo) {
        this.recordInfo = recordInfo;
    }
}
