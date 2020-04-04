package com.jabari.client.controller;

import com.jabari.client.entities.Request;
import com.jabari.client.network.config.ApiClient;
import com.jabari.client.network.config.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RequestController {

    ApiInterface.CalculateCallback calculateCallback;

    public RequestController(ApiInterface.CalculateCallback calculateCallback) {
        this.calculateCallback = calculateCallback;
    }

    private void calculate(Request request) {
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterfaces = retrofit.create(ApiInterface.class);
        Call<Request> call = apiInterfaces.calculate(request);
        call.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                if (response.isSuccessful()) {

                    if (response.body().getCost() != null) {
                        String pay = response.body().getCost();
                    }

                }
            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {

            }
        });
    }
}
