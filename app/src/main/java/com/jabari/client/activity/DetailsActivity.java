package com.jabari.client.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;

import com.jabari.client.R;
import com.suke.widget.SwitchButton;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DetailsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private SwitchButton swt_back, swt_pay_way, swt_pay_company;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        swt_back = findViewById(R.id.swt_back);
        swt_pay_way = findViewById(R.id.swt_pay_way);
        swt_pay_company = findViewById(R.id.swt_pay_company);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }
}
