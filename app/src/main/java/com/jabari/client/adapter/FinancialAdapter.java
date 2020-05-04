package com.jabari.client.adapter;

import android.app.Presentation;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jabari.client.R;
import com.jabari.client.network.model.Finance;

import java.util.ArrayList;

public class FinancialAdapter extends RecyclerView.Adapter<FinancialAdapter.FinancialViewHolder> {
    private ArrayList<Finance> financeArrayList;
    private RecyclerView recyclerView;
    private Context context;


    public FinancialAdapter(Context context, RecyclerView recyclerView, ArrayList<Finance> finances) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.financeArrayList = finances;
    }

    @NonNull
    @Override
    public FinancialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_finance, parent, false);
        return new FinancialAdapter.FinancialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FinancialViewHolder holder, int i) {
        holder.tv_travel_code.setText(financeArrayList.get(i).getTravel_code());
        holder.tv_travel_cache.setText(financeArrayList.get(i).getTravel_cache());
        holder.tv_travel_type.setText(financeArrayList.get(i).getTravel_type());
        holder.tv_travel_date.setText(financeArrayList.get(i).getTravel_date());
    }

    @Override
    public int getItemCount() {
        return financeArrayList.size();
    }

    public class FinancialViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_travel_code, tv_travel_type, tv_travel_cache, tv_travel_date;

        public FinancialViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_travel_code = itemView.findViewById(R.id.tv_travel_code);
            tv_travel_type = itemView.findViewById(R.id.tv_travel_type);
            tv_travel_cache = itemView.findViewById(R.id.tv_travel_cache);
            tv_travel_date = itemView.findViewById(R.id.tv_travel_date);
        }
    }
}
