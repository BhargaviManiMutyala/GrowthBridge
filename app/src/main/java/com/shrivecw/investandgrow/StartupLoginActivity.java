package com.shrivecw.investandgrow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class StartupLoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        // Initialize Firestore and FirebaseAuth
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    // Login method
    public void goToMain(View view) {
        String cin = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (cin.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Authenticate using Firestore
        db.collection("users") // Replace "users" with your collection name
                .whereEqualTo("CIN", cin)
                .whereEqualTo("password", password)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                        // Login successful, navigate to MainActivity
                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, DisplayInvestors.class);
                        intent.putExtra("email", cin); // Pass the email as an extra
                        startActivity(intent);
                    } else {
                        // Login failed
                        Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    public void goToRegister(View view) {
        Intent intent = new Intent(this, StartupRegister.class);
        startActivity(intent);
    }
}
