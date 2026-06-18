# MediCore

## CS-512 App Development Project

**Project Title:** MediCore - Medical Management System  
**Submitted By:** Shahwaiz Ali  
**AG No:** 2023-ag-10145  
**Course:** CS-512 App Development  
**Submitted To:** Sir Wasim Malik  
**Technology:** Android Studio, Java, Firebase Authentication, Cloud Firestore  

---

## 1. Introduction

MediCore is an Android-based Medical Management System designed to manage basic patient and doctor workflows in a clinic or hospital environment. The application provides separate role-based features for patients and doctors inside a single app.

The patient can create and view a profile, request appointments, view completed treatment history, and check bill history. The doctor can view his profile, manage pending and today's appointments, update patient treatment records, generate bills, and view patient treatment history.

The project uses Firebase Authentication for login and Cloud Firestore for storing patient records, appointment details, treatment history, and bills.

---

## 2. Project Objective

The main objective of MediCore is to provide a simple, organized, and user-friendly medical management application for both patients and doctors.

The project aims to:

- Provide role-based access for Patient and Doctor.
- Allow patient profile creation and viewing.
- Allow patients to request appointments.
- Allow doctors to approve, reject, or complete appointments.
- Store and display treatment history.
- Generate and view bills for completed appointments.
- Use Firebase as a real-time cloud database.
- Present the application with a clean deep blue and white medical theme.

---

## 3. Unique Features

MediCore includes some features that make it different from a simple medical management app:

- Animated splash screen with MediCore branding.
- Single role-based app for both patient and doctor.
- Deep blue and white professional medical UI theme.
- Doctor dashboard with quick statistics.
- Appointment status badges such as Pending, Approved, Rejected, Completed, and Paid.
- Receipt-style bill generation screen.
- Firestore demo data seeding for quick project checking.
- Project identity included with student name and AG number.

---

## 4. Tools and Technologies Used

| Tool / Technology | Purpose |
|---|---|
| Android Studio | Main development environment |
| Java | Application programming language |
| XML / Programmatic UI | Android screen layout and interface |
| Firebase Authentication | Email/password login |
| Cloud Firestore | Cloud database |
| Gradle | Project build system |
| Android Emulator / Phone | Testing and running the app |

---

## 5. Application Theme

The application uses a **Deep Blue and White** theme. This theme was selected because blue is commonly associated with trust, safety, and medical systems. The UI uses cards, buttons, and clean spacing to make the app look modern and easy to use.

Main theme colors:

- Deep Blue: `#0A3D91`
- Dark Blue: `#082B63`
- White: `#FFFFFF`
- Light Background: `#F5FAFF`

---

## 6. Main Users of the System

### 6.1 Patient

The patient is able to:

- Create or update patient profile.
- View personal profile.
- Request an appointment.
- View bill history.
- View treatment history.

### 6.2 Doctor

The doctor is able to:

- View doctor profile.
- View pending appointments.
- View today's appointments.
- Approve, reject, or complete appointments.
- Update patient disease, prescription, and progress.
- Generate patient bill.
- View treatment history of treated patients.

---

## 7. Functional Requirements

### 7.1 Patient Requirements

| Requirement | Description |
|---|---|
| Patient Creation | Patient can create or update his profile. |
| Patient Home | Patient can view his profile information. |
| Bills History | Patient can view bill history of completed appointments. |
| Treatment History | Patient can view disease, prescription, and progress history. |
| Patient Appointment | Patient can send appointment request to doctor. |

### 7.2 Doctor Requirements

| Requirement | Description |
|---|---|
| Doctor Profile | Doctor can view his own profile. |
| Pending Appointments | Doctor can view pending appointments against his doctor ID. |
| Today's Appointments | Doctor can view appointments for current day. |
| Appointment Action | Doctor can approve, reject, or complete appointments. |
| History Update | Doctor can update prescription, disease, and progress. |
| Generate Bill | Doctor can generate bill for patient. |
| Patient History | Doctor can view treatment history of treated patients. |

---

## 8. Non-Functional Requirements

- The app should be easy to use.
- The interface should be clean and readable.
- Patient and doctor workflows should be separated clearly.
- Firebase should store records online.
- The app should run on Android devices with minimum SDK 24 or above.
- The design should look suitable for a 6th semester app development project.

---

## 9. Firebase Configuration

Firebase is used for authentication and database storage.

### 9.1 Firebase Authentication

Firebase Authentication is used for email/password login.

Required Firebase setting:

- Authentication > Sign-in method > Email/Password > Enabled

Demo accounts:

| Role | Email | Password |
|---|---|---|
| Patient | patient@medicore.pk | 123456 |
| Doctor | doctor@medicore.pk | 123456 |

If these accounts are not manually created in Firebase Authentication, the app also provides demo buttons:

- Open Demo Patient
- Open Demo Doctor

### 9.2 Cloud Firestore

Cloud Firestore is used to store:

- Patients
- Appointments
- Treatments
- Bills

For project demonstration, Firestore can be started in test mode.

---

## 10. Database Design

The application uses the following Firestore collections.

### 10.1 Patients Collection

Collection name: `patients`

| Field | Description |
|---|---|
| name | Patient name |
| email | Patient email |
| age | Patient age |
| phone | Patient phone number |
| bloodGroup | Patient blood group |
| address | Patient address |

### 10.2 Appointments Collection

Collection name: `appointments`

| Field | Description |
|---|---|
| patientId | ID of patient |
| patientName | Name of patient |
| doctorId | ID of doctor |
| date | Appointment date |
| time | Appointment time |
| reason | Reason or symptoms |
| status | Pending, Approved, Rejected, or Completed |

### 10.3 Treatments Collection

Collection name: `treatments`

| Field | Description |
|---|---|
| patientId | ID of patient |
| patientName | Name of patient |
| doctorId | ID of doctor |
| date | Treatment date |
| disease | Diagnosed disease |
| prescription | Medicine or prescription |
| progress | Patient progress |

### 10.4 Bills Collection

Collection name: `bills`

| Field | Description |
|---|---|
| patientId | ID of patient |
| patientName | Name of patient |
| doctorId | ID of doctor |
| date | Bill date |
| doctorFee | Doctor fee |
| medicine | Medicine charges |
| lab | Lab charges |
| total | Total amount |
| status | Paid / unpaid status |

---

## 11. System Workflow

### 11.1 Patient Workflow

1. Patient opens the app.
2. Animated splash screen appears.
3. Patient selects Patient role.
4. Patient logs in or opens demo patient mode.
5. Patient views profile.
6. Patient can create/update profile.
7. Patient requests an appointment.
8. Patient views bill history.
9. Patient views treatment history.

### 11.2 Doctor Workflow

1. Doctor opens the app.
2. Doctor selects Doctor role.
3. Doctor logs in or opens demo doctor mode.
4. Doctor views dashboard and profile.
5. Doctor checks pending appointments.
6. Doctor checks today's appointments.
7. Doctor approves, rejects, or completes appointments.
8. Doctor updates patient treatment history.
9. Doctor generates patient bill.
10. Doctor views patient history.

---

## 12. Screens in the Application

The application contains the following main screens:

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

---

## 13. Testing

The application was tested using Android Studio and an Android device/emulator.

### 13.1 Test Cases

| Test Case | Expected Result | Status |
|---|---|---|
| App launches successfully | Splash screen appears | Passed |
| Select Patient role | Patient login form appears | Passed |
| Select Doctor role | Doctor login form appears | Passed |
| Open demo patient | Patient home screen opens | Passed |
| Save patient profile | Profile data is stored | Passed |
| Request appointment | Appointment record is created | Passed |
| Open demo doctor | Doctor dashboard opens | Passed |
| View pending appointments | Pending appointments are displayed | Passed |
| Approve appointment | Status changes to Approved | Passed |
| Reject appointment | Status changes to Rejected | Passed |
| Update treatment | Treatment record is saved | Passed |
| Generate bill | Bill record is saved | Passed |
| View bill history | Bill details are displayed | Passed |
| View treatment history | Treatment details are displayed | Passed |

---

## 14. How to Run the Project

1. Open Android Studio.
2. Open the `MediCore` project folder.
3. Wait for Gradle sync to finish.
4. Make sure `google-services.json` is placed inside the `app` folder.
5. Connect an Android phone or start an emulator.
6. Click the Run button.
7. Select Patient or Doctor role from the app.

---

## 15. Limitations

- The app is designed for academic demonstration.
- It uses simple role selection instead of advanced admin role verification.
- Firestore test mode is suitable for demonstration, but production apps require strict security rules.
- The bill and treatment update screens use the demo patient flow for quick checking.
- The UI is built in a simple way to keep the project understandable for semester-level presentation.

---

## 16. Future Enhancements

The project can be improved in the future by adding:

- Separate admin panel.
- Doctor registration.
- Multiple doctors and specializations.
- Appointment time slot availability.
- Push notifications.
- PDF bill download.
- Search and filter for patient history.
- Profile image upload.
- Strong Firestore security rules.

---

## 17. Conclusion

MediCore is a complete Android Medical Management System that fulfills the required patient and doctor features. It includes patient creation, appointment handling, bill history, treatment history, doctor profile, appointment approval, history update, bill generation, and patient history.

The project uses Firebase Authentication and Cloud Firestore, making it more practical than a static local app. Its animated splash screen, role-based design, dashboard layout, and clean medical theme make it presentable and unique for the CS-512 App Development submission.

---

## 18. Viva Preparation Notes

Important points to explain during viva:

- The app is named MediCore and was developed for a medical management system.
- Firebase Authentication is used for login.
- Cloud Firestore is used for storing patient, appointment, treatment, and bill data.
- The app has two main roles: Patient and Doctor.
- Patient can create profile, book appointment, view bills, and view treatment history.
- Doctor can manage appointments, update history, generate bills, and view patient history.
- The animated splash screen is the unique feature.
- Firestore demo data is seeded automatically to make checking easier.

Possible viva questions:

| Question | Short Answer |
|---|---|
| Why did you use Firebase? | Firebase provides cloud authentication and database support without building a separate backend. |
| What is Firestore? | Firestore is a NoSQL cloud database used to store app records in collections and documents. |
| What are the main roles? | Patient and Doctor. |
| What is the unique feature? | Animated splash screen with role-based dashboard UI. |
| What language is used? | Java. |
| What is the minimum SDK? | API 24. |
| Where is patient data stored? | In the `patients` collection in Firestore. |
| How are appointment statuses handled? | Each appointment has a `status` field such as Pending, Approved, Rejected, or Completed. |

