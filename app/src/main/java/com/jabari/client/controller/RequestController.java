package com.jabari.client.controller;

import com.jabari.client.entities.Cost;
import com.jabari.client.entities.Request;
import com.jabari.client.network.config.ApiClient;
import com.jabari.client.network.config.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RequestController {

    ApiInterface.CalculateCallback calculateCallback;
    ApiInterface.sendRequestCallback sendRequestCallback;

    public RequestController(ApiInterface.CalculateCallback calculateCallback) {
        this.calculateCallback = calculateCallback;
    }

    public RequestController(ApiInterface.sendRequestCallback sendRequestCallback) {
        this.sendRequestCallback = sendRequestCallback;
    }

    public void calculate(Cost cost) {
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterfaces = retrofit.create(ApiInterface.class);
        Call<Cost> call = apiInterfaces.calculate(cost.getLat(), cost.getLng(), cost.getDlat(), cost.getDlng(), cost.getVehicle());
        call.enqueue(new Callback<Cost>() {
            @Override
            public void onResponse(Call<Cost> call, Response<Cost> response) {
                if (response.isSuccessful()) {

                    if (response.body() != null) {
                        String calculated = response.body().getCost();
                        calculateCallback.onResponse(calculated);
                    } else calculateCallback.onFailure("Error");


                }
            }

            @Override
            public void onFailure(Call<Cost> call, Throwable t) {

                calculateCallback.onFailure("Error");

            }
        });
    }

    public void send(Request request) {

        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterfaces = retrofit.create(ApiInterface.class);
        Call<Request> call = apiInterfaces.send(request.getLat(), request.getLng(), request.getDlat(),
                request.getDlng(), request.getVehicle(), request.getStop(), request.getHaveReturn(),
                request.getCashPayment(), request.getPayByRequest(), request.getDestinationAddress(), request.getLocationAddress());
        call.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {

                if (response.isSuccessful()) {

                    if (response.body() != null) {
                        String message = response.body().getMessage();
                        sendRequestCallback.onResponse(message);
                    } else sendRequestCallback.onFailure("Error");
                }

            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {

            }
        });

    }

}
