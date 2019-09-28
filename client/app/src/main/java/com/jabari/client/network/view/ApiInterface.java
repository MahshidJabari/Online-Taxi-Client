package com.jabari.client.network.view;

import com.jabari.client.network.model.NeshanSearch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

public interface ApiInterface {
    @Headers("Api-Key: client")
    @GET
    Call<NeshanSearch> getNeshanSearch(@Url String url);
}
