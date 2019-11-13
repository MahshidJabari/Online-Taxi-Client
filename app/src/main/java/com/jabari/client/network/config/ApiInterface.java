package com.jabari.client.network.config;


import com.google.gson.JsonObject;
import com.jabari.client.global.GeneralResponse;
import com.jabari.client.network.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("auth/user/login/")
    Call<ResponseBody> getVerifyCode(@Field("mobile") String PhoneNumber);

    @FormUrlEncoded
    @POST("auth/user/loginverify")
    Call<JsonObject> check_login(@Field("mobile") String PhoneNumber,
                                 @Field("verifyCode") String verifyCode);

    interface LoginUserCallback{
        void onResponse(GeneralResponse generalResponse, User user, String token);
        void onFailure(String error);
    }

}
