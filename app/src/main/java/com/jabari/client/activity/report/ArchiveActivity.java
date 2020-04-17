package com.jabari.client.activity.report;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jabari.client.R;
import com.jabari.client.activity.main.MainActivity;
import com.jabari.client.adapter.ArchiveAdapter;
import com.jabari.client.network.model.Travel;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ArchiveActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArchiveAdapter adapter;
    private ArrayList<Travel> travels;
    private TextView tv_return;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);


        setUpView();

        Travel travel = new Travel();
        travel.setDate("1397/8/22");
        travel.setSender_location("ولیعصر،میدان ولیعصر،پلاک 2،واحد5");
        travel.setReciever_location("ولیعصر،میدان ولیعصر،پلاک 3،واحد5");
        /*travel.setSender_name("محمدرضا اکبری");
        travel.setSender_phone("0990 537 4633");
        travel.setReciever_name("محمدرضا اکبری");
        travel.setRecirer_phone("0912 421 7230");
*/
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

    private void getTravelList(){

    }

    private void setUpRecyclerView(ArrayList<Travel> travelList) {

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView = findViewById(R.id.archive_recycler);
        adapter = new ArchiveAdapter(this, recyclerView, travelList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

    }
}
