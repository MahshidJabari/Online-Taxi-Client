package com.jabari.client.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jabari.client.network.config.ApiClient;
import com.jabari.client.network.config.ApiInterface;
import com.jabari.client.network.model.Finance;

import java.lang.reflect.Type;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FinanceController {
    ApiInterface.reportCallBack reportCallBack;

    public FinanceController(ApiInterface.reportCallBack reportCallBack) {
        this.reportCallBack = reportCallBack;
    }

    public void getReport() {
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterfaces = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterfaces.financialReport(1);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    Type userListType = new TypeToken<ArrayList<Finance>>(){}.getType();
                    ArrayList<Finance> finances = new Gson().fromJson(response.body().get("requests"), userListType);
                    reportCallBack.onResponse(finances);

                } else reportCallBack.onFailure("null");
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                reportCallBack.onFailure("connection");
            }
        });
    }
}
