package com.jabari.client.controller;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jabari.client.custom.GlobalVariables;
import com.jabari.client.network.config.ApiClient;
import com.jabari.client.network.config.ApiInterface;
import com.jabari.client.network.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginController {

    public String txt;
    ApiInterface.LoginUserCallback loginUserCallback;
    ApiInterface.UserVerifyCodeCallback userVerifyCodeCallback;
    ApiInterface.GetLawsCallback getLawsCallback;

    public LoginController(ApiInterface.UserVerifyCodeCallback userVerifyCodeCallback) {
        this.userVerifyCodeCallback = userVerifyCodeCallback;
    }

    public LoginController(ApiInterface.LoginUserCallback loginUserCallback) {
        this.loginUserCallback = loginUserCallback;
    }

    public LoginController(ApiInterface.GetLawsCallback getLawsCallback) {
        this.getLawsCallback = getLawsCallback;
    }



    public void Do(final String mobileNum, String verify_code) {
        User user = new User();
        user.setMobileNum(mobileNum);
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterfaces = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterfaces.check_login(mobileNum, verify_code);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    User user = new Gson().fromJson(response.body().get("user"), User.class);
                    String token = new Gson().fromJson(response.body().get("jwtAccessToken"), String.class);
                    GlobalVariables.tok = token;
                    Log.d("tok", token);

                    loginUserCallback.onResponse(user, token);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loginUserCallback.onFailure("Error!");
            }
        });
    }

    public void VerifyCode(String PhoneNumber) {
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.getVerifyCode(PhoneNumber);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    userVerifyCodeCallback.onResponse();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                userVerifyCodeCallback.onFailure("Error!");
            }
        });
    }

    public String getLaws() {

        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.get_rules();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    txt = new Gson().fromJson(response.body().get("laws"), String.class);

                    getLawsCallback.onResponse(txt);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                getLawsCallback.onFailure("خطا در برقراری ارتباط");

            }
        });

        return txt;
    }


}