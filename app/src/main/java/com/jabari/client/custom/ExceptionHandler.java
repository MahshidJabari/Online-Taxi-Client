package com.jabari.client.custom;

import android.app.Activity;


import com.jabari.client.R;

import es.dmoral.toasty.Toasty;

public class ExceptionHandler {

    private final Activity myContext;

    public ExceptionHandler(Activity context) {
        myContext = context;
    }

    public void generateError(String err) {
        switch (err) {
            case "connection":
                Toasty.error(myContext.getBaseContext(), myContext.getString(R.string.exp_connection), Toasty.LENGTH_LONG).show();
                break;
            case "null":
                Toasty.error(myContext.getBaseContext(), myContext.getString(R.string.exp_null), Toasty.LENGTH_LONG).show();
                break;
            case "update location stopped":
                Toasty.error(myContext.getBaseContext(), myContext.getString(R.string.exp_update_location_stopped), Toasty.LENGTH_SHORT).show();
                break;
            case "location setting":
                Toasty.error(myContext.getBaseContext(), myContext.getString(R.string.exp_location_setting), Toasty.LENGTH_LONG).show();
                break;
            case "invalid_phone":
                Toasty.error(myContext.getBaseContext(), myContext.getString(R.string.exp_invalid_phone), Toasty.LENGTH_LONG).show();
                break;
            case "wrong_code":
                Toasty.error(myContext.getBaseContext(), myContext.getString(R.string.exp_wrong_code), Toasty.LENGTH_LONG).show();
                break;
            case "wrong_phone":
                Toasty.error(myContext.getBaseContext(), myContext.getString(R.string.exp_wrong_phone), Toasty.LENGTH_LONG).show();
                break;
            case "law":
                Toasty.error(myContext.getBaseContext(), myContext.getString(R.string.exp_law), Toasty.LENGTH_LONG).show();
                break;
            case "anonymous":
                Toasty.error(myContext.getBaseContext(), myContext.getString(R.string.exp_anonymous), Toasty.LENGTH_LONG).show();
                break;
            case "null_doc":
                Toasty.error(myContext.getBaseContext(), myContext.getString(R.string.exp_null_doc), Toasty.LENGTH_LONG).show();
                break;
            case "gallery":
                Toasty.error(myContext.getBaseContext(), myContext.getString(R.string.exp_gallery), Toasty.LENGTH_LONG).show();
                break;
            case "gift":
                Toasty.error(myContext.getBaseContext(), myContext.getString(R.string.exp_gift), Toasty.LENGTH_LONG).show();
                break;
            case "gift_null":
                Toasty.error(myContext.getBaseContext(), myContext.getString(R.string.exp_gift_null), Toasty.LENGTH_LONG).show();
                break;
        }
    }

    public void generateSuccess(String success) {

        switch (success) {
            case "code":
                Toasty.success(myContext.getBaseContext(), myContext.getString(R.string.success_code), Toasty.LENGTH_LONG).show();
                break;
            case "gift":
                Toasty.error(myContext.getBaseContext(), myContext.getString(R.string.success_gift), Toasty.LENGTH_LONG).show();
                break;
        }
    }
}
