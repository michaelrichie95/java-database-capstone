package com.project.back_end.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointment_id;

    @ManyToOne
    @NotNull(message = "Doctor cannot be null")
    private Doctor appointment_doctor;

    @ManyToOne
    @NotNull(message = "Patient cannot be null")
    private Patient appointment_patient;

    @NotNull(message = "Appointment time cannot be null")
    @Future(message = "Appointment time must be in the future")
    private LocalDateTime appointment_time;

    @NotNull(message = "Appointment status cannot be null")
    private int appointment_status; // 0 = scheduled, 1 = completed

    public Appointment() {
    }

    public Appointment(Doctor appointment_doctor, Patient appointment_patient, LocalDateTime appointment_time, int appointment_status) {
        this.appointment_doctor = appointment_doctor;
        this.appointment_patient = appointment_patient;
        this.appointment_time = appointment_time;
        this.appointment_status = appointment_status;
    }

    @Transient
    public LocalDateTime getEndTime() {
        return appointment_time.plusHours(1);
    }

    @Transient
    public LocalDate getAppointmentDate() {
        return appointment_time.toLocalDate();
    }

    @Transient
    public LocalTime getAppointmentTimeOnly() {
        return appointment_time.toLocalTime();
    }

    public Long getId() {
        return appointment_id;
    }

    public void setId(Long appointment_id) {
        this.appointment_id = appointment_id;
    }

    public Doctor getDoctor() {
        return appointment_doctor;
    }

    public void setDoctor(Doctor appointment_doctor) {
        this.appointment_doctor = appointment_doctor;
    }

    public Patient getPatient() {
        return appointment_patient;
    }

    public void setPatient(Patient appointment_patient) {
        this.appointment_patient = appointment_patient;
    }

    public LocalDateTime getAppointmentTime() {
        return appointment_time;
    }

    public void setAppointmentTime(LocalDateTime appointment_time) {
        this.appointment_time = appointment_time;
    }

    public int getStatus() {
        return appointment_status;
    }

    public void setStatus(int appointment_status) {
        this.appointment_status = appointment_status;
    }
}
