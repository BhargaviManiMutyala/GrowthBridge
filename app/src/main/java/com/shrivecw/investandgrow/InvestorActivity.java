package com.shrivecw.investandgrow;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class InvestorActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_investor);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void goToLogin(View view) {
        Intent intent = new Intent(InvestorActivity.this,InvestorLoginActivity.class);
        startActivity(intent);
    }
    public void goToRegister(View view) {
        Intent intent = new Intent(InvestorActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
    public void goToStartup(View view) {
        Intent intent = new Intent(InvestorActivity.this,StartupActivity.class);
        startActivity(intent);
    }
    public void goToHome(View view) {
        Intent intent = new Intent(InvestorActivity.this,HomeActivity.class);
        startActivity(intent);
    }
    public void goToAbout(View view) {
        Intent intent = new Intent(InvestorActivity.this, AboutActivity.class);
        startActivity(intent);
    }

}