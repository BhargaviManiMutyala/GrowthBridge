package com.shrivecw.investandgrow;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private Button startup, investor;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        startup = findViewById(R.id.startup);
        investor = findViewById(R.id.investor);
        investor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(HomePage.this, "Action 2 Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Investordetails.class);
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
            }
        });
        startup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(HomePage.this, "Action 2 Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Startupdetails.class);
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
            }
        });
    }
}
