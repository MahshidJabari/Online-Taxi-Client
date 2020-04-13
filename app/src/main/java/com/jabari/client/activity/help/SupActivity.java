package com.jabari.client.activity.help;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jabari.client.R;
import com.jabari.client.controller.UserController;
import com.jabari.client.network.config.ApiInterface;

import es.dmoral.toasty.Toasty;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SupActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
    }

    public void CallSupportClicked(View view) {
        ApiInterface.callSupportCallback callSupportCallback = new ApiInterface.callSupportCallback() {
            @Override
            public void onResponse(String phone) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                startActivity(intent);
            }

            @Override
            public void onFailure(String error) {
                if (error.equals("connection"))
                    Toasty.error(SupActivity.this, "خطا در برقراری ارتباط", Toasty.LENGTH_LONG).show();
                if (error.equals("null"))
                    Toasty.error(SupActivity.this, "درخواست با خطا مواجه شد", Toasty.LENGTH_LONG).show();
            }
        };
        UserController userController = new UserController(callSupportCallback);
        userController.call();
    }
}
