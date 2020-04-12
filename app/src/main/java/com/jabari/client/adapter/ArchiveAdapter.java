package com.jabari.client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jabari.client.R;
import com.jabari.client.network.model.Travel;

import java.util.ArrayList;


public class ArchiveAdapter extends RecyclerView.Adapter<ArchiveAdapter.ArchiveViewHolder> {

    private Context context;
    private RecyclerView recyclerView;
    private ArrayList<Travel> travelList;


    public ArchiveAdapter(Context context, RecyclerView recyclerView, ArrayList<Travel> travels){

        this.context = context;
        this.recyclerView = recyclerView;
        this.travelList = travels;
    }
    @NonNull

    @Override
    public ArchiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_archive,parent,false);
        return new ArchiveAdapter.ArchiveViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ArchiveViewHolder holder, int i) {


        holder.tv_date.setText(travelList.get(i).getDate());
        holder.tv_sender.setText(travelList.get(i).getSender_name());
        holder.tv_start_location.setText(travelList.get(i).getSender_location());
        holder.tv_receiver.setText(travelList.get(i).getReciever_name());
        holder.tv_receiver_loc.setText(travelList.get(i).getReciever_location());
    }

    @Override
    public int getItemCount() {
        return travelList.size();
    }

    public class ArchiveViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_date,tv_start_location,tv_sender,tv_receiver_loc,tv_receiver;
        public ArchiveViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_start_location = itemView.findViewById(R.id.tv_start_location);
            tv_sender = itemView.findViewById(R.id.tv_sender_name);
            tv_receiver_loc = itemView.findViewById(R.id.tv_end_location);
            tv_receiver = itemView.findViewById(R.id.tv_receiver_name);
                  }
    }
}
