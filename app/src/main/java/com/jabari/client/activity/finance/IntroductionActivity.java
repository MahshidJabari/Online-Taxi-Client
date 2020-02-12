package com.jabari.client.activity.finance;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import com.jabari.client.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class IntroductionActivity extends AppCompatActivity {
    private FloatingActionButton fab_others, fab_mail, fab_telegram, fab_comment;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        fab_others = findViewById(R.id.fab_others);
        fab_mail = findViewById(R.id.fab_mail);
        fab_telegram = findViewById(R.id.fab_telegram);
        fab_comment = findViewById(R.id.fab_comment);
        fab_others.bringToFront();
        fab_mail.bringToFront();
        fab_telegram.bringToFront();
        fab_comment.bringToFront();

    }
}
