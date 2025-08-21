package com.project.back_end.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Document(collection = "prescriptions")
public class Prescription {

    @Id
    private String prescription_id;

    @NotNull(message = "Patient name cannot be null")
    @Size(min = 3, max = 100)
    private String patient_name;

    @NotNull(message = "Appointment ID cannot be null")
    private Long appointment_id;

    @NotNull(message = "Medication cannot be null")
    @Size(min = 3, max = 100)
    private String medication;

    @NotNull(message = "Dosage cannot be null")
    @Size(min = 3, max = 20)
    private String dosage;

    @Size(max = 200)
    private String doctor_notes;

    public Prescription() {
    }

    public Prescription(String patient_name, Long appointment_id, String medication, String dosage, String doctor_notes) {
        this.patient_name = patient_name;
        this.appointment_id = appointment_id;
        this.medication = medication;
        this.dosage = dosage;
        this.doctor_notes = doctor_notes;
    }

    public String getId() {
        return prescription_id;
    }

    public void setId(String prescription_id) {
        this.prescription_id = prescription_id;
    }

    public String getPatientName() {
        return patient_name;
    }

    public void setPatientName(String patient_name) {
        this.patient_name = patient_name;
    }

    public Long getAppointmentId() {
        return appointment_id;
    }

    public void setAppointmentId(Long appointment_id) {
        this.appointment_id = appointment_id;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDoctorNotes() {
        return doctor_notes;
    }

    public void setDoctorNotes(String doctor_notes) {
        this.doctor_notes = doctor_notes;
    }
}
