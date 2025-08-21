## MySQL Database Design

### Table: admin
- admin_id: INT, Primary Key, Auto Increment
- admin_username: VARCHAR(20), Unique, Not Null
- admin_password: VARCHAR(255), Not Null

### Table: appointments
- appointment_id: INT, Primary Key, Auto Increment
- doctor_id: INT, Foreign Key (doctor_id) REFERENCES doctors(doctor_id)
- appointment_patient_id: INT, Foreign Key (patient_id) REFERENCES patients(patient_id)
- appointment_time: DATETIME, Not Null
- appointment_status: INT (0 = Scheduled, 1 = Completed)

### Table: doctors
- doctor_id: INT, Primary Key, Auto Increment
- doctor_name: VARCHAR(100), Not Null
- doctor_specialty: VARCHAR(50), Not Null
- doctor_email: VARCHAR(100), Unique, Not Null
- doctor_password: VARCHAR(255), Not Null
- doctor_phone: VARCHAR(20), Nullable

### Table: patients
- patient_id: INT, Primary Key, Auto Increment
- patient_name: VARCHAR(100), Not Null
- patient_email: VARCHAR(100), Unique, Nullable
- patient_password: VARCHAR(255), Not Null
- patient_phone: VARCHAR(20), Nullable
- patient_address: VARCHAR(255), Nullable

### Collection: prescriptions
```json
{
  "prescription_id": "ObjectId('64abc123456')",
  "patient_name": "John Smith",
  "appointment_id": 51,
  "medication": "Paracetamol",
  "dosage": "500mg",
  "doctorNotes": "Take 1 tablet every 6 hours."
}
