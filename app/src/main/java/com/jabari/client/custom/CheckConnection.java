package com.jabari.client.custom;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckConnection {

    public static boolean haveNetworkConnection(Context context) {
        boolean conntected = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null) {
            if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                conntected = true;
            } else conntected = netInfo.getType() == ConnectivityManager.TYPE_MOBILE;
        }
        return conntected;
    }

}
