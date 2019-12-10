package com.jabari.client.controller;

import android.util.Log;

import com.jabari.client.network.config.ApiInterface;
import com.jabari.client.network.config.NeshanRetrofit;
import com.jabari.client.network.model.Item;
import com.jabari.client.network.model.NeshanSearch;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DoSearchController {

    List<Item> items;
    ApiInterface.SearchCallback searchCallBack;

    public DoSearchController(ApiInterface.SearchCallback searchCallback) {

        this.searchCallBack = searchCallback;
    }

    public void Do(String term, final double lat, final double lng) {

        final String requestURL = "https://api.neshan.org/v1/search?term=" + term + "&lat=" + lat + "&lng=" + lng;

        Retrofit retrofit = NeshanRetrofit.getRetrofitInstance();
        ApiInterface apiInterfaces = retrofit.create(ApiInterface.class);
        Call<NeshanSearch> call = apiInterfaces.getNeshanSearch(requestURL);
        call.enqueue(new Callback<NeshanSearch>() {
            @Override
            public void onResponse(Call<NeshanSearch> call, Response<NeshanSearch> response) {

                NeshanSearch search = response.body();
                if (response.isSuccessful()) {
                    Log.d("response", response.body().toString());
                    Log.d("response1", search.getItems().toString());
                    items = search.getItems();
                    searchCallBack.onResponse(items);
                } else
                    Log.d("response", "empty");
            }

            @Override
            public void onFailure(Call<NeshanSearch> call, Throwable t) {

                searchCallBack.onFailure("Error!");
            }
        });


    }
}
