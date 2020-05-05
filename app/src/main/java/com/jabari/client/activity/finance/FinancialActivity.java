package com.jabari.client.activity.finance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jabari.client.R;
import com.jabari.client.activity.main.MainActivity;
import com.jabari.client.adapter.FinancialAdapter;
import com.jabari.client.controller.FinanceController;
import com.jabari.client.network.config.ApiInterface;
import com.jabari.client.network.model.Finance;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FinancialActivity extends AppCompatActivity {

    private TextView tv_code, tv_type, tv_cache, tv_date;
    private RecyclerView recycler_finance;
    private RecyclerView recyclerView;
    private FinancialAdapter adapter;
    private ArrayList<Finance> finances;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);

        setViews();
        getFinancialReport();
    }

    private void setViews() {
        tv_code = findViewById(R.id.tv_travel_code);
        tv_type = findViewById(R.id.tv_travel_type);
        tv_cache = findViewById(R.id.tv_travel_cache);
        tv_date = findViewById(R.id.tv_travel_date);
    }

    private void setUpRecyclerView(ArrayList<Finance> finances) {

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView = findViewById(R.id.recycler_finance);
        adapter = new FinancialAdapter(this, recyclerView, finances);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

    }

    private void getFinancialReport() {
        ApiInterface.reportCallBack reportCallBack = new ApiInterface.reportCallBack() {
            @Override
            public void onResponse(ArrayList<Finance> finances) {
                setUpRecyclerView(finances);

            }

            @Override
            public void onFailure(String error) {

            }
        };
        FinanceController financeController = new FinanceController(reportCallBack);
        financeController.getReport();
    }

    public void OnBackClick() {
        startActivity(new Intent(FinancialActivity.this, MainActivity.class));
    }
}
