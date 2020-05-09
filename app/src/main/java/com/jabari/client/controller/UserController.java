package com.jabari.client.controller;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jabari.client.network.config.ApiClient;
import com.jabari.client.network.config.ApiInterface;
import com.jabari.client.network.model.User;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserController {

    ApiInterface.callSupportCallback callSupportCallback;
    ApiInterface.getCurrentUserCallback getCurrentUserCallback;
    ApiInterface.uploadImageCallback uploadImageCallback;
    ApiInterface.updateUserCallback updateUserCallback;
    ApiInterface.giftCallBack giftCallBack;

    public UserController(ApiInterface.callSupportCallback callSupportCallback) {
        this.callSupportCallback = callSupportCallback;
    }

    public UserController(ApiInterface.getCurrentUserCallback getCurrentUserCallback) {
        this.getCurrentUserCallback = getCurrentUserCallback;
    }

    public UserController(ApiInterface.uploadImageCallback uploadImageCallback) {
        this.uploadImageCallback = uploadImageCallback;
    }

    public UserController(ApiInterface.updateUserCallback updateUserCallback) {
        this.updateUserCallback = updateUserCallback;
    }

    public UserController(ApiInterface.giftCallBack giftCallBack) {
        this.giftCallBack = giftCallBack;
    }

    public void call() {
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterfaces = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterfaces.call_support();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    String phone = new Gson().fromJson(response.body().get("phoneNumber"), String.class);
                    callSupportCallback.onResponse(phone);
                } else
                    callSupportCallback.onFailure("null");
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callSupportCallback.onFailure("connection");
            }
        });
    }

    public void getCurrentUser() {
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.getCurrentUser();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    boolean success = new Gson().fromJson(response.body().get("success"), Boolean.class);
                    if (success) {
                        User user = new Gson().fromJson(response.body().get("user"), User.class);
                        String id = new Gson().fromJson(response.body().get("id"), String.class);
                        getCurrentUserCallback.onResponse(user, id);
                    } else
                        getCurrentUserCallback.onFailure("expired");
                } else
                    Log.d("response", "null");


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                getCurrentUserCallback.onFailure("connection");

            }
        });

    }

    public void upload(String filePath) {
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterfaces = retrofit.create(ApiInterface.class);
        File f = new File(filePath);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), f);
        MultipartBody.Part image = MultipartBody.Part.createFormData("image", f.getName(), reqFile);

        Call<JsonObject> call = apiInterfaces.upload_image(image);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    boolean success = new Gson().fromJson(response.body().get("success"), Boolean.class);
                    if (success) {
                        String url = new Gson().fromJson(response.body().get("url"), String.class);
                        uploadImageCallback.onResponse("http://digipeyk.com/" + url);
                    } else
                        uploadImageCallback.onFailure("null");
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                uploadImageCallback.onFailure("connection");
            }
        });
    }

    public void updateUser(User user) {
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterfaces = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterfaces.update_user_info(1, "کاربر  دیجی پیک", user.getEmail(), false,
                user.getFirstName() + user.getLastName(), user.getAvatar());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    boolean success = new Gson().fromJson(response.body().get("success"), Boolean.class);
                    updateUserCallback.onResponse(success);
                } else updateUserCallback.onFailure("null");
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                updateUserCallback.onFailure("connection");
            }
        });
    }

    public void gift(String code) {
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterfaces = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterfaces.useGiftCode(code);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null)
                    giftCallBack.onResponse("gift");
                else
                    giftCallBack.onFailure("gift");
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                giftCallBack.onFailure("connection");
            }
        });
    }
}
