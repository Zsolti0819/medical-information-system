package com.example.medis.Entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;

public class Appointment {
    private long id;
    private String title;
    private String description;
    private LocalDateTime start_time;
    private LocalDateTime end_time;

    private String start_time_formatted;

    private String end_time_formatted;
    private LocalDateTime created_at, updated_at;
    private int start_hour, start_min, start_year, start_month, start_day,  end_hour, end_min, end_year, end_month, end_day;
    private long patient_id;
    private long doctor_id;
    private String doctor_name;
    private boolean deleted;
    private long created_by;

    public String getStart_time_formatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return start_time.format(formatter);
    }

    public String getEnd_time_formatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return end_time.format(formatter);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        this.start_year = start_time.getYear();
        this.start_month = start_time.getMonthValue();
        this.start_day = start_time.getDayOfMonth();
        this.start_hour = start_time.getHour();
        this.start_min = start_time.getMinute();
    }

    public LocalDateTime getEnd_time() {
        return end_time;
    }

    public void setEnd_time(LocalDateTime end_time) {
        this.end_time = end_time;
        this.end_year = end_time.getYear();
        this.end_month = end_time.getMonthValue();
        this.end_day = end_time.getDayOfMonth();
        this.end_hour = end_time.getHour();
        this.end_min = end_time.getMinute();
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

    public long getCreated_by() {
        return created_by;
    }

    public void setCreated_by(long created_by) {
        this.created_by = created_by;
    }

    public int getStart_hour() {
        return start_hour;
    }

    public int getStart_min() {
        return start_min;
    }

    public int getStart_year() {
        return start_year;
    }

    public int getStart_month() {
        return start_month;
    }

    public int getStart_day() {
        return start_day;
    }

    public int getEnd_hour() {
        return end_hour;
    }

    public int getEnd_min() {
        return end_min;
    }

    public int getEnd_year() {
        return end_year;
    }

    public int getEnd_month() {
        return end_month;
    }

    public int getEnd_day() {
        return end_day;
    }

    public Date getStartDate(){
        return new GregorianCalendar(start_year,start_month,start_day).getTime();
    }

    public Date getEndDate(){
        return new GregorianCalendar(end_year,end_month,end_day).getTime();
    }

    public void setStart_hour(int start_hour) {
        this.start_hour = start_hour;
    }

    public void setStart_min(int start_min) {
        this.start_min = start_min;
    }

    public void setStart_year(int start_year) {
        this.start_year = start_year;
    }

    public void setStart_month(int start_month) {
        this.start_month = start_month;
    }

    public void setStart_day(int start_day) {
        this.start_day = start_day;
    }

    public void setEnd_hour(int end_hour) {
        this.end_hour = end_hour;
    }

    public void setEnd_min(int end_min) {
        this.end_min = end_min;
    }

    public void setEnd_year(int end_year) {
        this.end_year = end_year;
    }

    public void setEnd_month(int end_month) {
        this.end_month = end_month;
    }

    public void setEnd_day(int end_day) {
        this.end_day = end_day;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }
}
