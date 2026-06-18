MEDICORE - MEDICAL MANAGEMENT SYSTEM
CS-512 APP DEVELOPMENT PROJECT

Submitted By: Shahwaiz Ali
AG No: 2023-ag-10145
Submitted To: Sir Wasim Malik
Technology: Android Studio, Java, Firebase Authentication, Cloud Firestore

1. INTRODUCTION

MediCore is an Android-based Medical Management System designed to manage basic patient and doctor workflows in a clinic or hospital environment. The application provides role-based features for patients and doctors inside a single app.

The patient can create and view a profile, request appointments, view completed treatment history, and check bill history. The doctor can view his profile, manage pending and today's appointments, update patient treatment records, generate bills, and view patient treatment history.

2. PROJECT OBJECTIVE

The objective of MediCore is to provide a simple and user-friendly medical management application.

Main objectives:
- Provide role-based access for Patient and Doctor.
- Allow patient profile creation and viewing.
- Allow patients to request appointments.
- Allow doctors to approve, reject, or complete appointments.
- Store and display treatment history.
- Generate and view bills.
- Use Firebase as a cloud database.
- Present a clean deep blue and white medical interface.

3. UNIQUE FEATURES

- Animated splash screen with MediCore branding.
- Single role-based app for both patient and doctor.
- Deep blue and white professional medical UI.
- Doctor dashboard with quick statistics.
- Appointment status badges.
- Receipt-style bill generation.
- Firestore demo data for quick checking.
- Student identity included in the app.

4. TOOLS AND TECHNOLOGIES

- Android Studio: Development environment
- Java: Programming language
- Firebase Authentication: Email/password login
- Cloud Firestore: Database
- Gradle: Build system
- Android Emulator/Phone: Testing

5. APPLICATION THEME

The application uses a deep blue and white theme. Blue was selected because it represents trust, safety, and medical care.

Main colors:
- Deep Blue: #0A3D91
- Dark Blue: #082B63
- White: #FFFFFF
- Light Background: #F5FAFF

6. USERS OF THE SYSTEM

Patient:
- Create or update profile
- View profile
- Request appointment
- View bill history
- View treatment history

Doctor:
- View doctor profile
- View pending appointments
- View today's appointments
- Approve, reject, or complete appointments
- Update patient disease, prescription, and progress
- Generate patient bill
- View patient treatment history

7. FUNCTIONAL REQUIREMENTS

Patient Requirements:
- Patient Creation: Patient can create or update profile.
- Patient Home: Patient can view profile information.
- Bills History: Patient can view bill history of completed appointments.
- Treatment History: Patient can view treatment records.
- Patient Appointment: Patient can request an appointment.

Doctor Requirements:
- Doctor Profile: Doctor can view his own profile.
- Pending Appointments: Doctor can view pending appointments against doctor ID.
- Today's Appointments: Doctor can view current day appointments.
- Appointment Action: Doctor can approve, reject, or complete appointments.
- History Update: Doctor can update prescription, disease, and progress.
- Generate Bill: Doctor can generate patient bill.
- Patient History: Doctor can view treatment history of treated patients.

8. FIREBASE CONFIGURATION

Firebase Authentication:
- Enable Email/Password sign-in.

Demo accounts:
- Patient: patient@medicore.pk / 123456
- Doctor: doctor@medicore.pk / 123456

Cloud Firestore:
- Used for storing patients, appointments, treatments, and bills.
- Firestore can be started in test mode for demonstration.

9. DATABASE DESIGN

Collection: patients
Fields:
- name
- email
- age
- phone
- bloodGroup
- address

Collection: appointments
Fields:
- patientId
- patientName
- doctorId
- date
- time
- reason
- status

Collection: treatments
Fields:
- patientId
- patientName
- doctorId
- date
- disease
- prescription
- progress

Collection: bills
Fields:
- patientId
- patientName
- doctorId
- date
- doctorFee
- medicine
- lab
- total
- status

10. SYSTEM WORKFLOW

Patient Workflow:
1. Patient opens app.
2. Animated splash screen appears.
3. Patient selects Patient role.
4. Patient logs in or opens demo patient mode.
5. Patient views profile.
6. Patient creates or updates profile.
7. Patient requests appointment.
8. Patient views bill history.
9. Patient views treatment history.

Doctor Workflow:
1. Doctor opens app.
2. Doctor selects Doctor role.
3. Doctor logs in or opens demo doctor mode.
4. Doctor views dashboard.
5. Doctor checks pending appointments.
6. Doctor checks today's appointments.
7. Doctor approves, rejects, or completes appointments.
8. Doctor updates patient history.
9. Doctor generates bill.
10. Doctor views patient history.

11. SCREENS

1. Animated Splash Screen
2. Role-Based Login Screen
3. Patient Home Screen
4. Patient Creation Screen
5. Patient Appointment Screen
6. Bills History Screen
7. Treatment History Screen
8. Doctor Dashboard Screen
9. Pending Appointments Screen
10. Today's Appointments Screen
11. History Update Screen
12. Generate Bill Screen
13. Patient History Screen

12. TESTING

Tested features:
- App launch
- Splash screen
- Patient role selection
- Doctor role selection
- Demo patient opening
- Demo doctor opening
- Patient profile saving
- Appointment request
- Pending appointment view
- Appointment approval/rejection/completion
- Treatment update
- Bill generation
- Bill history view
- Treatment history view

13. HOW TO RUN

1. Open Android Studio.
2. Open the MediCore project folder.
3. Wait for Gradle sync.
4. Make sure google-services.json is inside the app folder.
5. Start emulator or connect Android phone.
6. Click Run.
7. Select Patient or Doctor role.

14. LIMITATIONS

- The app is designed for academic demonstration.
- It uses simple role selection.
- Firestore test mode is suitable for demo but not production.
- Advanced admin management is not included.
- PDF bill download is not included.

15. FUTURE ENHANCEMENTS

- Admin panel
- Doctor registration
- Multiple doctors
- Appointment time slots
- Push notifications
- PDF bill download
- Search and filter history
- Profile image upload
- Strong Firestore security rules

16. CONCLUSION

MediCore is a complete Android Medical Management System that fulfills the patient and doctor requirements given in the project. It includes patient creation, appointment handling, bill history, treatment history, doctor profile, appointment approval, history update, bill generation, and patient history.

The project uses Firebase Authentication and Cloud Firestore, making it practical and cloud-based. Its animated splash screen, role-based design, dashboard layout, and clean medical theme make it suitable for CS-512 App Development submission.

17. VIVA POINTS

- App name: MediCore
- Developer: Shahwaiz Ali
- AG No: 2023-ag-10145
- Main roles: Patient and Doctor
- Language: Java
- Database: Cloud Firestore
- Authentication: Firebase Authentication
- Unique feature: Animated splash screen
- Theme: Deep Blue and White
- Main collections: patients, appointments, treatments, bills

Possible Questions:

Q: Why did you use Firebase?
A: Firebase provides authentication and cloud database support without needing a separate backend.

Q: What is Firestore?
A: Firestore is a NoSQL cloud database that stores data in collections and documents.

Q: What are the main roles?
A: Patient and Doctor.

Q: What is the unique feature?
A: Animated splash screen with role-based dashboard UI.

Q: How are appointment statuses handled?
A: Each appointment has a status field such as Pending, Approved, Rejected, or Completed.

