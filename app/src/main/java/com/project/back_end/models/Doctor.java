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
@Table(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctor_id;

    @NotNull(message = "Doctor doctor_name cannot be null")
    @Size(min = 3, max = 100)
    private String doctor_name;

    @NotNull(message = "Specialty cannot be null")
    @Size(min = 3, max = 50)
    private String doctor_specialty;

    @NotNull(message = "Email cannot be null")
    @Email
    private String doctor_email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 6)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String doctor_password;

    @NotNull(message = "Phone number cannot be null")
    @Pattern(regexp = "^[0-9]{10}$")
    private String doctor_phone;

    @ElementCollection
    private List<String> doctor_availableTimes;

    public Doctor() {
    }

    public Doctor(String doctor_name, String doctor_specialty, String doctor_email, String doctor_password, String doctor_phone, List<String> doctor_availableTimes) {
        this.doctor_name = doctor_name;
        this.doctor_specialty = doctor_specialty;
        this.doctor_email = doctor_email;
        this.doctor_password = doctor_password;
        this.doctor_phone = doctor_phone;
        this.doctor_availableTimes = doctor_availableTimes;
    }

    public Long getId() {
        return doctor_id;
    }

    public void setId(Long doctor_id) {
        this.doctor_id= doctor_id;
    }

    public String getName() {
        return doctor_name;
    }

    public void setName(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getSpecialty() {
        return doctor_specialty;
    }

    public void setSpecialty(String doctor_specialty) {
        this.doctor_specialty = doctor_specialty;
    }

    public String getEmail() {
        return doctor_email;
    }

    public void setEmail(String doctor_email) {
        this.doctor_email = doctor_email;
    }

    public void setPassword(String doctor_password) {
        this.doctor_password = doctor_password;
    }

    public String getPhone() {
        return doctor_phone;
    }

    public void setPhone(String doctor_phone) {
        this.doctor_phone = doctor_phone;
    }

    public List<String> getAvailableTimes() {
        return doctor_availableTimes;
    }

    public void setAvailableTimes(List<String> doctor_availableTimes) {
        this.doctor_availableTimes = doctor_availableTimes;
    }
}
