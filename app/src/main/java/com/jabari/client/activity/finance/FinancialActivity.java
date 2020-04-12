package com.jabari.client.activity.finance;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.jabari.client.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FinancialActivity extends AppCompatActivity {

    private TextView tv_code, tv_type, tv_cache, tv_date;
    private RecyclerView recycler_finance;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);

        setViews();
    }

    private void setViews() {
        tv_code = findViewById(R.id.tv_travel_code);
        tv_type = findViewById(R.id.tv_travel_type);
        tv_cache = findViewById(R.id.tv_travel_cache);
        tv_date = findViewById(R.id.tv_travel_date);
    }

    private void setRecyclerView(){


    }
}
