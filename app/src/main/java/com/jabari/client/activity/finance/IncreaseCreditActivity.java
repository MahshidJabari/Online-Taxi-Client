package com.jabari.client.activity.finance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jabari.client.R;
import com.jabari.client.activity.main.MainActivity;
import com.jabari.client.activity.main.SendRequestActivity;
import com.jabari.client.custom.DigitConverter;
import com.jabari.client.custom.GlobalVariables;

public class IncreaseCreditActivity extends AppCompatActivity {

    private View view;
    private TextView tv_calculated;
    private Button btn_online_payment, btn_cache_payment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_increase_credit);

        setUpViews();
        cachePaymentOnClickListener();
        onlinePaymentOnClickListener();
    }

    private void setUpViews() {

        view = findViewById(R.id.view);
        btn_cache_payment = findViewById(R.id.btn_cache_payment);
        btn_online_payment = findViewById(R.id.btn_online_payment);
        view.bringToFront();
        tv_calculated = findViewById(R.id.tv_calculated);
        btn_online_payment.bringToFront();
        tv_calculated.setText(DigitConverter.convert(GlobalVariables.calculated));
    }

    private void cachePaymentOnClickListener() {
        btn_cache_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IncreaseCreditActivity.this, MainActivity.class));
            }
        });
    }

    private void onlinePaymentOnClickListener() {
        btn_online_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IncreaseCreditActivity.this, PaymentActivity.class));
            }
        });
    }

    public void OnBack(View view) {
        startActivity(new Intent(IncreaseCreditActivity.this, SendRequestActivity.class));
    }

}
