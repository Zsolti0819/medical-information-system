package com.example.medis.Entity;

import java.util.ArrayList;

public class PatientData {
    private Patient patientData;
    private ArrayList<Appointment> appointments;
    private ArrayList<Prescription> prescriptions;
    private  ArrayList<Record> records;

    public PatientData(Patient patientData, ArrayList<Appointment> appointments, ArrayList<Prescription> prescriptions, ArrayList<Record> records) {
        this.patientData = patientData;
        this.appointments = appointments;
        this.prescriptions = prescriptions;
        this.records = records;
    }

    public PatientData(Patient patientData) {
        this.patientData = patientData;
    }

    public void setPatientData(Patient patientData) {
        this.patientData = patientData;
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }

    public void setPrescriptions(ArrayList<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public void setRecords(ArrayList<Record> records) {
        this.records = records;
    }
}
