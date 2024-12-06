package com.shrivecw.investandgrow;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Investordetails extends AppCompatActivity {
    String email;
    private static final int PICK_IMAGE_REQUEST_BANK = 1;
    private static final int PICK_IMAGE_REQUEST_TAX = 2;
    private static final String TAG = "Investordetails";

    private Uri imageUriBank;
    private Uri imageUriTax;

    private TextView investedIn;
    private EditText editInvestedIn;
    private TextView describe;
    private EditText editDescribe;
    private TextView interest;
    private EditText editInterest;
    private TextView bank;
    private Button uploadBank;
    private TextView imageBank;
    private TextView tax;
    private Button uploadTax;
    private TextView imageTax;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investordetails);

        // Initialize Views
        investedIn = findViewById(R.id.investedIn);
        editInvestedIn = findViewById(R.id.editInvestedIn);
        describe = findViewById(R.id.describe);
        editDescribe = findViewById(R.id.editDescribe);
        interest = findViewById(R.id.interest);
        editInterest = findViewById(R.id.editInterest);
        bank = findViewById(R.id.bank);
        uploadBank = findViewById(R.id.uploadBank);
        imageBank = findViewById(R.id.imageBank);
        tax = findViewById(R.id.tax);
        uploadTax = findViewById(R.id.uploadTax);
        imageTax = findViewById(R.id.imageTax);
        submit = findViewById(R.id.submit);

        // Set Upload Listeners
        uploadBank.setOnClickListener(v -> selectFile(PICK_IMAGE_REQUEST_BANK));
        uploadTax.setOnClickListener(v -> selectFile(PICK_IMAGE_REQUEST_TAX));

        // Set Submit Listener
        submit.setOnClickListener(v -> {
            String investedInText = editInvestedIn.getText().toString();
            String describeText = editDescribe.getText().toString();
            String interestText = editInterest.getText().toString();

            // Log and display entered information (placeholder for further logic)
            Log.d(TAG, "Invested In: " + investedInText);
            Log.d(TAG, "Description: " + describeText);
            Log.d(TAG, "Interest: " + interestText);

            Toast.makeText(this, "Submitted Successfully!", Toast.LENGTH_SHORT).show();
        });
    }

    private void selectFile(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"), requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri fileUri = data.getData();
            String fileName = getFileName(fileUri);

            if (requestCode == PICK_IMAGE_REQUEST_BANK) {
                imageUriBank = fileUri;
                imageBank.setText(fileName != null ? fileName : "File Selected");
            } else if (requestCode == PICK_IMAGE_REQUEST_TAX) {
                imageUriTax = fileUri;
                imageTax.setText(fileName != null ? fileName : "File Selected");
            }
        }
    }

    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String fileName = null;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            if (cursor.moveToFirst()) {
                fileName = cursor.getString(nameIndex);
            }
            cursor.close();
        }
        return fileName;
    }
}
