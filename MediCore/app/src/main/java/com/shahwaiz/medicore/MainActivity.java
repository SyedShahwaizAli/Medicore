package com.shahwaiz.medicore;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final int BLUE = Color.rgb(10, 61, 145);
    private static final int DARK_BLUE = Color.rgb(8, 43, 99);
    private static final int ICE = Color.rgb(245, 250, 255);
    private static final int LINE = Color.rgb(216, 230, 247);
    private static final int TEXT = Color.rgb(19, 34, 56);
    private static final int GREEN = Color.rgb(25, 135, 84);
    private static final int RED = Color.rgb(190, 45, 64);
    private static final int AMBER = Color.rgb(181, 118, 0);

    private FrameLayout main;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private String selectedRole = "Patient";
    private String activePatientId = "patient-10145";
    private final String doctorId = "doctor-10145";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main = findViewById(R.id.main);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        seedDemoData();
        showSplash();
    }

    private void showSplash() {
        main.removeAllViews();
        FrameLayout splash = new FrameLayout(this);
        splash.setBackground(gradient(DARK_BLUE, BLUE, 0));

        LinearLayout box = new LinearLayout(this);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setGravity(Gravity.CENTER);
        box.setPadding(dp(28), dp(28), dp(28), dp(28));

        TextView logo = text("MC", 42, Color.WHITE, true);
        logo.setGravity(Gravity.CENTER);
        logo.setBackground(circle(Color.argb(45, 255, 255, 255), Color.WHITE, 2));
        LinearLayout.LayoutParams logoParams = new LinearLayout.LayoutParams(dp(112), dp(112));
        box.addView(logo, logoParams);

        TextView name = text("MediCore", 34, Color.WHITE, true);
        name.setGravity(Gravity.CENTER);
        name.setPadding(0, dp(18), 0, dp(4));
        box.addView(name);

        TextView credit = text("CS-512 App Development Project\nShahwaiz Ali  |  2023-ag-10145", 15, Color.rgb(223, 236, 255), false);
        credit.setGravity(Gravity.CENTER);
        box.addView(credit);

        ProgressBar progress = new ProgressBar(this);
        progress.setIndeterminate(true);
        LinearLayout.LayoutParams progressParams = new LinearLayout.LayoutParams(dp(42), dp(42));
        progressParams.topMargin = dp(26);
        box.addView(progress, progressParams);

        splash.addView(box, centered());
        main.addView(splash);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(logo, View.SCALE_X, 0.5f, 1.08f, 1f),
                ObjectAnimator.ofFloat(logo, View.SCALE_Y, 0.5f, 1.08f, 1f),
                ObjectAnimator.ofFloat(name, View.ALPHA, 0f, 1f),
                ObjectAnimator.ofFloat(credit, View.TRANSLATION_Y, dp(18), 0)
        );
        set.setDuration(1050);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.start();
        splash.postDelayed(this::showLogin, 1700);
    }

    private void showLogin() {
        main.removeAllViews();
        LinearLayout root = page();
        root.setPadding(dp(22), dp(30), dp(22), dp(22));

        TextView title = text("MediCore", 36, BLUE, true);
        root.addView(title);
        root.addView(text("Medical Management System by Shahwaiz Ali", 15, Color.rgb(82, 98, 121), false));
        root.addView(space(18));

        LinearLayout roleRow = row();
        Button patient = pill("Patient");
        Button doctor = pill("Doctor");
        patient.setOnClickListener(v -> {
            selectedRole = "Patient";
            showLogin();
        });
        doctor.setOnClickListener(v -> {
            selectedRole = "Doctor";
            showLogin();
        });
        roleRow.addView(patient, weight());
        roleRow.addView(doctor, weight());
        root.addView(roleRow);

        LinearLayout card = card();
        TextView formTitle = text(selectedRole + " Login", 21, TEXT, true);
        card.addView(formTitle);
        card.addView(text(selectedRole.equals("Patient")
                ? "Use demo login or create a patient account."
                : "Doctor dashboard login for appointment handling.", 14, Color.rgb(90, 104, 123), false));

        EditText email = field("Email address", InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        EditText pass = field("Password", InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        email.setText(selectedRole.equals("Patient") ? "patient@medicore.pk" : "doctor@medicore.pk");
        pass.setText("123456");
        card.addView(email);
        card.addView(pass);

        Button login = primary("Login");
        Button register = outline("Create Patient");
        Button demo = outline("Open Demo " + selectedRole);
        login.setOnClickListener(v -> login(email.getText().toString(), pass.getText().toString()));
        register.setOnClickListener(v -> registerPatient(email.getText().toString(), pass.getText().toString()));
        demo.setOnClickListener(v -> openHome());
        card.addView(login);
        if (selectedRole.equals("Patient")) card.addView(register);
        card.addView(demo);
        root.addView(card);

        TextView note = text("Firebase Auth + Firestore enabled. Demo data is created automatically for presentation.", 13, Color.rgb(97, 111, 130), false);
        note.setPadding(dp(4), dp(12), dp(4), 0);
        root.addView(note);
        main.addView(scroll(root));
    }

    private void login(String email, String password) {
        if (email.trim().isEmpty() || password.trim().isEmpty()) {
            toast("Enter email and password.");
            return;
        }
        auth.signInWithEmailAndPassword(email.trim(), password.trim())
                .addOnSuccessListener(result -> openHome())
                .addOnFailureListener(error -> toast("Firebase login failed. Use demo login or create account."));
    }

    private void registerPatient(String email, String password) {
        if (!selectedRole.equals("Patient")) {
            toast("Only patient account creation is required in project scope.");
            return;
        }
        auth.createUserWithEmailAndPassword(email.trim(), password.trim())
                .addOnSuccessListener(result -> {
                    activePatientId = result.getUser() == null ? activePatientId : result.getUser().getUid();
                    createPatientProfile(activePatientId, "New Patient", email.trim(), "Not added", "Not added", "Not added");
                    toast("Patient created successfully.");
                    showPatientCreation();
                })
                .addOnFailureListener(error -> toast("Could not create account. Try demo if account already exists."));
    }

    private void openHome() {
        if (selectedRole.equals("Doctor")) showDoctorHome();
        else showPatientHome();
    }

    private void showPatientHome() {
        main.removeAllViews();
        LinearLayout root = pageWithHeader("Patient Home", "Profile, appointments, bills and treatment records.");
        String uid = currentUidOrDemo();

        db.collection("patients").document(uid).get()
                .addOnSuccessListener(doc -> fillPatientHome(root, doc))
                .addOnFailureListener(error -> fillPatientHome(root, null));
        main.addView(scroll(root));
    }

    private void fillPatientHome(LinearLayout root, DocumentSnapshot doc) {
        LinearLayout profile = card();
        profile.addView(text("My Profile", 21, TEXT, true));
        profile.addView(info("Name", value(doc, "name", "Shahwaiz Ali")));
        profile.addView(info("AG No", "2023-ag-10145"));
        profile.addView(info("Email", value(doc, "email", "patient@medicore.pk")));
        profile.addView(info("Phone", value(doc, "phone", "0300-10145")));
        profile.addView(info("Blood Group", value(doc, "bloodGroup", "B+")));
        root.addView(profile);

        root.addView(menuButton("Create / Update Patient", this::showPatientCreation));
        root.addView(menuButton("Book Appointment", this::showAppointmentForm));
        root.addView(menuButton("Bills History", this::showBillHistory));
        root.addView(menuButton("Treatment History", this::showTreatmentHistory));
        root.addView(menuButton("Logout", this::showLogin));
    }

    private void showPatientCreation() {
        main.removeAllViews();
        LinearLayout root = pageWithHeader("Patient Creation", "Create or update the patient profile stored in Firestore.");
        LinearLayout card = card();
        EditText name = field("Patient name", InputType.TYPE_CLASS_TEXT);
        EditText age = field("Age", InputType.TYPE_CLASS_NUMBER);
        EditText phone = field("Phone number", InputType.TYPE_CLASS_PHONE);
        EditText blood = field("Blood group", InputType.TYPE_CLASS_TEXT);
        EditText address = field("Address", InputType.TYPE_CLASS_TEXT);
        name.setText("Shahwaiz Ali");
        age.setText("22");
        phone.setText("0300-10145");
        blood.setText("B+");
        address.setText("Faisalabad, Pakistan");
        card.addView(name);
        card.addView(age);
        card.addView(phone);
        card.addView(blood);
        card.addView(address);
        Button save = primary("Save Patient");
        save.setOnClickListener(v -> {
            createPatientProfile(currentUidOrDemo(), name.getText().toString(), "patient@medicore.pk",
                    age.getText().toString(), phone.getText().toString(), blood.getText().toString());
            toast("Patient profile saved.");
            showPatientHome();
        });
        card.addView(save);
        root.addView(card);
        root.addView(menuButton("Back", this::showPatientHome));
        main.addView(scroll(root));
    }

    private void showAppointmentForm() {
        main.removeAllViews();
        LinearLayout root = pageWithHeader("Patient Appointment", "Request an appointment with the assigned doctor.");
        LinearLayout card = card();
        EditText date = field("Date (yyyy-mm-dd)", InputType.TYPE_CLASS_TEXT);
        EditText time = field("Time", InputType.TYPE_CLASS_TEXT);
        EditText reason = field("Reason / symptoms", InputType.TYPE_CLASS_TEXT);
        date.setText(today());
        time.setText("11:30 AM");
        reason.setText("Fever and throat infection");
        card.addView(date);
        card.addView(time);
        card.addView(reason);
        Button submit = primary("Request Appointment");
        submit.setOnClickListener(v -> {
            Map<String, Object> map = new HashMap<>();
            map.put("patientId", currentUidOrDemo());
            map.put("patientName", "Shahwaiz Ali");
            map.put("doctorId", doctorId);
            map.put("date", date.getText().toString());
            map.put("time", time.getText().toString());
            map.put("reason", reason.getText().toString());
            map.put("status", "Pending");
            db.collection("appointments").add(map);
            toast("Appointment request sent.");
            showPatientHome();
        });
        card.addView(submit);
        root.addView(card);
        root.addView(menuButton("Back", this::showPatientHome));
        main.addView(scroll(root));
    }

    private void showBillHistory() {
        main.removeAllViews();
        LinearLayout root = pageWithHeader("Bills History", "Completed appointment invoices.");
        db.collection("bills").whereEqualTo("patientId", currentUidOrDemo()).get()
                .addOnSuccessListener(snap -> {
                    for (QueryDocumentSnapshot doc : snap) root.addView(billCard(doc));
                    if (snap.isEmpty()) root.addView(empty("No bills yet. Demo completed bill appears after doctor generates it."));
                })
                .addOnFailureListener(error -> root.addView(empty("Could not load Firestore bills.")));
        root.addView(menuButton("Back", this::showPatientHome));
        main.addView(scroll(root));
    }

    private void showTreatmentHistory() {
        main.removeAllViews();
        LinearLayout root = pageWithHeader("Treatment History", "Completed appointments with prescription and progress.");
        loadHistory(root, currentUidOrDemo(), false);
        root.addView(menuButton("Back", this::showPatientHome));
        main.addView(scroll(root));
    }

    private void showDoctorHome() {
        main.removeAllViews();
        LinearLayout root = pageWithHeader("Doctor Dashboard", "Appointments, history updates and billing.");
        LinearLayout profile = card();
        profile.addView(text("Doctor Profile", 21, TEXT, true));
        profile.addView(info("Name", "Dr. Wasim Malik"));
        profile.addView(info("Doctor ID", doctorId));
        profile.addView(info("Speciality", "General Medicine"));
        profile.addView(info("Project Student", "Shahwaiz Ali  |  2023-ag-10145"));
        root.addView(profile);

        LinearLayout stats = row();
        stats.addView(stat("Pending", "3"), weight());
        stats.addView(stat("Today", "2"), weight());
        stats.addView(stat("Treated", "5"), weight());
        root.addView(stats);

        root.addView(menuButton("Pending Appointments", this::showPendingAppointments));
        root.addView(menuButton("Today's Appointments", this::showTodaysAppointments));
        root.addView(menuButton("History Update", this::showHistoryUpdate));
        root.addView(menuButton("Generate Bill", this::showGenerateBill));
        root.addView(menuButton("Patient History", this::showDoctorPatientHistory));
        root.addView(menuButton("Logout", this::showLogin));
        main.addView(scroll(root));
    }

    private void showPendingAppointments() {
        main.removeAllViews();
        LinearLayout root = pageWithHeader("Pending Appointments", "Requests against doctor ID " + doctorId + ".");
        loadAppointments(root, "Pending", false);
        root.addView(menuButton("Back", this::showDoctorHome));
        main.addView(scroll(root));
    }

    private void showTodaysAppointments() {
        main.removeAllViews();
        LinearLayout root = pageWithHeader("Today's Appointments", "Select or reject appointments for " + today() + ".");
        loadAppointments(root, null, true);
        root.addView(menuButton("Back", this::showDoctorHome));
        main.addView(scroll(root));
    }

    private void loadAppointments(LinearLayout root, String status, boolean todayOnly) {
        db.collection("appointments").whereEqualTo("doctorId", doctorId).get()
                .addOnSuccessListener(snap -> {
                    boolean added = false;
                    for (QueryDocumentSnapshot doc : snap) {
                        String s = doc.getString("status");
                        String d = doc.getString("date");
                        if (status != null && !status.equals(s)) continue;
                        if (todayOnly && !today().equals(d)) continue;
                        root.addView(appointmentCard(doc));
                        added = true;
                    }
                    if (!added) root.addView(empty("No matching appointments found."));
                })
                .addOnFailureListener(error -> root.addView(empty("Could not load appointments.")));
    }

    private View appointmentCard(QueryDocumentSnapshot doc) {
        LinearLayout card = card();
        card.addView(text(doc.getString("patientName"), 20, TEXT, true));
        card.addView(info("Date", doc.getString("date") + " at " + doc.getString("time")));
        card.addView(info("Reason", safe(doc.getString("reason"))));
        card.addView(statusBadge(safe(doc.getString("status"))));

        LinearLayout actions = row();
        Button approve = outline("Approve");
        Button reject = outline("Reject");
        Button complete = primary("Complete");
        approve.setOnClickListener(v -> updateAppointment(doc.getId(), "Approved"));
        reject.setOnClickListener(v -> updateAppointment(doc.getId(), "Rejected"));
        complete.setOnClickListener(v -> updateAppointment(doc.getId(), "Completed"));
        actions.addView(approve, weight());
        actions.addView(reject, weight());
        actions.addView(complete, weight());
        card.addView(actions);
        return card;
    }

    private void updateAppointment(String id, String status) {
        db.collection("appointments").document(id).update("status", status);
        toast("Appointment marked " + status + ".");
        showDoctorHome();
    }

    private void showHistoryUpdate() {
        main.removeAllViews();
        LinearLayout root = pageWithHeader("History Update", "Update disease, prescription and progress.");
        LinearLayout card = card();
        EditText disease = field("Disease", InputType.TYPE_CLASS_TEXT);
        EditText prescription = field("Prescription", InputType.TYPE_CLASS_TEXT);
        EditText progress = field("Progress", InputType.TYPE_CLASS_TEXT);
        disease.setText("Acute throat infection");
        prescription.setText("Azithromycin 500mg, Panadol, warm fluids");
        progress.setText("Improving. Follow up after 5 days.");
        card.addView(disease);
        card.addView(prescription);
        card.addView(progress);
        Button save = primary("Save Treatment Record");
        save.setOnClickListener(v -> {
            Map<String, Object> map = new HashMap<>();
            map.put("patientId", activePatientId);
            map.put("patientName", "Shahwaiz Ali");
            map.put("doctorId", doctorId);
            map.put("date", today());
            map.put("disease", disease.getText().toString());
            map.put("prescription", prescription.getText().toString());
            map.put("progress", progress.getText().toString());
            db.collection("treatments").add(map);
            toast("Treatment history updated.");
            showDoctorHome();
        });
        card.addView(save);
        root.addView(card);
        root.addView(menuButton("Back", this::showDoctorHome));
        main.addView(scroll(root));
    }

    private void showGenerateBill() {
        main.removeAllViews();
        LinearLayout root = pageWithHeader("Generate Bill", "Create a receipt for completed treatment.");
        LinearLayout card = card();
        EditText fee = field("Doctor fee", InputType.TYPE_CLASS_NUMBER);
        EditText medicine = field("Medicine charges", InputType.TYPE_CLASS_NUMBER);
        EditText lab = field("Lab charges", InputType.TYPE_CLASS_NUMBER);
        fee.setText("1500");
        medicine.setText("900");
        lab.setText("700");
        card.addView(fee);
        card.addView(medicine);
        card.addView(lab);
        Button generate = primary("Generate Receipt");
        generate.setOnClickListener(v -> {
            int total = amount(fee) + amount(medicine) + amount(lab);
            Map<String, Object> map = new HashMap<>();
            map.put("patientId", activePatientId);
            map.put("patientName", "Shahwaiz Ali");
            map.put("doctorId", doctorId);
            map.put("date", today());
            map.put("doctorFee", amount(fee));
            map.put("medicine", amount(medicine));
            map.put("lab", amount(lab));
            map.put("total", total);
            map.put("status", "Paid");
            db.collection("bills").add(map);
            toast("Bill generated: Rs. " + total);
            showDoctorHome();
        });
        card.addView(generate);
        root.addView(card);
        root.addView(menuButton("Back", this::showDoctorHome));
        main.addView(scroll(root));
    }

    private void showDoctorPatientHistory() {
        main.removeAllViews();
        LinearLayout root = pageWithHeader("Patient History", "Treatment history of all treated patients.");
        loadHistory(root, null, true);
        root.addView(menuButton("Back", this::showDoctorHome));
        main.addView(scroll(root));
    }

    private void loadHistory(LinearLayout root, String patientId, boolean allPatients) {
        db.collection("treatments").get()
                .addOnSuccessListener(snap -> {
                    boolean added = false;
                    for (QueryDocumentSnapshot doc : snap) {
                        if (!allPatients && !safe(patientId).equals(doc.getString("patientId"))) continue;
                        root.addView(treatmentCard(doc));
                        added = true;
                    }
                    if (!added) root.addView(empty("No treatment records found."));
                })
                .addOnFailureListener(error -> root.addView(empty("Could not load treatment history.")));
    }

    private View treatmentCard(QueryDocumentSnapshot doc) {
        LinearLayout card = card();
        card.addView(text(safe(doc.getString("patientName")), 20, TEXT, true));
        card.addView(info("Date", safe(doc.getString("date"))));
        card.addView(info("Disease", safe(doc.getString("disease"))));
        card.addView(info("Prescription", safe(doc.getString("prescription"))));
        card.addView(info("Progress", safe(doc.getString("progress"))));
        return card;
    }

    private View billCard(QueryDocumentSnapshot doc) {
        LinearLayout card = card();
        card.addView(text("MediCore Invoice", 21, TEXT, true));
        card.addView(info("Patient", safe(doc.getString("patientName"))));
        card.addView(info("Date", safe(doc.getString("date"))));
        card.addView(info("Doctor Fee", "Rs. " + doc.getLong("doctorFee")));
        card.addView(info("Medicine", "Rs. " + doc.getLong("medicine")));
        card.addView(info("Lab", "Rs. " + doc.getLong("lab")));
        TextView total = text("Total: Rs. " + doc.getLong("total"), 22, BLUE, true);
        total.setPadding(0, dp(10), 0, 0);
        card.addView(total);
        card.addView(statusBadge(safe(doc.getString("status"))));
        return card;
    }

    private void seedDemoData() {
        createPatientProfile(activePatientId, "Shahwaiz Ali", "patient@medicore.pk", "22", "0300-10145", "B+");
        db.collection("appointments").document("demo-pending-1").set(mapOf(
                "patientId", activePatientId, "patientName", "Shahwaiz Ali", "doctorId", doctorId,
                "date", today(), "time", "10:30 AM", "reason", "Fever and body pain", "status", "Pending"));
        db.collection("appointments").document("demo-approved-1").set(mapOf(
                "patientId", activePatientId, "patientName", "Shahwaiz Ali", "doctorId", doctorId,
                "date", today(), "time", "12:00 PM", "reason", "Routine checkup", "status", "Approved"));
        db.collection("treatments").document("demo-treatment-1").set(mapOf(
                "patientId", activePatientId, "patientName", "Shahwaiz Ali", "doctorId", doctorId,
                "date", "2026-06-08", "disease", "Seasonal flu", "prescription", "Paracetamol and rest",
                "progress", "Recovered"));
        db.collection("bills").document("demo-bill-1").set(mapOf(
                "patientId", activePatientId, "patientName", "Shahwaiz Ali", "doctorId", doctorId,
                "date", "2026-06-08", "doctorFee", 1200, "medicine", 650, "lab", 0,
                "total", 1850, "status", "Paid"));
    }

    private void createPatientProfile(String id, String name, String email, String age, String phone, String blood) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("email", email);
        map.put("age", age);
        map.put("phone", phone);
        map.put("bloodGroup", blood);
        map.put("address", "Faisalabad, Pakistan");
        db.collection("patients").document(id).set(map);
    }

    private LinearLayout page() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(ICE);
        return root;
    }

    private LinearLayout pageWithHeader(String title, String subtitle) {
        LinearLayout root = page();
        root.setPadding(dp(18), dp(20), dp(18), dp(24));
        TextView brand = text("MediCore", 14, BLUE, true);
        root.addView(brand);
        root.addView(text(title, 29, TEXT, true));
        TextView sub = text(subtitle, 14, Color.rgb(88, 104, 125), false);
        sub.setPadding(0, dp(4), 0, dp(14));
        root.addView(sub);
        return root;
    }

    private LinearLayout card() {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(18), dp(18), dp(18), dp(18));
        card.setBackground(round(Color.WHITE, LINE, 1, 8));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
        params.setMargins(0, dp(10), 0, dp(10));
        card.setLayoutParams(params);
        return card;
    }

    private TextView text(String value, int sp, int color, boolean bold) {
        TextView tv = new TextView(this);
        tv.setText(value);
        tv.setTextSize(sp);
        tv.setTextColor(color);
        tv.setLineSpacing(dp(2), 1.0f);
        if (bold) tv.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        return tv;
    }

    private TextView info(String label, String value) {
        TextView tv = text(label + ": " + safe(value), 15, TEXT, false);
        tv.setPadding(0, dp(5), 0, dp(5));
        return tv;
    }

    private TextView statusBadge(String status) {
        int color = AMBER;
        if ("Approved".equals(status) || "Completed".equals(status) || "Paid".equals(status)) color = GREEN;
        if ("Rejected".equals(status)) color = RED;
        TextView badge = text(status, 13, color, true);
        badge.setPadding(dp(10), dp(5), dp(10), dp(5));
        badge.setBackground(round(Color.argb(20, Color.red(color), Color.green(color), Color.blue(color)), color, 1, 18));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
        params.topMargin = dp(8);
        badge.setLayoutParams(params);
        return badge;
    }

    private Button primary(String label) {
        Button button = button(label, Color.WHITE, BLUE, BLUE);
        button.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        return button;
    }

    private Button outline(String label) {
        return button(label, BLUE, Color.WHITE, LINE);
    }

    private Button pill(String label) {
        boolean active = selectedRole.equals(label);
        return button(label, active ? Color.WHITE : BLUE, active ? BLUE : Color.WHITE, active ? BLUE : LINE);
    }

    private Button button(String label, int textColor, int fill, int stroke) {
        Button b = new Button(this);
        b.setAllCaps(false);
        b.setText(label);
        b.setTextColor(textColor);
        b.setTextSize(15);
        b.setBackground(round(fill, stroke, 1, 8));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, dp(52));
        params.setMargins(0, dp(10), 0, 0);
        b.setLayoutParams(params);
        return b;
    }

    private View menuButton(String label, Runnable action) {
        Button b = primary(label);
        b.setOnClickListener(v -> action.run());
        return b;
    }

    private EditText field(String hint, int inputType) {
        EditText edit = new EditText(this);
        edit.setHint(hint);
        edit.setInputType(inputType);
        edit.setSingleLine(false);
        edit.setMinHeight(dp(52));
        edit.setTextColor(TEXT);
        edit.setHintTextColor(Color.rgb(116, 132, 152));
        edit.setPadding(dp(12), 0, dp(12), 0);
        edit.setBackground(round(Color.rgb(250, 253, 255), LINE, 1, 8));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
        params.setMargins(0, dp(12), 0, 0);
        edit.setLayoutParams(params);
        return edit;
    }

    private LinearLayout row() {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER);
        return row;
    }

    private View stat(String label, String value) {
        LinearLayout stat = card();
        stat.setGravity(Gravity.CENTER);
        stat.addView(text(value, 25, BLUE, true));
        TextView small = text(label, 12, Color.rgb(88, 104, 125), false);
        small.setGravity(Gravity.CENTER);
        stat.addView(small);
        return stat;
    }

    private View empty(String message) {
        TextView tv = text(message, 15, Color.rgb(91, 106, 128), false);
        tv.setPadding(dp(12), dp(20), dp(12), dp(20));
        tv.setGravity(Gravity.CENTER);
        return tv;
    }

    private ScrollView scroll(View child) {
        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.addView(child);
        return scroll;
    }

    private View space(int dp) {
        Space s = new Space(this);
        s.setLayoutParams(new LinearLayout.LayoutParams(1, dp(dp)));
        return s;
    }

    private LinearLayout.LayoutParams weight() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, -2, 1);
        params.setMargins(dp(4), 0, dp(4), 0);
        return params;
    }

    private FrameLayout.LayoutParams centered() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-1, -2);
        params.gravity = Gravity.CENTER;
        return params;
    }

    private GradientDrawable round(int fill, int stroke, int strokeDp, int radiusDp) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(fill);
        d.setStroke(dp(strokeDp), stroke);
        d.setCornerRadius(dp(radiusDp));
        return d;
    }

    private GradientDrawable circle(int fill, int stroke, int strokeDp) {
        GradientDrawable d = round(fill, stroke, strokeDp, 80);
        d.setShape(GradientDrawable.OVAL);
        return d;
    }

    private GradientDrawable gradient(int start, int end, int radius) {
        GradientDrawable d = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{start, end});
        d.setCornerRadius(dp(radius));
        return d;
    }

    private String currentUidOrDemo() {
        return auth.getCurrentUser() == null ? activePatientId : auth.getCurrentUser().getUid();
    }

    private String value(DocumentSnapshot doc, String key, String fallback) {
        if (doc == null || doc.getString(key) == null) return fallback;
        return doc.getString(key);
    }

    private String safe(String text) {
        return text == null || text.trim().isEmpty() ? "Not available" : text;
    }

    private String today() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
    }

    private int amount(EditText editText) {
        try {
            return Integer.parseInt(editText.getText().toString().trim());
        } catch (Exception ignored) {
            return 0;
        }
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private Map<String, Object> mapOf(Object... values) {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < values.length - 1; i += 2) {
            map.put(String.valueOf(values[i]), values[i + 1]);
        }
        return map;
    }
}
