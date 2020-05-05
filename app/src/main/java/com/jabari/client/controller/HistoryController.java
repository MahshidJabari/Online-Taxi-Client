package com.jabari.client.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jabari.client.network.config.ApiClient;
import com.jabari.client.network.config.ApiInterface;
import com.jabari.client.network.model.Finance;
import com.jabari.client.network.model.Request;
import com.jabari.client.network.model.Travel;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HistoryController {
    ApiInterface.reportCallBack reportCallBack;
    ApiInterface.requestSuccessCallback requestSuccessCallback;
    ApiInterface.requestsReportCallBack requestsReportCallBack;

    public HistoryController(ApiInterface.reportCallBack reportCallBack) {
        this.reportCallBack = reportCallBack;
    }

    public HistoryController(ApiInterface.requestSuccessCallback requestSuccessCallback) {
        this.requestSuccessCallback = requestSuccessCallback;
    }

    public HistoryController(ApiInterface.requestsReportCallBack requestsReportCallBack) {
        this.requestsReportCallBack = requestsReportCallBack;
    }

    public void getReport() {
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterfaces = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterfaces.financialReport(1);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    Type financialListType = new TypeToken<ArrayList<Finance>>() {
                    }.getType();
                    ArrayList<Finance> finances = new Gson().fromJson(response.body().get("requests"), financialListType);
                    reportCallBack.onResponse(finances);

                } else reportCallBack.onFailure("null");
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                reportCallBack.onFailure("connection");
            }
        });
    }

    public void getSuccess() {
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterfaces = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterfaces.requestSuccessHistory(1);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {

                    String requests = new Gson().fromJson(response.body().get("countSuccess"), String.class);
                    String payments = new Gson().fromJson(response.body().get("sumValues"), String.class);
                    requestSuccessCallback.onResponse(requests, payments);
                } else requestSuccessCallback.onFailure("null");
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                requestSuccessCallback.onFailure("connection");
            }
        });
    }

    public void getRequestDetail() {
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterfaces = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterfaces.requestsReport(1);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    Type requestListType = new TypeToken<ArrayList<Travel>>() {
                    }.getType();
                    ArrayList<Travel> requests = new Gson().fromJson(response.body().get("requests"), requestListType);
                   requestsReportCallBack.onResponse(requests);

                } else requestsReportCallBack.onFailure("null");
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                requestsReportCallBack.onFailure("connection");
            }
        });
    }
}
