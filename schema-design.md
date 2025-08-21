## MySQL Database Design

### Table: admin
- admin_id: INT, Primary Key, Auto Increment
- admin_username: VARCHAR(20), Unique, Not Null
- admin_password: VARCHAR(255), Not Null

### Table: appointments
- appointment_id: INT, Primary Key, Auto Increment
- appointment_doctor: INT, Foreign Key → doctors(id)
- appointment_patient: INT, Foreign Key → patients(id)
- appointment_time: DATETIME, Not Null
- appointment_status: INT (0 = Scheduled, 1 = Completed)

### Table: doctors
- doctor_id: INT, Primary Key, Auto Increment
- name: VARCHAR(100), Not Null
- specialty: VARCHAR(50), Not Null
- email: VARCHAR(100), Unique, Not Null
- password: VARCHAR(255), Not Null
- phone: VARCHAR(20), Nullable

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
