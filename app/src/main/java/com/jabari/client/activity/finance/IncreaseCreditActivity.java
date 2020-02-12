package com.jabari.client.activity.finance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jabari.client.R;

public class IncreaseCreditActivity extends AppCompatActivity {

    private View view;
    private Button btn_online_payment,btn_cache_payment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_increase_credit);

        setUpViews();
    }

    private void setUpViews(){

        view = findViewById(R.id.view);
        btn_cache_payment = findViewById(R.id.btn_cache_payment);
        btn_online_payment = findViewById(R.id.btn_online_payment);
        view.bringToFront();
        btn_online_payment.bringToFront();
    }
}
