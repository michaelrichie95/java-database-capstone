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
    private Long id;

    @ManyToOne
    @NotNull(message = "Doctor cannot be null")
    private Doctor doctor;

    @ManyToOne
    @NotNull(message = "Patient cannot be null")
    private Patient patient;

    @NotNull(message = "Appointment time cannot be null")
    @Future(message = "Appointment time must be in the future")
    private LocalDateTime time;

    @NotNull(message = "Appointment status cannot be null")
    private int status; // 0 = scheduled, 1 = completed

    public Appointment() {
    }

    public Appointment(Doctor doctor, Patient patient, LocalDateTime time, int status) {
        this.doctor = doctor;
        this.patient = patient;
        this.time = time;
        this.status = status;
    }

    @Transient
    public LocalDateTime getEndTime() {
        return time.plusHours(1);
    }

    @Transient
    public LocalDate getAppointmentDate() {
        return time.toLocalDate();
    }

    @Transient
    public LocalTime getAppointmentTimeOnly() {
        return time.toLocalTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDateTime getAppointmentTime() {
        return time;
    }

    public void setAppointmentTime(LocalDateTime time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
