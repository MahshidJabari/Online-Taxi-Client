package com.jabari.client.activity.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jabari.client.R;
import com.jabari.client.activity.main.FirstActivity;
import com.jabari.client.custom.GlobalVariables;
import com.jabari.client.custom.PrefManager;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    public void OnExitClick() {
        GlobalVariables.isLogin = false;
        removePreferences();
        startActivity(new Intent(ProfileActivity.this, FirstActivity.class));

    }

    private void removePreferences() {

        PrefManager prefManager = new PrefManager(this);
        prefManager.removeToken();
        prefManager.removeUser();
        GlobalVariables.tok = "";
        GlobalVariables.phoneUser = "";

    }

}
