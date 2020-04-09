package com.jabari.client.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.jabari.client.R;
import com.jabari.client.activity.finance.GiftCardActivity;
import com.jabari.client.activity.help.SupActivity;
import com.jabari.client.custom.GlobalVariables;
import com.suke.widget.SwitchButton;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DetailsActivity extends AppCompatActivity {

    private SwitchButton swb_back, swb_pay_way, swb_pay_company, swb_scheduling;
    private LinearLayout lin_date, lin_date_label, lin_travel_cache;
    private TextView tv_travel_cache;
    private Button btn_gift_code;
    private Spinner hour_spinner, min_spinner, stop_spinner, vehicle_spinner;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        setUpViews();
        onCheckedChanged();
        setUpSpinners();

    }

    private void setUpViews() {

        swb_back = findViewById(R.id.swt_back);
        swb_pay_way = findViewById(R.id.swt_pay_way);
        swb_pay_company = findViewById(R.id.swt_pay_company);
        swb_scheduling = findViewById(R.id.swt_scheduling);
        lin_date = findViewById(R.id.lin_date);
        lin_date_label = findViewById(R.id.lin_date_label);
        lin_travel_cache = findViewById(R.id.lin_travel_cache);
        tv_travel_cache = findViewById(R.id.tv_travel_cache);
        btn_gift_code = findViewById(R.id.btn_gift_code);

        tv_travel_cache.setText(GlobalVariables.calculated);

    }

    private void setUpSpinners() {
        min_spinner = findViewById(R.id.min_spin);
        hour_spinner = findViewById(R.id.hour_spin);
        stop_spinner = findViewById(R.id.stop_spin);
        vehicle_spinner = findViewById(R.id.vehicle_spin);

        ArrayAdapter<CharSequence> min_adapter = ArrayAdapter.createFromResource(this, R.array.min_arrays,
                R.layout.spinner_item_dark);
        min_adapter.setDropDownViewResource(R.layout.spinner_dark_text);
        min_spinner.setAdapter(min_adapter);

        ArrayAdapter<CharSequence> hour_adapter = ArrayAdapter.createFromResource(this, R.array.hour_arrays,
                R.layout.spinner_item_dark);
        hour_adapter.setDropDownViewResource(R.layout.spinner_dark_text);
        hour_spinner.setAdapter(hour_adapter);

        ArrayAdapter<CharSequence> stop_adapter = ArrayAdapter.createFromResource(this, R.array.stop_arrays,
                android.R.layout.simple_spinner_item);
        stop_adapter.setDropDownViewResource(R.layout.spinner_dark_text);
        stop_spinner.setAdapter(stop_adapter);

        ArrayAdapter<CharSequence> vehicle_adapter = ArrayAdapter.createFromResource(this, R.array.vehicle_arrays,
                R.layout.spinner_item);
        vehicle_adapter.setDropDownViewResource(R.layout.spinner_dark_text);
        vehicle_spinner.setAdapter(vehicle_adapter);


    }

    public void onCheckedChanged() {

        swb_scheduling.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {

                lin_date.setVisibility(View.GONE);
                lin_date_label.setVisibility(View.GONE);
                lin_travel_cache.setVisibility(View.VISIBLE);
            }
        });

    }

    public void OnCancelClicked(View view) {


        startActivity(new Intent(DetailsActivity.this, MainActivity.class));

    }

    public void OnGiftCodeButtonClicked(View view) {
        startActivity(new Intent(DetailsActivity.this, GiftCardActivity.class));
    }

    public void OnSupportClicked(View view) {
        startActivity(new Intent(DetailsActivity.this, SupActivity.class));
    }

    public void OnContinueClick(View view) {


        if (swb_back.isChecked())
            GlobalVariables.HaveReturn = 1;
        else
            GlobalVariables.HaveReturn = 0;
        if (swb_pay_way.isChecked())
            GlobalVariables.CashPayment = 1;
        else
            GlobalVariables.CashPayment = 0;
        if (swb_pay_company.isChecked())
            GlobalVariables.PayByRequest = 0;
        else
            GlobalVariables.PayByRequest = 1;
        String vehicle = vehicle_spinner.getSelectedItem().toString();
        switch (vehicle) {
            case "پیک موتوری":
                GlobalVariables.vehicle = 1;
                break;
            case "تاکسی موتور":
                GlobalVariables.vehicle = 2;
                break;
            case "سواری":
                GlobalVariables.vehicle = 3;
                break;
            case "وانت سنگین":
                GlobalVariables.vehicle = 4;
                break;

            case "وانت بار":
                GlobalVariables.vehicle = 5;
                break;

        }
        String stop = stop_spinner.getSelectedItem().toString();
        switch (stop) {
            case "دارد":
                GlobalVariables.haveStop = 1;
                break;
            case "ندارد":
                GlobalVariables.haveStop = 0;
                break;

        }
        startActivity(new Intent(DetailsActivity.this, SendRequestActivity.class));
    }


}
