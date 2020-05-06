package com.jabari.client.activity.report;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jabari.client.R;
import com.jabari.client.activity.main.MainActivity;
import com.jabari.client.adapter.ArchiveAdapter;
import com.jabari.client.controller.HistoryController;
import com.jabari.client.custom.DigitConverter;
import com.jabari.client.custom.ExceptionHandler;
import com.jabari.client.network.config.ApiInterface;
import com.jabari.client.network.model.Travel;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ArchiveActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArchiveAdapter adapter;
    private TextView tv_successful_travel, tv_payments;
    private ExceptionHandler handler;
    private ArrayList<Travel> travelList;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);
        handler = new ExceptionHandler(this);

        setUpView();
        getSuccessFulTravel();
        getTravelList();

    }

    private void setUpView() {
        tv_payments = findViewById(R.id.tv_payments);
        tv_successful_travel = findViewById(R.id.tv_successful_travel);
    }

    public void OnBackClick(View view) {

        startActivity(new Intent(ArchiveActivity.this, MainActivity.class));
    }

    private void getSuccessFulTravel() {

        ApiInterface.requestSuccessCallback requestSuccessCallback = new ApiInterface.requestSuccessCallback() {
            @Override
            public void onResponse(String succeedRequests, String payments) {

                tv_successful_travel.setText(DigitConverter.convert(succeedRequests));
                tv_payments.setText(DigitConverter.convert(payments));

            }

            @Override
            public void onFailure(String error) {
                handler.generateError(error);
            }
        };
        HistoryController historyController = new HistoryController(requestSuccessCallback);
        historyController.getSuccess();
    }

    private void getTravelList() {
        ApiInterface.requestsReportCallBack reportCallBack = new ApiInterface.requestsReportCallBack() {
            @Override
            public void onResponse(ArrayList<Travel> requests) {
                setUpRecyclerView(requests);
            }

            @Override
            public void onFailure(String error) {

            }
        };
        HistoryController historyController = new HistoryController(reportCallBack);
        historyController.getRequestDetail();
    }

    private void setUpRecyclerView(final ArrayList<Travel> travelList) {

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.archive_recycler);
        adapter = new ArchiveAdapter(this, recyclerView, travelList, new ArchiveAdapter.ArchiveAdapterListener() {
            @Override
            public void detailOnClick(View v, int position) {
                Intent intent = new Intent(ArchiveActivity.this,ArchiveDetailActivity.class);
                intent.putExtra("travel",travelList.get(position));
                startActivity(intent);
            }

            @Override
            public void requestOnClick(View v, int position) {

            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

    }
}
