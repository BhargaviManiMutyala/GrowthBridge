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

public class StartupProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileDetailsActivity";

    private TextView userDetailsTextView, startupDetailsTextView;
    private Button addStartupButton;
    private FirebaseFirestore firestore;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);

        // Initialize views
        userDetailsTextView = findViewById(R.id.userDetailsTextView);
        startupDetailsTextView = findViewById(R.id.startupDetailsTextView);
        addStartupButton = findViewById(R.id.addStartupButton);

        firestore = FirebaseFirestore.getInstance();

        // Get the CIN from the intent
        String cin = getIntent().getStringExtra("CIN");
        if (cin == null || cin.isEmpty()) {
            Toast.makeText(this, "CIN not provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Fetch user details
        fetchUserDetails(cin);

        // Fetch startup details
        fetchStartupDetails(cin);

        // Handle Add Startup button click
        addStartupButton.setOnClickListener(v -> {
            Intent intent = new Intent(StartupProfileActivity.this, Startupdetails.class);
            intent.putExtra("CIN", cin); // Pass CIN to the next activity
            startActivity(intent);
        });
    }

    private void fetchUserDetails(String cin) {
        firestore.collection("users")
                .whereEqualTo("CIN", cin)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                        DocumentSnapshot document = task.getResult().getDocuments().get(0);
                        String name = document.getString("name");
                        String email = document.getString("email");
                        String phone = document.getString("phone");


                        String userDetails = "Name: " + name + "\n"
                                + "Email: " + email + "\n"
                                + "Phone: " + phone+"\n";
                        //Toast.makeText(this, ""+userDetails, Toast.LENGTH_SHORT).show();
                        userDetailsTextView.setText(userDetails);
                    } else {
                        Log.e(TAG, "User details not found", task.getException());
                        userDetailsTextView.setText("User details not found.");
                    }
                });
    }

    private void fetchStartupDetails(String cin) {
        firestore.collection("startup")
                .whereEqualTo("CIN", cin) // Query by CIN
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
                        Log.e(TAG, "Startup details not found for CIN: " + cin);
                        startupDetailsTextView.setText("Startup details not found.");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error fetching startup details by CIN", e);
                    startupDetailsTextView.setText("Error fetching startup details.");
                });
    }

    // Helper method to format a single startup's details
    // Helper method to format a single startup's details
    private String formatStartupDetails(DocumentSnapshot document) {
        String title = document.getString("title") != null ? document.getString("title") : "N/A";
        Double annualIncome = document.getDouble("annualIncome");
        String annualIncomeString = annualIncome != null ? String.valueOf(annualIncome) : "N/A";
        Double companyPeriod = document.getDouble("companyPeriod");
        String companyPeriodString = companyPeriod != null ? String.valueOf(companyPeriod) + " years" : "N/A";
        String category = document.getString("category") != null ? document.getString("category") : "N/A";
        String description = document.getString("description") != null ? document.getString("description") : "N/A";
        String patentId = document.getString("patentId") != null ? document.getString("patentId") : "N/A";

        return "Title: " + title + "\n"
                + "Annual Income: " + annualIncomeString + "\n"
                + "Category: " + category + "\n"
                + "Company Period: " + companyPeriodString + "\n"
                + "Description: " + description + "\n"
                + "Patent ID: " + patentId;
    }




}