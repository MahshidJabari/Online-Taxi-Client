package com.jabari.client.custom;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_TOKEN = "token";
    private static final String IS_USER = "user";
    private static final String PREF_NAME = "his-welcome";
    private static final String IS_Phone = "phone";
    private static final String BITMAP = "BITMAP";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;


    public PrefManager(Context context) {

        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public String getTOken() {
        String result = "";
        result = pref.getString(IS_TOKEN, result);

        return result;

    }

    public void setToken(String token) {

        editor.putString(IS_TOKEN, token);
        editor.commit();
    }

    public void removeToken() {
        editor.remove(IS_TOKEN);
        editor.commit();
    }

    public String getPhoneNum() {

        String result = "";
        return pref.getString(IS_Phone, result);
    }

    public void setPhoneNum(String emailVal) {

        editor.putString(IS_Phone, emailVal);
        editor.commit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public void removeUser() {
        editor.remove(IS_USER);
        editor.commit();
    }
    public void setBitmapString (String bits){
        editor.putString(BITMAP,bits);
        editor.commit();
    }
    public String getBitmap(){
        String result = "";
        return pref.getString(BITMAP,result);
    }


}
