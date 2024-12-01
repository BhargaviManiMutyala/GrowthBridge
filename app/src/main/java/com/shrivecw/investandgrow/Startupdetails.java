package com.shrivecw.investandgrow;


import androidx.appcompat.app.AppCompatActivity;

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

public class Startupdetails extends AppCompatActivity {

    private static final int PICK_BANK_STATEMENT_REQUEST = 1;
    private static final int PICK_PATENT_DOCUMENT_REQUEST = 2;

    private Uri bankStatementUri = null;
    private Uri patentDocumentUri = null;

    private EditText editTitle, editDescription, editCompanyPeriod, editAnnualIncome, editCategory;
    private Button btnUploadBankStatement, btnUploadPatentDocument, btnSubmit;
    private TextView textBankStatement, textPatentDocument;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startupdetails);

        // Initialize Views
        editTitle = findViewById(R.id.editTitle);
        editCategory = findViewById(R.id.editCategory);
        editDescription = findViewById(R.id.editDescription);
        editCompanyPeriod = findViewById(R.id.editCompanyPeriod);
        editAnnualIncome = findViewById(R.id.editAnnualIncome);
        btnUploadBankStatement = findViewById(R.id.btnUploadBankStatement);
        btnUploadPatentDocument = findViewById(R.id.btnUploadPatentDocument);
        btnSubmit = findViewById(R.id.btnSubmit);
        textBankStatement = findViewById(R.id.textBankStatement);
        textPatentDocument = findViewById(R.id.textPatentDocument);

        // Set up Upload Buttons
        btnUploadBankStatement.setOnClickListener(v -> pickDocument(PICK_BANK_STATEMENT_REQUEST));
        btnUploadPatentDocument.setOnClickListener(v -> pickDocument(PICK_PATENT_DOCUMENT_REQUEST));

        // Set up Submit Button
        btnSubmit.setOnClickListener(v -> submitForm());
    }

    private void pickDocument(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*"); // Accept all file types
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            String fileName = getFileName(uri);

            if (fileName != null) {
                if (requestCode == PICK_BANK_STATEMENT_REQUEST) {
                    bankStatementUri = uri;
                    textBankStatement.setText("Uploaded: " + fileName); // Update TextView
                    Toast.makeText(this, "Bank Statement Uploaded", Toast.LENGTH_SHORT).show();
                } else if (requestCode == PICK_PATENT_DOCUMENT_REQUEST) {
                    patentDocumentUri = uri;
                    textPatentDocument.setText("Uploaded: " + fileName); // Update TextView
                    Toast.makeText(this, "Patent Document Uploaded", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Failed to retrieve file name", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getFileName(Uri uri) {
        String fileName = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    fileName = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        return fileName != null ? fileName : uri.getLastPathSegment();
    }

    private void submitForm() {
        // Retrieve user input
        String title = editTitle.getText().toString().trim();
        String category = editCategory.getText().toString().trim();
        String description = editDescription.getText().toString().trim();
        String companyPeriodStr = editCompanyPeriod.getText().toString().trim();
        String annualIncomeStr = editAnnualIncome.getText().toString().trim();

        // Validate input
        if (title.isEmpty() || category.isEmpty() || description.isEmpty() || companyPeriodStr.isEmpty() || annualIncomeStr.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (bankStatementUri == null) {
            Toast.makeText(this, "Please upload a bank statement", Toast.LENGTH_SHORT).show();
            return;
        }

        if (patentDocumentUri == null) {
            Toast.makeText(this, "Please upload a patent document", Toast.LENGTH_SHORT).show();
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

        // Success
        Toast.makeText(this, "Form Submitted Successfully!\nTitle: " + title + "\nCategory: " + category, Toast.LENGTH_LONG).show();
    }
}
