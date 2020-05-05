package com.jabari.client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jabari.client.R;
import com.jabari.client.custom.DigitConverter;
import com.jabari.client.network.model.Request;

import java.util.ArrayList;


public class ArchiveAdapter extends RecyclerView.Adapter<ArchiveAdapter.ArchiveViewHolder> {

    private Context context;
    private RecyclerView recyclerView;
    private ArrayList<Request> travelList;


    public ArchiveAdapter(Context context, RecyclerView recyclerView, ArrayList<Request> travels) {

        this.context = context;
        this.recyclerView = recyclerView;
        this.travelList = travels;
    }

    @NonNull

    @Override
    public ArchiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_archive, parent, false);
        return new ArchiveAdapter.ArchiveViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ArchiveViewHolder holder, int i) {


        holder.tv_date.setText(DigitConverter.convert(travelList.get(i).getCreatedAt()));
        holder.tv_start_location.setText(travelList.get(i).getLocationAddress());
        holder.tv_receiver_loc.setText(travelList.get(i).getDestinationAddress());
        holder.tv_travel_code.setText(DigitConverter.convert(travelList.get(i).getId()));

    }

    @Override
    public int getItemCount() {
        return travelList.size();
    }

    public class ArchiveViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_date, tv_start_location, tv_receiver_loc, tv_sender, tv_receiver, tv_travel_code;
        private Button btn_request, btn_details;

        public ArchiveViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_start_location = itemView.findViewById(R.id.tv_start_location);
            tv_receiver_loc = itemView.findViewById(R.id.tv_end_location);
            btn_request = itemView.findViewById(R.id.btn_request);
            btn_details = itemView.findViewById(R.id.btn_travel_details);
            tv_travel_code = itemView.findViewById(R.id.tv_travel_code);
            /*tv_sender = itemView.findViewById(R.id.tv_sender_name);
            tv_receiver = itemView.findViewById(R.id.tv_receiver_name);*/
        }
    }
}
