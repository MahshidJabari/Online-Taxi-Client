package com.jabari.client.activity.finance;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jabari.client.R;
import com.jabari.client.controller.UserController;
import com.jabari.client.custom.DigitConverter;
import com.jabari.client.custom.ExceptionHandler;
import com.jabari.client.custom.GlobalVariables;
import com.jabari.client.network.config.ApiInterface;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class GiftCardActivity extends AppCompatActivity {

    private TextView tv_introduce_card;
    private EditText et_gift_card;
    private ExceptionHandler handler;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_card);
        handler = new ExceptionHandler(this);
        setUpView();
    }

    private void setUpView() {

        tv_introduce_card = findViewById(R.id.tv_introduce_code);
        et_gift_card = findViewById(R.id.tv_gift_card);
        tv_introduce_card.setText(DigitConverter.convert(GlobalVariables.introduceCode));

    }

    public void OnDiscountClicked(View view) {
        if (et_gift_card.getText() != null) {
            ApiInterface.giftCallBack giftCallBack = new ApiInterface.giftCallBack() {
                @Override
                public void onResponse(String success) {

                }

                @Override
                public void onFailure(String error) {
                    handler.generateError(error);
                }
            };
            UserController userController = new UserController(giftCallBack);
            userController.gift(et_gift_card.getText().toString());
        } else
            handler.generateError("gift_null");
    }


}
