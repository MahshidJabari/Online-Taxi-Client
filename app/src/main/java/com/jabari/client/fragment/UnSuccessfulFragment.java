package com.jabari.client.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jabari.client.R;
import com.jabari.client.adapter.UnsuccessfulAdapter;
import com.jabari.client.network.model.UnsuccessfulTravel;

import java.util.ArrayList;

public class UnSuccessfulFragment extends Fragment {
    private RecyclerView recyclerView;
    private UnsuccessfulAdapter adapter;
    private ArrayList<UnsuccessfulTravel> travels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_unsuccessful, container, false);
        recyclerView = view.findViewById(R.id.unsuccessful_recycler);

        UnsuccessfulTravel travel = new UnsuccessfulTravel();
        travel.setDetail("لطفا سریعتر به دستم برسد!");
        travel.setPayment_company("مبدا");
        travel.setPayment_way("نقدی");
        travel.setTake_back("دارد");
        travel.setTime("16:22");
        travel.setTransfer_payment("3000");

        travels = new ArrayList<>();
        travels.add(travel);
        travels.add(travel);

        setUpRecyclerView(travels, recyclerView);

        return view;

    }

    private void setUpRecyclerView(ArrayList<UnsuccessfulTravel> travels, RecyclerView recyclerView) {
        LinearLayoutManager manager = new LinearLayoutManager
                (this.getContext(), LinearLayoutManager.HORIZONTAL, false);

        adapter = new UnsuccessfulAdapter(this.getContext(), recyclerView, travels);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);


    }

}
