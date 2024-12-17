package com.shrivecw.investandgrow;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class InvestorProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileDetailsActivity";

    private TextView userDetailsTextView, startupDetailsTextView;
    private FirebaseFirestore firestore;
    Button addprojectButton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);

        // Initialize views
        userDetailsTextView = findViewById(R.id.userDetailsTextView);
        startupDetailsTextView = findViewById(R.id.startupDetailsTextView);
        addprojectButton = findViewById(R.id.addStartupButton);
        firestore = FirebaseFirestore.getInstance();

        // Get the CIN from the intent
        String email = getIntent().getStringExtra("email");
        if (email == null || email.isEmpty()) {
            Toast.makeText(this, "Email not provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Fetch user details
        fetchUserDetails(email);

        // Fetch startup details
        fetchProjectDetails(email);

        addprojectButton.setOnClickListener(v -> {
            Intent intent = new Intent(InvestorProfileActivity.this, Investordetails.class);
            intent.putExtra("email", email); // Pass CIN to the next activity
            startActivity(intent);
        });
    }

    private void fetchUserDetails(String email) {
        firestore.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                        DocumentSnapshot document = task.getResult().getDocuments().get(0);
                        String name = document.getString("name");
                        String email1 = document.getString("email");
                        String phone = document.getString("phone");


                        String userDetails = "Name: " + name + "\n"
                                + "Email: " + email1 + "\n"
                                + "Phone: " + phone+"\n";
                        //Toast.makeText(this, ""+userDetails, Toast.LENGTH_SHORT).show();
                        userDetailsTextView.setText(userDetails);
                    } else {
                        Log.e(TAG, "User details not found", task.getException());
                        userDetailsTextView.setText("User details not found.");
                    }
                });
    }

    private void fetchProjectDetails(String email) {
        firestore.collection("investor")
                .whereEqualTo("email", email) // Query by CIN
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                        // Iterate through all matching documents
                        StringBuilder allStartupDetails = new StringBuilder();
                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
                            // Append each document's details to the StringBuilder
                            allStartupDetails.append(formatStartupDetails(document)).append("\n\n");
                        }

                        // Display all startup details in the TextView
                        startupDetailsTextView.setText(allStartupDetails.toString());
                    } else {
                        // No CIN match found
                        Log.e(TAG, "Startup details not found for email: " + email);
                        startupDetailsTextView.setText("Project details not found.");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error fetching startup details by email", e);
                    startupDetailsTextView.setText("Error fetching project details.");
                });
    }

    // Helper method to format a single startup's details
    // Helper method to format a single startup's details
    private String formatStartupDetails(DocumentSnapshot document) {
        String investedIn = document.getString("investedIn") != null ? document.getString("investedIn") : "N/A";
        String description = document.getString("description") != null ? document.getString("description") : "N/A";
        String interest = document.getString("interest") != null ? document.getString("interest") : "N/A";

        return "You Invested in: " + investedIn + "\n"
                + "Description: " + description + "\n"
                + "Your interests: " + interest;
    }




}