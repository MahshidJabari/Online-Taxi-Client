package com.jabari.client.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.jabari.client.R;
import com.jabari.client.network.model.UnsuccessfulTravel;

import java.util.ArrayList;

public class UnsuccessfulAdapter extends
        RecyclerView.Adapter<UnsuccessfulAdapter.UnsuccessfulViewHolder> {
    private Context context;
    private RecyclerView recyclerView;
    private ArrayList<UnsuccessfulTravel> travelList;

    public UnsuccessfulAdapter(Context context, RecyclerView recyclerView, ArrayList<UnsuccessfulTravel> travels){

        this.context = context;
        this.recyclerView = recyclerView;
        this.travelList = travels;
    }
    @NonNull
    @Override
    public UnsuccessfulViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_unsuccessful,parent,false);
        return new UnsuccessfulAdapter.UnsuccessfulViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnsuccessfulViewHolder holder, int i) {

        holder.tv_date.setText(travelList.get(i).getDate());
        holder.tv_detail.setText(travelList.get(i).getDetail());
        holder.tv_payment_company.setText(travelList.get(i).getPayment_company());
        holder.tv_payment_type.setText(travelList.get(i).getPayment_way());
        holder.tv_take_back.setText(travelList.get(i).getTake_back());
        holder.tv_time.setText(travelList.get(i).getTime());
        holder.tv_payment_amount.setText(travelList.get(i).getTransfer_payment());
        if(!holder.switch_.isChecked()){
            holder.tv_phone.setText(travelList.get(i).getSender_location());
            holder.tv_phone.setCompoundDrawablesWithIntrinsicBounds(0,0,
                    R.drawable.ic_phone_in_talk_green_24dp,0);
            holder.tv_name.setText(travelList.get(i).getSender_name());
            holder.tv_name.setCompoundDrawablesWithIntrinsicBounds(0,0,
                    R.drawable.ic_person_green_24dp,0);
            holder.tv_location.setText(travelList.get(i).getSender_location());
            holder.tv_location.setCompoundDrawablesWithIntrinsicBounds(0,0,
                    R.drawable.ic_location_green,0);

        }
        else if(holder.switch_.isChecked()){
            holder.tv_phone.setText(travelList.get(i).getRecirer_phone());
            holder.tv_phone.setCompoundDrawablesWithIntrinsicBounds(0,0,
                    R.drawable.ic_phone_orange,0);
            holder.tv_name.setText(travelList.get(i).getReciever_name());
            holder.tv_name.setCompoundDrawablesWithIntrinsicBounds(0,0,
                    R.drawable.ic_person_orange,0);
            holder.tv_location.setText(travelList.get(i).getReciever_location());
            holder.tv_location.setCompoundDrawablesWithIntrinsicBounds(0,0,
                    R.drawable.ic_location_orange,0);

        }


    }

    @Override
    public int getItemCount() {
        return travelList.size();
    }

    public class UnsuccessfulViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_time,tv_date,tv_payment_type,tv_payment_amount,
                tv_payment_company,tv_location,tv_name,tv_phone,tv_detail,tv_take_back;
        private Button btn_delete,btn_retry;
        private com.suke.widget.SwitchButton switch_;

        public UnsuccessfulViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_time = itemView.findViewById(R.id.tv_time);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_payment_type = itemView.findViewById(R.id.tv_pay_type);
            tv_payment_amount = itemView.findViewById(R.id.pay_amount);
            tv_payment_company = itemView.findViewById(R.id.tv_payment_comp);
            tv_take_back = itemView.findViewById(R.id.tv_take_back);
            tv_location = itemView.findViewById(R.id.tv_location);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_phone = itemView.findViewById(R.id.tv_phone);
            tv_detail = itemView.findViewById(R.id.tv_detail);

            btn_delete = itemView.findViewById(R.id.btn_delete);
            btn_retry = itemView.findViewById(R.id.btn_retry);

            switch_ = itemView.findViewById(R.id.switch_destination_start);

        }
    }
}
