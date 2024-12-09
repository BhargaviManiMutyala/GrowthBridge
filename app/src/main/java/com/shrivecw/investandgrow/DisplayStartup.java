package com.shrivecw.investandgrow;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class DisplayStartup extends AppCompatActivity {
    String email;
    private static final String TAG = "DisplayStartups";
    private RecyclerView recyclerView;
    private TextView noData;
    private StartupAdapter adapter;
    private List<Startup> startupList = new ArrayList<>();
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaystartups);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        recyclerView = findViewById(R.id.recyclerView);
        noData = findViewById(R.id.noData);
        firestore = FirebaseFirestore.getInstance();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StartupAdapter(this, startupList);
        recyclerView.setAdapter(adapter);

        fetchStartups();
    }

    private void fetchStartups() {
        firestore.collection("startup")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        QuerySnapshot snapshot = task.getResult();
                        if (!snapshot.isEmpty()) {
                            for (DocumentSnapshot document : snapshot) {
                                Startup startup = document.toObject(Startup.class);
                                startupList.add(startup);
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
