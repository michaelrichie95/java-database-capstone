package com.project.back_end.models;

import com.fasterxml.jackson.annotation.JsonProperty; 
import jakarta.persistence.Entity;                
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;          
import jakarta.persistence.GenerationType;         
import jakarta.persistence.Id;                      
import jakarta.validation.constraints.NotNull;     

@Entity
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long admin_id;

    @NotNull(message = "Username cannot be null")
    private String admin_username;

    @NotNull(message = "Password cannot be null")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String admin_password;

    public Admin() {
    }

    public Admin(String admin_username, String admin_password) {
        this.admin_username = admin_username;
        this.admin_password = admin_password;
    }

    public Long getId() {
        return admin_id;
    }

    public void setId(Long admin_id) {
        this.admin_id = admin_id;
    }

    public String getUsername() {
        return admin_username;
    }

    public void setUsername(String admin_username) {
        this.admin_username = admin_username;
    }

    public void setPassword(String admin_password) {
        this.admin_password = admin_password;
    }
}
