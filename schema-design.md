## MySQL Database Design

### Table: admins
- id: INT, Primary Key, Auto Increment
- username: VARCHAR(50), UNIQUE, NOT NULL
- password: VARCHAR(255), NOT NULL

### Table: doctors
- id: INT, Primary Key, Auto Increment
- name: VARCHAR(255), NOT NULL
- speciality: VARCHAR(100), NOT NULL
- email: VARCHAR(255), NOT NULL
- password: VARCHAR(255), NOT NULL
- phone: VARCHAR(15), NOT NULL
- available_times: Element Collection (List of VARCHAR)

### Table: patients
- id: INT, Primary Key, Auto Increment
- name: VARCHAR(50), NOT NULL
- email: VARCHAR(255), NOT NULL
- password: VARCHAR(255), NOT NULL
- phone: VARCHAR(15), NOT NULL
- address: VARCHAR(255)

### Table: appointments
- id: INT, Primary Key, Auto Increment
- doctor_id: INT, Foreign Key → doctors(id)
- patient_id: INT, Foreign Key → patients(id)
- appointment_time: DATETIME, NOT NULL
- status: INT (0 = Scheduled, 1 = Completed), NOT NULL

### Table: doctor_available_times
- id: INT, Foreign Key (doctor_id) REFERENCES doctors(doctor_id)
- available_times: VARCHAR(20), Not Null

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
