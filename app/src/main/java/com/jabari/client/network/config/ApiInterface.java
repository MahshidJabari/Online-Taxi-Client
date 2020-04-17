package com.jabari.client.network.config;


import com.google.gson.JsonObject;
import com.jabari.client.network.model.Cost;
import com.jabari.client.network.model.Request;
import com.jabari.client.network.model.Item;
import com.jabari.client.network.model.NeshanSearch;
import com.jabari.client.network.model.Travel;
import com.jabari.client.network.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("auth/user/login/")
    Call<JsonObject> getVerifyCode(@Field("mobile") String PhoneNumber);

    interface UserVerifyCodeCallback {
        void onResponse();

        void onFailure(String error);
    }

    @FormUrlEncoded
    @POST("auth/user/loginverify")
    Call<JsonObject> check_login(@Field("mobile") String PhoneNumber,
                                 @Field("verifyCode") String verifyCode);

    interface LoginUserCallback {
        void onResponse(User user, String token);

        void onFailure(String error);
    }

    @GET("general/laws")
    Call<JsonObject> get_rules();

    interface GetLawsCallback {
        void onResponse(String laws);

        void onFailure(String error);
    }

    @Headers("Api-Key: service.PnRV9ocd8zm9QYYlJUNLJoAihE3hfy34WUZ6jcjr")
    @GET
    Call<NeshanSearch> getNeshanSearch(@Url String url);

    interface SearchCallback {
        void onResponse(List<Item> itemList);

        void onFailure(String err);
    }

    @FormUrlEncoded
    @POST("user/calculate")
    Call<Cost> calculate(@Field("lat") String lat,
                         @Field("lng") String lng,
                         @Field("dlat") String dlat,
                         @Field("dlng") String dlng,
                         @Field("vehicle") int vehicle);

    interface CalculateCallback {
        void onResponse(String calculated);

        void onFailure(String err);
    }

    @FormUrlEncoded
    @PUT("user/request")
    Call<Request> send(@Field("lat") String lat,
                       @Field("lng") String lng,
                       @Field("dlat") String dlat,
                       @Field("dlng") String dlng,
                       @Field("vehicle") int vehicle,
                       @Field("stop") int stop,
                       @Field("haveReturn") int haveReturn,
                       @Field("cashPayment") int cashPayment,
                       @Field("payByRequest") int payByRequest,
                       @Field("destinationAddress") String destinationAddress,
                       @Field("locationAddress") String locationAddress);

    interface sendRequestCallback {
        void onResponse(String message);

        void onFailure(String err);
    }

    @GET("user")
    Call<JsonObject> getCurrentUser();

    interface getCurrentUserCallback {
        void onResponse(User user, String id);

        void onFailure(String error);
    }

    @FormUrlEncoded
    @POST("bank/request")
    Call<JsonObject> pay(@Field("amount") String calculated);

    interface payCallback {
        void onResponse(String url);

        void onFailure(String error);
    }

    @FormUrlEncoded
    @POST("user/request/result")
    Call<JsonObject> check_request(@Field("amount") String calculated);

    interface checkCallback {
        void onResponse(boolean success);

        void onFailure(String error);
    }

    @GET("general/support")
    Call<JsonObject> call_support();

    interface callSupportCallback {
        void onResponse(String phone);

        void onFailure(String error);
    }

    @Multipart
    @POST("image")
    Call<JsonObject> upload_image(@Part MultipartBody.Part image);

    interface uploadImageCallback {
        void onResponse(String url);

        void onFailure(String error);
    }

    @FormUrlEncoded
    @PUT("user")
    Call<JsonObject> update_user_info(@Field("age") int age,
                                      @Field("bio") String bio,
                                      @Field("email") String email,
                                      @Field("gender") boolean gender,
                                      @Field("name") String name,
                                      @Field("avatar") String url);

    interface updateUserCallback {
        void onResponse(boolean success);

        void onFailure(String error);
    }

    @FormUrlEncoded
    @POST("user/request/success")
    Call<Travel> travelHistory();

    interface travelHistoryCallback {
        void onResponse(List<Travel> travels);

        void onFailure(String error);
    }
}
