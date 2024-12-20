package com.shrivecw.investandgrow;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class StartupRegister extends AppCompatActivity {

    private EditText etName, etEmail, etPhone, etPassword, etConfirmPassword, etCIN;
    private Button btnRegister;
    private TextView tvLogin;

    // Firestore instance
    private FirebaseFirestore db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startupregister);

        // Initialize views
        etCIN = findViewById(R.id.et_CIN);
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        btnRegister = findViewById(R.id.btn_register);
        tvLogin = findViewById(R.id.tv_login);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Handle Register Button Click
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cin = etCIN.getText().toString().trim();
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();

                if (cin.isEmpty() || name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(StartupRegister.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(StartupRegister.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    // Save user data to Firestore
                    saveUserToFirestore(cin, name, email, phone, password);
                }
            }
        });

        // Handle "Already Registered? Login" Click
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to LoginActivity
                Intent intent = new Intent(StartupRegister.this, StartupLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveUserToFirestore(String cin, String name, String email, String phone, String password) {
        Map<String, Object> user = new HashMap<>();
        user.put("CIN", cin);
        user.put("name", name);
        user.put("email", email);
        user.put("phone", phone);
        user.put("password", password);

        db.collection("users")
                .add(user)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(StartupRegister.this, "User registered successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(StartupRegister.this, Startupdetails.class);
                    intent.putExtra("CIN",cin);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(StartupRegister.this, "Failed to register user: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }
}