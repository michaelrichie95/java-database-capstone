import { openModal } from '../components/modals.js';
import { getDoctors, filterDoctors, saveDoctor } from './services/doctorServices.js';
import { createDoctorCard } from './components/doctorCard.js';

const addDoctorBtn = document.getElementById('addDocBtn');
if (addDoctorBtn) {
  addDoctorBtn.addEventListener('click', () => openModal('addDoctor'));
}

window.addEventListener('DOMContentLoaded', loadDoctorCards);

export async function loadDoctorCards() {
  try {
    const contentDiv = document.getElementById('content');
    contentDiv.innerHTML = '';

    const doctors = await getDoctors();

    if (!doctors || doctors.length === 0) {
      contentDiv.innerHTML = '<p>No doctors found.</p>';
      return;
    }

    renderDoctorCards(doctors);
  } catch (error) {
    console.error('Error loading doctors:', error);
    alert('Failed to load doctors. Please try again.');
  }
}

export function renderDoctorCards(doctors) {
  const contentDiv = document.getElementById('content');
  contentDiv.innerHTML = '';

  doctors.forEach(doctor => {
    const card = createDoctorCard(doctor);
    contentDiv.appendChild(card);
  });
}

const searchBar = document.getElementById('searchBar');
const filterTime = document.getElementById('filterTime');
const filterSpecialty = document.getElementById('filterSpecialty');

if (searchBar) searchBar.addEventListener('input', filterDoctorsOnChange);
if (filterTime) filterTime.addEventListener('change', filterDoctorsOnChange);
if (filterSpecialty) filterSpecialty.addEventListener('change', filterDoctorsOnChange);

export async function filterDoctorsOnChange() {
  try {
    const name = searchBar?.value.trim() || null;
    const time = filterTime?.value || null;
    const specialty = filterSpecialty?.value || null;

    const result = await filterDoctors(name, time, specialty);
    const doctors = result.doctors || [];

    if (doctors.length === 0) {
      document.getElementById('content').innerHTML = '<p>No doctors found with the given filters.</p>';
      return;
    }

    renderDoctorCards(doctors);
  } catch (error) {
    console.error('Error filtering doctors:', error);
    alert('Failed to filter doctors. Please try again.');
  }
}

export async function adminAddDoctor(event) {
  event.preventDefault();

  const name = document.getElementById('doctorName').value.trim();
  const email = document.getElementById('doctorEmail').value.trim();
  const phone = document.getElementById('doctorPhone').value.trim();
  const password = document.getElementById('doctorPassword').value;
  const specialty = document.getElementById('doctorSpecialty').value.trim();

  const availabilityCheckboxes = document.querySelectorAll("input[name='availability']:checked");
  const availableTimes = Array.from(availabilityCheckboxes).map(cb => cb.value);

  const token = localStorage.getItem('token');
  if (!token) {
    alert('Admin token not found. Please log in.');
    return;
  }

  const doctor = { name, email, phone, password, specialty, availableTimes };

  try {
    const response = await saveDoctor(doctor, token);

    if (response.success) {
      alert('Doctor added successfully!');
      openModal('addDoctor', true);
      loadDoctorCards();
    } else {
      alert('Failed to add doctor: ' + response.message);
    }
  } catch (error) {
    console.error('Error adding doctor:', error);
    alert('Error occurred while adding doctor. Please try again.');
  }
}


/*
  This script handles the admin dashboard functionality for managing doctors:
  - Loads all doctor cards
  - Filters doctors by name, time, or specialty
  - Adds a new doctor via modal form


  Attach a click listener to the "Add Doctor" button
  When clicked, it opens a modal form using openModal('addDoctor')


  When the DOM is fully loaded:
    - Call loadDoctorCards() to fetch and display all doctors


  Function: loadDoctorCards
  Purpose: Fetch all doctors and display them as cards

    Call getDoctors() from the service layer
    Clear the current content area
    For each doctor returned:
    - Create a doctor card using createDoctorCard()
    - Append it to the content div

    Handle any fetch errors by logging them


  Attach 'input' and 'change' event listeners to the search bar and filter dropdowns
  On any input change, call filterDoctorsOnChange()


  Function: filterDoctorsOnChange
  Purpose: Filter doctors based on name, available time, and specialty

    Read values from the search bar and filters
    Normalize empty values to null
    Call filterDoctors(name, time, specialty) from the service

    If doctors are found:
    - Render them using createDoctorCard()
    If no doctors match the filter:
    - Show a message: "No doctors found with the given filters."

    Catch and display any errors with an alert


  Function: renderDoctorCards
  Purpose: A helper function to render a list of doctors passed to it

    Clear the content area
    Loop through the doctors and append each card to the content area


  Function: adminAddDoctor
  Purpose: Collect form data and add a new doctor to the system

    Collect input values from the modal form
    - Includes name, email, phone, password, specialty, and available times

    Retrieve the authentication token from localStorage
    - If no token is found, show an alert and stop execution

    Build a doctor object with the form values

    Call saveDoctor(doctor, token) from the service

    If save is successful:
    - Show a success message
    - Close the modal and reload the page

    If saving fails, show an error message
*/
