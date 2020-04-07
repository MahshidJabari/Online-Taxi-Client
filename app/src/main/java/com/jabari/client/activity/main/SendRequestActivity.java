package com.jabari.client.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jabari.client.R;
import com.jabari.client.activity.finance.GiftCardActivity;
import com.jabari.client.activity.finance.IncreaseCreditActivity;
import com.jabari.client.controller.RequestController;
import com.jabari.client.custom.GlobalVariables;
import com.jabari.client.entities.Request;
import com.jabari.client.network.config.ApiInterface;

public class SendRequestActivity extends AppCompatActivity {

    private EditText et_start_location, et_destination;
    private TextView tv_payment_way, tv_vehicle_name, tv_payment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_request);

        setViews();
    }

    public void onDetailClicked(View view) {
        startActivity(new Intent(SendRequestActivity.this, DetailsActivity.class));
    }

    public void setViews() {
        et_start_location = findViewById(R.id.et_start_loc);
        et_destination = findViewById(R.id.et_destination);
        tv_payment = findViewById(R.id.tv_payment);
        tv_payment_way = findViewById(R.id.tv_payment_way);
        tv_vehicle_name = findViewById(R.id.tv_vehicle_name);
        tv_payment.setText(GlobalVariables.calculated);
        if (GlobalVariables.CashPayment == 1)
            tv_payment_way.setText("نقدی");
        else
            tv_payment_way.setText("اعتباری");
        switch (GlobalVariables.v) {
            case 0:
                tv_vehicle_name.setText("پیک موتوری");
                break;
            case 1:
                tv_vehicle_name.setText("تاکسی موتور");
                break;
            case 2:
                tv_vehicle_name.setText("سواری");
                break;
            case 3:
                tv_vehicle_name.setText("وانت سنگین");
                break;
            case 4:
                tv_vehicle_name.setText("وانت بار");
                break;
        }
    }

    public void OnDiscountClicked(View view) {
        startActivity(new Intent(SendRequestActivity.this, GiftCardActivity.class));

    }

    public void SendRequest(View view) {

        Request request = new Request();
        request.setLat(String.valueOf(GlobalVariables.start.getX()));
        request.setLng(String.valueOf(GlobalVariables.start.getY()));
        request.setDlat(String.valueOf(GlobalVariables.end.getX()));
        request.setDlng(String.valueOf(GlobalVariables.end.getY()));
        request.setHaveReturn(GlobalVariables.HaveReturn);
        request.setPayByRequest(GlobalVariables.PayByRequest);
        request.setCashPayment(GlobalVariables.CashPayment);
        request.setVehicle(GlobalVariables.vehicle);
        request.setStop(GlobalVariables.haveStop);
        request.setDestinationAddress(et_destination.getText().toString());
        request.setLocationAddress(et_start_location.getText().toString());

        send(request);
    }

    public void OnCancelClicked(View view) {

        startActivity(new Intent(SendRequestActivity.this, MainActivity.class));

    }


    public void send(Request request) {
        ApiInterface.sendRequestCallback sendRequestCallback = new ApiInterface.sendRequestCallback() {
            @Override
            public void onResponse(String message) {

                startActivity(new Intent(SendRequestActivity.this, IncreaseCreditActivity.class));

            }

            @Override
            public void onFailure(String err) {

            }
        };
        RequestController requestController = new RequestController(sendRequestCallback);
        requestController.send(request);


    }

}
