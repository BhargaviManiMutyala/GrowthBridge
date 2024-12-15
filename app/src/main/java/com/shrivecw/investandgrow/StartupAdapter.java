package com.shrivecw.investandgrow;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StartupAdapter extends RecyclerView.Adapter<StartupAdapter.StartupViewHolder> {

    private Context context;
    private List<Startup> startupList;

    public StartupAdapter(Context context, List<Startup> startupList) {
        this.context = context;
        this.startupList = startupList;
    }

    @NonNull
    @Override
    public StartupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_itemstartup, parent, false);
        return new StartupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StartupViewHolder holder, int position) {
        Startup startup = startupList.get(position);
        holder.title.setText("Title: " + startup.getTitle());
        holder.category.setText("Category: " + startup.getCategory());
        holder.description.setText("Description: " + startup.getDescription());
        holder.companyPeriod.setText("Company Period: " + startup.getCompanyPeriod() + " years");
        holder.annualIncome.setText("Annual Income: $" + startup.getAnnualIncome());
        holder.messageButton.setOnClickListener(view -> {
            Intent intent = new Intent(context, MessageActivity.class);
            intent.putExtra("investorName", startup.getTitle());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return startupList.size();
    }

    static class StartupViewHolder extends RecyclerView.ViewHolder {
        TextView title, category, description, companyPeriod, annualIncome, messageButton;

        public StartupViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            category = itemView.findViewById(R.id.category);
            description = itemView.findViewById(R.id.description);
            companyPeriod = itemView.findViewById(R.id.companyPeriod);
            annualIncome = itemView.findViewById(R.id.annualIncome);
            messageButton = itemView.findViewById(R.id.actionButton);
        }
    }
}
