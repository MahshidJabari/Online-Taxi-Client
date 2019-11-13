package com.jabari.client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jabari.client.R;
import com.jabari.client.adapter.ArchiveAdapter;
import com.jabari.client.network.model.Travel;

import java.util.ArrayList;

public class ArchiveActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArchiveAdapter adapter;
    private ArrayList<Travel> travels;
    private TextView tv_return;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);


        setUpView();

        Travel travel = new Travel();
        travel.setDate("1397/8/22");
        travel.setSender_name("محمدرضا اکبری");
        travel.setSender_location("ولیعصر،میدان ولیعصر،پلاک 2،واحد5");
        travel.setSender_phone("0990 537 4633");
        travel.setReciever_location("ولیعصر،میدان ولیعصر،پلاک 3،واحد5");
        travel.setReciever_name("محمدرضا اکبری");
        travel.setRecirer_phone("0912 421 7230");

        travels = new ArrayList<>();
        travels.add(travel);
        travels.add(travel);
        setUpRecyclerView(travels);
    }

    private void setUpView() {

        tv_return = findViewById(R.id.tv_return);
        tv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ArchiveActivity.this, MainActivity.class));
            }
        });
    }

    private void setUpRecyclerView(ArrayList<Travel> travelList) {

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView = findViewById(R.id.archive_recycler);
        adapter = new ArchiveAdapter(this, recyclerView, travelList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

    }
}
