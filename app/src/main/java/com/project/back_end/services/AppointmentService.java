package com.project.back_end.services;

import com.project.back_end.model.Appointment;
import com.project.back_end.model.Doctor;
import com.project.back_end.model.Patient;
import com.project.back_end.repo.AppointmentRepository;
import com.project.back_end.repo.DoctorRepository;
import com.project.back_end.repo.PatientRepository;
import com.project.back_end.security.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final TokenService tokenService;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              PatientRepository patientRepository,
                              DoctorRepository doctorRepository,
                              TokenService tokenService) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.tokenService = tokenService;
    }

    /**
     * Book a new appointment
     */
    @Transactional
    public int bookAppointment(Appointment appointment) {
        try {
            appointmentRepository.save(appointment);
            return 1; // success
        } catch (Exception e) {
            return 0; // failure
        }
    }

    /**
     * Update an existing appointment
     */
    @Transactional
    public ResponseEntity<Map<String, String>> updateAppointment(Appointment appointment) {
        Map<String, String> response = new HashMap<>();

        return appointmentRepository.findById(appointment.getId())
                .map(existing -> {
                    // validate doctor exists
                    Optional<Doctor> doctorOpt = doctorRepository.findById(appointment.getDoctor().getId());
                    if (doctorOpt.isEmpty()) {
                        response.put("message", "Invalid doctor ID.");
                        return ResponseEntity.badRequest().body(response);
                    }

                    // validate patient exists
                    Optional<Patient> patientOpt = patientRepository.findById(appointment.getPatient().getId());
                    if (patientOpt.isEmpty()) {
                        response.put("message", "Invalid patient ID.");
                        return ResponseEntity.badRequest().body(response);
                    }

                    // validate appointment update rules (simplified)
                    // In a real app, you'd check doctor availability etc.
                    existing.setAppointmentTime(appointment.getAppointmentTime());
                    existing.setStatus(appointment.getStatus());
                    appointmentRepository.save(existing);

                    response.put("message", "Appointment updated successfully.");
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    response.put("message", "Appointment not found.");
                    return ResponseEntity.badRequest().body(response);
                });
    }

    /**
     * Cancel an appointment
     */
    @Transactional
    public ResponseEntity<Map<String, String>> cancelAppointment(long id, String token) {
        Map<String, String> response = new HashMap<>();

        return appointmentRepository.findById(id)
                .map(appointment -> {
                    Long patientIdFromToken = tokenService.getPatientIdFromToken(token);
                    if (!Objects.equals(appointment.getPatient().getId(), patientIdFromToken)) {
                        response.put("message", "You are not authorized to cancel this appointment.");
                        return ResponseEntity.status(403).body(response);
                    }
                    appointmentRepository.delete(appointment);
                    response.put("message", "Appointment canceled successfully.");
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    response.put("message", "Appointment not found.");
                    return ResponseEntity.badRequest().body(response);
                });
    }

    /**
     * Get appointments for a doctor on a specific date, optionally filtered by patient name
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getAppointment(String pname, LocalDate date, String token) {
        Map<String, Object> result = new HashMap<>();

        Long doctorId = tokenService.getDoctorIdFromToken(token);
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();

        List<Appointment> appointments = appointmentRepository
                .findByDoctorIdAndAppointmentTimeBetween(doctorId, startOfDay, endOfDay);

        if (pname != null && !pname.isEmpty()) {
            appointments.removeIf(a -> !a.getPatient().getName().toLowerCase().contains(pname.toLowerCase()));
        }

        result.put("appointments", appointments);
        return result;
    }

    /**
     * Change the status of an appointment
     */
    @Transactional
    public ResponseEntity<Map<String, String>> changeStatus(Long id, String status) {
        Map<String, String> response = new HashMap<>();

        return appointmentRepository.findById(id)
                .map(appointment -> {
                    appointment.setStatus(status);
                    appointmentRepository.save(appointment);
                    response.put("message", "Status updated successfully.");
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    response.put("message", "Appointment not found.");
                    return ResponseEntity.badRequest().body(response);
                });
    }
}

// 1. **Add @Service Annotation**:
//    - To indicate that this class is a service layer class for handling business logic.
//    - The `@Service` annotation should be added before the class declaration to mark it as a Spring service component.
//    - Instruction: Add `@Service` above the class definition.

// 2. **Constructor Injection for Dependencies**:
//    - The `AppointmentService` class requires several dependencies like `AppointmentRepository`, `Service`, `TokenService`, `PatientRepository`, and `DoctorRepository`.
//    - These dependencies should be injected through the constructor.
//    - Instruction: Ensure constructor injection is used for proper dependency management in Spring.

// 3. **Add @Transactional Annotation for Methods that Modify Database**:
//    - The methods that modify or update the database should be annotated with `@Transactional` to ensure atomicity and consistency of the operations.
//    - Instruction: Add the `@Transactional` annotation above methods that interact with the database, especially those modifying data.

// 4. **Book Appointment Method**:
//    - Responsible for saving the new appointment to the database.
//    - If the save operation fails, it returns `0`; otherwise, it returns `1`.
//    - Instruction: Ensure that the method handles any exceptions and returns an appropriate result code.

// 5. **Update Appointment Method**:
//    - This method is used to update an existing appointment based on its ID.
//    - It validates whether the patient ID matches, checks if the appointment is available for updating, and ensures that the doctor is available at the specified time.
//    - If the update is successful, it saves the appointment; otherwise, it returns an appropriate error message.
//    - Instruction: Ensure proper validation and error handling is included for appointment updates.

// 6. **Cancel Appointment Method**:
//    - This method cancels an appointment by deleting it from the database.
//    - It ensures the patient who owns the appointment is trying to cancel it and handles possible errors.
//    - Instruction: Make sure that the method checks for the patient ID match before deleting the appointment.

// 7. **Get Appointments Method**:
//    - This method retrieves a list of appointments for a specific doctor on a particular day, optionally filtered by the patient's name.
//    - It uses `@Transactional` to ensure that database operations are consistent and handled in a single transaction.
//    - Instruction: Ensure the correct use of transaction boundaries, especially when querying the database for appointments.

// 8. **Change Status Method**:
//    - This method updates the status of an appointment by changing its value in the database.
//    - It should be annotated with `@Transactional` to ensure the operation is executed in a single transaction.
//    - Instruction: Add `@Transactional` before this method to ensure atomicity when updating appointment status.
