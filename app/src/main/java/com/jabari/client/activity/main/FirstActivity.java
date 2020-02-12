package com.jabari.client.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.jabari.client.R;
import com.jabari.client.activity.account.LoginActivity;
import com.jabari.client.custom.GlobalVariables;
import com.jabari.client.custom.PrefManager;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FirstActivity extends AppCompatActivity {
    private Button btn_login;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_first);

        setPage();
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstActivity.this, LoginActivity.class));
            }
        });


    }

    private Boolean checkPref() {
        PrefManager prefManager = new PrefManager(getBaseContext());
        String token = "";
        String userPhoneNum = "";
        token = prefManager.getTOken();
        userPhoneNum = prefManager.getPhoneNum();

        if (TextUtils.isEmpty(token) || TextUtils.isEmpty(userPhoneNum)) {
            return false;
        } else if (!TextUtils.isEmpty(token)) {
            GlobalVariables.tok = token;
            GlobalVariables.phoneUser = userPhoneNum;

        }
        return true;
    }

    private void removePreferences() {

        PrefManager prefManager = new PrefManager(this);
        prefManager.removeToken();
        prefManager.removeUser();
        GlobalVariables.tok = "";
        GlobalVariables.phoneUser = "";

    }

    private void setPage() {
        if (checkPref()) {
            GlobalVariables.isLogin = true;
            startActivity(new Intent(FirstActivity.this, MainActivity.class));

        } else {
            removePreferences();
        }
    }

}
