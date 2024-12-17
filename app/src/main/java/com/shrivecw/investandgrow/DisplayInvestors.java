package com.shrivecw.investandgrow;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class DisplayInvestors extends AppCompatActivity {
    String cin;
    private static final String TAG = "DisplayInvestors";
    private RecyclerView recyclerView;
    private TextView noData;
    private InvestorAdapter adapter;
    private List<Investor> investorList = new ArrayList<>();
    private FirebaseFirestore firestore;
    Button profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayinvestors);
        Intent intent = getIntent();
        cin = intent.getStringExtra("CIN");
        recyclerView = findViewById(R.id.recyclerView);
        noData = findViewById(R.id.noData);
        firestore = FirebaseFirestore.getInstance();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new InvestorAdapter(this, investorList);
        recyclerView.setAdapter(adapter);
        profileButton = findViewById(R.id.viewProfileButton);
        profileButton.setOnClickListener(view -> {
            Intent intent1 = new Intent(this,StartupProfileActivity.class);
            intent1.putExtra("CIN", cin);
            startActivity(intent1);
            finish();
        });
        fetchInvestors();
    }

    private void fetchInvestors() {
        firestore.collection("investor")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        QuerySnapshot snapshot = task.getResult();
                        if (!snapshot.isEmpty()) {
                            for (DocumentSnapshot document : snapshot) {
                                Investor investor = document.toObject(Investor.class);
                                investorList.add(investor);
                            }
                            adapter.notifyDataSetChanged();
                            recyclerView.setVisibility(View.VISIBLE);
                        } else {
                            noData.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Log.e(TAG, "Error fetching data", task.getException());
                        noData.setVisibility(View.VISIBLE);
                    }
                });
    }

}
