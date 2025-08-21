## MySQL Database Design

### Table: admin
- admin_id: INT, Primary Key, Auto Increment
- username: VARCHAR(20), Unique, Not Null
- password_hash: VARCHAR(255), Not Null
- email: VARCHAR(100), Unique, Not Null
- last_login: DATETIME, Nullable

### Table: appointments
- appointment_id: INT, Primary Key, Auto Increment
- doctor_id: INT, Foreign Key → doctors(id)
- patient_id: INT, Foreign Key → patients(id)
- appointment_time: DATETIME, Not Null
- status: INT (0 = Scheduled, 1 = Completed)

### Table: doctors
- doctor_id: INT, Primary Key, Auto Increment
- first_name: VARCHAR(20), Not Null
- last_name: VARCHAR(20), Not Null
- email: VARCHAR(100), Unique, Not Null
- phone_number: VARCHAR(20), Nullable
- specialization: VARCHAR(30), Not Null
- license_number: VARCHAR(20), Unique, Nullable
- status: INT (0 = Inactive, 1 = Active)
- available_start_time: TIME, Nullable
- available_end_time: TIME, Nullable

### Table: patients
- patient_id: INT, Primary Key, Auto Increment
- first_name: VARCHAR(20), Not Null
- last_name: VARCHAR(20), Not Null
- email: VARCHAR(100), Unique, Nullable
- phone_number: VARCHAR(20), Nullable
- date_of_birth: DATE, Nullable
- gender: ENUM('male', 'female', 'N/A'), Nullable
- address: VARCHAR(100), Nullable

### Collection: prescriptions
```json
{
  "_id": "ObjectId('64abc123456')",
  "patientName": "John Smith",
  "appointmentId": 51,
  "medication": "Paracetamol",
  "dosage": "500mg",
  "doctorNotes": "Take 1 tablet every 6 hours.",
  "refillCount": 2,
  "pharmacy": {
    "name": "Walgreens SF",
    "location": "Market Street"
  }
}
