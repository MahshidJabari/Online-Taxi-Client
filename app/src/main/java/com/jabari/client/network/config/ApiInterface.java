package com.jabari.client.network.config;


import com.google.gson.JsonObject;
import com.jabari.client.custom.GeneralResponse;
import com.jabari.client.network.model.Item;
import com.jabari.client.network.model.NeshanSearch;
import com.jabari.client.network.model.User;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("auth/user/login/")
    Call<JsonObject> getVerifyCode(@Field("mobile") String PhoneNumber);

    interface UserVerifyCodeCallback{
        void onResponse(GeneralResponse generalResponse);
        void onFailure(String error);
    }

    @FormUrlEncoded
    @POST("auth/user/loginverify")
    Call<JsonObject> check_login(@Field("mobile") String PhoneNumber,
                                 @Field("verifyCode") String verifyCode);

    interface LoginUserCallback{
        void onResponse(GeneralResponse generalResponse, User user, String token);
        void onFailure(String error);
    }

    @GET("general/laws")
    Call<JsonObject> get_rules();
    interface GetLawsCallback{
        void onResponse(GeneralResponse generalResponse);
        void onFailure(String error);
    }
    @Headers("Api-Key: service.PnRV9ocd8zm9QYYlJUNLJoAihE3hfy34WUZ6jcjr")
    @GET
    Call<NeshanSearch> getNeshanSearch(@Url String url);
    interface SearchCallback{
        void onResponse(List<Item> itemList);
        void onFailure(String err);
    }

}
