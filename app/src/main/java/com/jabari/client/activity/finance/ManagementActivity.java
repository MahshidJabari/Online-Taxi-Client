package com.jabari.client.activity.finance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.jabari.client.R;
import com.jabari.client.activity.main.MainActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ManagementActivity extends AppCompatActivity {
    private TextView tv_return;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        tv_return = findViewById(R.id.tv_return);
        tv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManagementActivity.this, MainActivity.class));
            }
        });
    }
}
