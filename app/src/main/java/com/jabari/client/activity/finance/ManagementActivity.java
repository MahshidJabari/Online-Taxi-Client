package com.jabari.client.activity.finance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jabari.client.R;
import com.jabari.client.activity.main.MainActivity;
import com.jabari.client.custom.GlobalVariables;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ManagementActivity extends AppCompatActivity {
    private TextView tv_return,tv_Credit;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        setViews();
           }
    public void setViews(){
        tv_Credit = findViewById(R.id.tv_credit);
        tv_Credit.setText(GlobalVariables.credit);

        tv_return = findViewById(R.id.tv_return);
        tv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManagementActivity.this, MainActivity.class));
            }
        });

    }
}
