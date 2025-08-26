package com.project.back_end.controllers;

import com.project.back_end.model.Appointment;
import com.project.back_end.services.AppointmentService;
import com.project.back_end.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final Service service;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, Service service) {
        this.appointmentService = appointmentService;
        this.service = service;
    }

    // ---------------------- Get Appointments ----------------------
    @GetMapping("/{date}/{patientName}/{token}")
    public ResponseEntity<?> getAppointments(
            @PathVariable String date,
            @PathVariable String patientName,
            @PathVariable String token) {

        // Validate token for doctor
        ResponseEntity<Map<String, String>> tokenValidation = service.validateToken(token, "doctor");
        if (tokenValidation.getStatusCode() != HttpStatus.OK) {
            return tokenValidation;
        }

        // Fetch appointments
        List<Appointment> appointments = appointmentService.getAppointment(date, patientName);
        return ResponseEntity.ok(Map.of("appointments", appointments));
    }

    // ---------------------- Book Appointment ----------------------
    @PostMapping("/{token}")
    public ResponseEntity<Map<String, String>> bookAppointment(
            @RequestBody Appointment appointment,
            @PathVariable String token) {

        // Validate token for patient
        ResponseEntity<Map<String, String>> tokenValidation = service.validateToken(token, "patient");
        if (tokenValidation.getStatusCode() != HttpStatus.OK) {
            return tokenValidation;
        }

        // Validate appointment
        int validationResult = service.validateAppointment(appointment);
        if (validationResult == -1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Doctor not found"));
        } else if (validationResult == 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Appointment slot unavailable"));
        }

        // Book appointment
        boolean booked = appointmentService.bookAppointment(appointment);
        if (booked) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Appointment booked successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Failed to book appointment"));
        }
    }

    // ---------------------- Update Appointment ----------------------
    @PutMapping("/{token}")
    public ResponseEntity<Map<String, String>> updateAppointment(
            @RequestBody Appointment appointment,
            @PathVariable String token) {

        // Validate token for patient
        ResponseEntity<Map<String, String>> tokenValidation = service.validateToken(token, "patient");
        if (tokenValidation.getStatusCode() != HttpStatus.OK) {
            return tokenValidation;
        }

        boolean updated = appointmentService.updateAppointment(appointment);
        if (updated) {
            return ResponseEntity.ok(Map.of("message", "Appointment updated successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Failed to update appointment"));
        }
    }

    // ---------------------- Cancel Appointment ----------------------
    @DeleteMapping("/{id}/{token}")
    public ResponseEntity<Map<String, String>> cancelAppointment(
            @PathVariable Long id,
            @PathVariable String token) {

        // Validate token for patient
        ResponseEntity<Map<String, String>> tokenValidation = service.validateToken(token, "patient");
        if (tokenValidation.getStatusCode() != HttpStatus.OK) {
            return tokenValidation;
        }

        boolean canceled = appointmentService.cancelAppointment(id);
        if (canceled) {
            return ResponseEntity.ok(Map.of("message", "Appointment canceled successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Failed to cancel appointment"));
        }
    }
}


// 1. Set Up the Controller Class:
//    - Annotate the class with `@RestController` to define it as a REST API controller.
//    - Use `@RequestMapping("/appointments")` to set a base path for all appointment-related endpoints.
//    - This centralizes all routes that deal with booking, updating, retrieving, and canceling appointments.


// 2. Autowire Dependencies:
//    - Inject `AppointmentService` for handling the business logic specific to appointments.
//    - Inject the general `Service` class, which provides shared functionality like token validation and appointment checks.


// 3. Define the `getAppointments` Method:
//    - Handles HTTP GET requests to fetch appointments based on date and patient name.
//    - Takes the appointment date, patient name, and token as path variables.
//    - First validates the token for role `"doctor"` using the `Service`.
//    - If the token is valid, returns appointments for the given patient on the specified date.
//    - If the token is invalid or expired, responds with the appropriate message and status code.


// 4. Define the `bookAppointment` Method:
//    - Handles HTTP POST requests to create a new appointment.
//    - Accepts a validated `Appointment` object in the request body and a token as a path variable.
//    - Validates the token for the `"patient"` role.
//    - Uses service logic to validate the appointment data (e.g., check for doctor availability and time conflicts).
//    - Returns success if booked, or appropriate error messages if the doctor ID is invalid or the slot is already taken.


// 5. Define the `updateAppointment` Method:
//    - Handles HTTP PUT requests to modify an existing appointment.
//    - Accepts a validated `Appointment` object and a token as input.
//    - Validates the token for `"patient"` role.
//    - Delegates the update logic to the `AppointmentService`.
//    - Returns an appropriate success or failure response based on the update result.


// 6. Define the `cancelAppointment` Method:
//    - Handles HTTP DELETE requests to cancel a specific appointment.
//    - Accepts the appointment ID and a token as path variables.
//    - Validates the token for `"patient"` role to ensure the user is authorized to cancel the appointment.
//    - Calls `AppointmentService` to handle the cancellation process and returns the result.
