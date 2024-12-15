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

public class StartupActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_startup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void goToLogin(View view) {
        Intent intent = new Intent(StartupActivity.this,StartupLoginActivity.class);
        startActivity(intent);
    }
    public void goToRegister(View view) {
        Intent intent = new Intent(StartupActivity.this,StartupRegister.class);
        startActivity(intent);
    }
    public void goToHome(View view) {
        Intent intent = new Intent(StartupActivity.this, HomeActivity.class);
        startActivity(intent);
    }
    public void goToInvestor(View view) {
        Intent intent = new Intent(StartupActivity.this,InvestorActivity.class);
        startActivity(intent);
    }
    public void goToAbout(View view) {
        Intent intent = new Intent(StartupActivity.this,AboutActivity.class);
        startActivity(intent);
    }
}