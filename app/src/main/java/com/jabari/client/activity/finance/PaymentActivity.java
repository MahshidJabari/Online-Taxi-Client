package com.jabari.client.activity.finance;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jabari.client.R;
import com.jabari.client.controller.RequestController;
import com.jabari.client.custom.DigitConverter;
import com.jabari.client.custom.GlobalVariables;
import com.jabari.client.network.config.ApiInterface;
import com.zarinpal.ewallets.purchase.ZarinPal;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PaymentActivity extends AppCompatActivity {

    private TextView tv_payment_cache;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        setView();
    }

    private void setView() {
        tv_payment_cache = findViewById(R.id.tv_payment_cache);
        tv_payment_cache.setText(DigitConverter.convert(GlobalVariables.calculated));

    }

    public void OnPaymentClick(View view) {
        ApiInterface.payCallback payCallback = new ApiInterface.payCallback() {
            @Override
            public void onResponse(String url) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }

            @Override
            public void onFailure(String error) {

            }
        };
        RequestController requestController = new RequestController(payCallback);
        requestController.pay("100");

    }

    public void OnBack(View view) {
        startActivity(new Intent(PaymentActivity.this, IncreaseCreditActivity.class));
    }

}