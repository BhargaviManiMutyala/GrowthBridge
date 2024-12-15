package com.shrivecw.investandgrow;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class Startupdetails extends AppCompatActivity {

    String cin;

    private EditText editTitle, editDescription, editCompanyPeriod, editAnnualIncome, editCategory, editPatent;
    private Button btnSubmit;

    private FirebaseFirestore db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startupdetails);
        Intent intent = getIntent();
        cin = intent.getStringExtra("CIN");
        // Initialize Views
        editPatent = findViewById(R.id.editPatent);
        editTitle = findViewById(R.id.editTitle);
        editCategory = findViewById(R.id.editCategory);
        editDescription = findViewById(R.id.editDescription);
        editCompanyPeriod = findViewById(R.id.editCompanyPeriod);
        editAnnualIncome = findViewById(R.id.editAnnualIncome);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Set up Submit Button
        btnSubmit.setOnClickListener(v -> submitForm());
    }



    private void submitForm() {
        String patentID = editPatent.getText().toString().trim();
        String title = editTitle.getText().toString().trim();
        String category = editCategory.getText().toString().trim();
        String description = editDescription.getText().toString().trim();
        String companyPeriodStr = editCompanyPeriod.getText().toString().trim();
        String annualIncomeStr = editAnnualIncome.getText().toString().trim();

        if (patentID.isEmpty() || title.isEmpty() || category.isEmpty() || description.isEmpty() || companyPeriodStr.isEmpty() || annualIncomeStr.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int companyPeriod;
        try {
            companyPeriod = Integer.parseInt(companyPeriodStr);
            if (companyPeriod < 0 || companyPeriod > 9) {
                Toast.makeText(this, "Company period must be between 0 and 9 years", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid company period", Toast.LENGTH_SHORT).show();
            return;
        }

        double annualIncome;
        try {
            annualIncome = Double.parseDouble(annualIncomeStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid annual income", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a data map
        Map<String, Object> startupData = new HashMap<>();
        startupData.put("CIN",cin);
        startupData.put("patentId",patentID);
        startupData.put("title", title);
        startupData.put("category", category);
        startupData.put("description", description);
        startupData.put("companyPeriod", companyPeriod);
        startupData.put("annualIncome", annualIncome);

        // Save to Firestore
        db.collection("startup")
                .add(startupData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Startup details saved successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Startupdetails.this, DisplayInvestors.class);
                    intent.putExtra("CIN", cin);
                    startActivity(intent);
                    finish(); // Close the activity or navigate to another screen
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to save startup details: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
