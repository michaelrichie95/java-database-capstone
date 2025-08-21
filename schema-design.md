## MySQL Database Design

### Table: admin
- admin_id: INT, Primary Key, Auto Increment
- username: VARCHAR(20), Unique, Not Null
- password: VARCHAR(255), Not Null

### Table: appointments
- appointment_id: INT, Primary Key, Auto Increment
- doctor_id: INT, Foreign Key → doctors(id)
- patient_id: INT, Foreign Key → patients(id)
- appointment_time: DATETIME, Not Null
- status: INT (0 = Scheduled, 1 = Completed)

### Table: doctors
- doctor_id: INT, Primary Key, Auto Increment
- name: VARCHAR(100), Not Null
- specialty: VARCHAR(50), Not Null
- email: VARCHAR(100), Unique, Not Null
- password: VARCHAR(255), Not Null
- phone: VARCHAR(20), Nullable

### Table: patients
- patient_id: INT, Primary Key, Auto Increment
- name: VARCHAR(100), Not Null
- email: VARCHAR(100), Unique, Nullable
- password: VARCHAR(255), Not Null
- phone: VARCHAR(20), Nullable
- address: VARCHAR(255), Nullable

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
