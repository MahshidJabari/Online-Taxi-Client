package com.jabari.client.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jabari.client.R;
import com.jabari.client.activity.help.SupportActivity;
import com.jabari.client.activity.finance.GiftCardActivity;
import com.suke.widget.SwitchButton;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DetailsActivity extends AppCompatActivity {

    private SwitchButton swb_back, swb_pay_way, swb_pay_company, swb_scheduling;
    private LinearLayout lin_date, lin_date_label, lin_travel_cache;
    private TextView tv_travel_cache;
    private Button btn_gift_code;

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
        startActivity(new Intent(DetailsActivity.this, SupportActivity.class));
    }

}
