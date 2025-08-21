package com.project.back_end.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patient_id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 3, max = 100)
    private String patient_name;

    @NotNull(message = "Email cannot be null")
    @Email
    private String patient_email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 6)
    private String patient_password;

    @NotNull(message = "Phone number cannot be null")
    @Pattern(regexp = "\\d{10}")
    private String patient_phone;

    @NotNull(message = "Address cannot be null")
    @Size(max = 255)
    private String patient_address;

    public Patient() {
    }

    public Patient(String patient_name, String patient_email, String patient_password, String patient_phone, String patient_address) {
        this.patient_name = patient_name;
        this.patient_email = patient_email;
        this.patient_password = patient_password;
        this.patient_phone = patient_phone;
        this.patient_address = patient_address;
    }

    public Long getId() {
        return patient_id;
    }

    public void setId(Long patient_id) {
        this.patient_id = patient_id;
    }

    public String getName() {
        return patient_name;
    }

    public void setName(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getEmail() {
        return patient_email;
    }

    public void setEmail(String patient_email) {
        this.patient_email = patient_email;
    }

    public void setPassword(String patient_password) {
        this.patient_password = patient_password;
    }

    public String getPhone() {
        return patient_phone;
    }

    public void setPhone(String patient_phone) {
        this.patient_phone = patient_phone;
    }

    public String getAddress() {
        return patient_address;
    }

    public void setAddress(String patient_address) {
        this.patient_address = patient_address;
    }
}
