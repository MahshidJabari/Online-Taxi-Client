package com.jabari.client.network.config;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NeshanRetrofit {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.neshan.org/v1/";

    public static Retrofit getRetrofitInstance() {
        // creating a CertificatePinner object and adding public key of neshan.org to it
        CertificatePinner certPinner = new CertificatePinner.Builder()
                .add("*.neshan.org",
                        "sha256/yYpZh6il0CwJjGkd4ZsI2jH9FuBTcjptkUAjsQ8f4d4=")
                .add("*.neshan.org",
                        "sha256/S4AbJNGvyS57nzJwv8sPMUML8VHSqH1vbiBftdPcErI=")
                .add("*.neshan.org",
                        "sha256/qiYwp7YXsE0KKUureoyqpQFubb5gSDeoOoVxn6tmfrU=")
                .add("*.neshan.org",
                        "sha256/Cyg7e5STKgZCwdABdPZlqO5lQWSE0KbWr624HoIUuUc=")
                .build();

        // adding the created certPinner to OkHttpClient
        OkHttpClient client = new OkHttpClient.Builder()
                .certificatePinner(certPinner)
                .build();

        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}

