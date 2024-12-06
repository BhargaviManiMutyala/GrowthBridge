package com.shrivecw.investandgrow;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InvestorAdapter extends RecyclerView.Adapter<InvestorAdapter.InvestorViewHolder> {

    private Context context;
    private List<Investor> investorList;

    public InvestorAdapter(Context context, List<Investor> investorList) {
        this.context = context;
        this.investorList = investorList;
    }

    @NonNull
    @Override
    public InvestorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_iteminvestor, parent, false);
        return new InvestorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InvestorViewHolder holder, int position) {
        Investor investor = investorList.get(position);
        holder.investedIn.setText("Invested In: " + investor.getInvestedIn());
        holder.description.setText("Description: " + investor.getDescription());
        holder.interest.setText("Interest: " + investor.getInterest());

        /*holder.bankDocument.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(investor.getBankDocumentUri()));
            context.startActivity(intent);
        });

        holder.taxDocument.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(investor.getTaxDocumentUri()));
            context.startActivity(intent);
        });*/
    }

    @Override
    public int getItemCount() {
        return investorList.size();
    }

    static class InvestorViewHolder extends RecyclerView.ViewHolder {
        TextView investedIn, description, interest, bankDocument, taxDocument;

        public InvestorViewHolder(@NonNull View itemView) {
            super(itemView);
            investedIn = itemView.findViewById(R.id.investedIn);
            description = itemView.findViewById(R.id.description);
            interest = itemView.findViewById(R.id.interest);
            /*bankDocument = itemView.findViewById(R.id.bankDocument);
            taxDocument = itemView.findViewById(R.id.taxDocument);*/
        }
    }
}
