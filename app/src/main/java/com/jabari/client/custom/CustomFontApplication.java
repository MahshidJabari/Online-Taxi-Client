package com.jabari.client.custom;

import android.app.Application;

import com.jabari.client.R;

import me.cheshmak.android.sdk.core.Cheshmak;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class CustomFontApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Cheshmak.with(this);
        Cheshmak.initTracker("DUWZ9cCJLPQz012y1Luu0Q==");
        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder()
                        .setDefaultFontPath("font/bnazanin.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

    }
}
