package com.shrivecw.investandgrow;



import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ForgotInvestor extends AppCompatActivity {

    private EditText emailEditText, newPasswordEditText, confirmPasswordEditText;
    private Button resetButton;
    private FirebaseFirestore db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotinvestor);

        // Initialize views
        emailEditText = findViewById(R.id.etemail);
        newPasswordEditText = findViewById(R.id.etreset);
        confirmPasswordEditText = findViewById(R.id.etconfirm);
        resetButton = findViewById(R.id.button);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Set up button click listener
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String newPassword = newPasswordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                if (email.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(ForgotInvestor.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(ForgotInvestor.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Query Firestore to find the document with the matching CIN
                db.collection("users")
                        .whereEqualTo("email", email)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                                // Update the password field in Firestore
                                String documentId = task.getResult().getDocuments().get(0).getId();
                                db.collection("users").document(documentId)
                                        .update("password", newPassword)
                                        .addOnSuccessListener(aVoid -> {
                                            Toast.makeText(ForgotInvestor.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                                            finish(); // Close the activity after successful update
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(ForgotInvestor.this, "Error updating password: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        });
                            } else {
                                Toast.makeText(ForgotInvestor.this, "CIN not found", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(ForgotInvestor.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }
}