package com.jabari.client.activity.finance;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jabari.client.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class GiftCardActivity extends AppCompatActivity {

    private EditText et_gift_card,et_discount_card;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_card);

        setUpView();
    }

    private void setUpView(){

        et_discount_card = findViewById(R.id.et_discount_code);
        et_gift_card = findViewById(R.id.et_discount_code);

    }

    public void OnDiscountClicked(View view){

        if(et_gift_card!=null){

        }
        if(et_discount_card!= null){

        }
    }
}
