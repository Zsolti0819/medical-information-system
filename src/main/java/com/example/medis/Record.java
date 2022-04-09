package com.example.medis;

import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;

public class Record {
    private SimpleStringProperty title, description;
    private LocalDateTime createdAt;


    public Record(String title, String description, LocalDateTime createdAt) {
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
