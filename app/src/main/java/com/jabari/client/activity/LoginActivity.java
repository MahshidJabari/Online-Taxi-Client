package com.jabari.client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jabari.client.R;
import com.jabari.client.controller.LoginController;
import com.jabari.client.global.GeneralResponse;
import com.jabari.client.global.GlobalVariables;
import com.jabari.client.global.PrefManager;
import com.jabari.client.network.config.ApiClient;
import com.jabari.client.network.config.ApiInterface;
import com.jabari.client.network.model.User;


import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText et_phoneNum,et_verify_code;
    private Button btn_send;
    private FloatingActionButton fab_login;
    private ProgressBar pbLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_phoneNum = findViewById(R.id.et_phoneNum);
        et_verify_code = findViewById(R.id.et_validationcode);
        btn_send = findViewById(R.id.btn_send);
        fab_login = findViewById(R.id.btn_login);
        pbLoading = findViewById(R.id.pbLoading);
        fab_login.bringToFront();



        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValidPhone(et_phoneNum.getText().toString())){
                    btn_send.setVisibility(View.GONE);
                    pbLoading.setVisibility(View.VISIBLE);
                    getVerifyCode(et_phoneNum.getText().toString());
                }
            }
        });
        fab_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GlobalVariables.getVerify){
                check_login(et_phoneNum.getText().toString()
                            ,et_verify_code.getText().toString());}
                else
                    Toast.makeText(LoginActivity.this,"کد فعالسازی به درستی وارد نشده!",Toast.LENGTH_SHORT).show();
            }
        });

       }
    private boolean isValidPhone(String phone){

        if(!Pattern.matches("^0(9)\\d{9}$",phone)){
            Toast.makeText(LoginActivity.this,"شماره ی موبایل اشتباه وارد شده!",Toast.LENGTH_LONG).show();
            et_phoneNum.getText().clear();
            return false;
        }
        else
            return true;
    }

    private void check_login(final String phoneNum, final String verify_code) {



        ApiInterface.LoginUserCallback loginUserCallback = new ApiInterface.LoginUserCallback() {

            @Override
            public void onResponse(GeneralResponse generalResponse, User user, String token) {


                if(generalResponse.getSuccess()){

                    saveLoginPreferences(token, String.valueOf(user.getMobileNum()));

                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                 }
            }

            @Override
            public void onFailure(String error) {

            }

        };

        LoginController loginUserController = new LoginController(loginUserCallback);
        loginUserController.Do(phoneNum,verify_code);
    }

    private void getVerifyCode(final String phoneNum) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.getVerifyCode(phoneNum);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){

                    pbLoading.setVisibility(View.GONE);
                    btn_send.setVisibility(View.VISIBLE);

                    GlobalVariables.getVerify = true;
                    Toast.makeText(LoginActivity.this,"کد فعالسازی ارسال شد",Toast.LENGTH_LONG).show();
                    Toast.makeText(LoginActivity.this,phoneNum.substring(5,10),Toast.LENGTH_LONG).show();
                   }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                pbLoading.setVisibility(View.GONE);
                btn_send.setVisibility(View.VISIBLE);
                GlobalVariables.getVerify = false;
                Toast.makeText(LoginActivity.this,"مجددا تلاش کنید",Toast.LENGTH_LONG).show();

            }
          });

        if(!GlobalVariables.getVerify){
            pbLoading.setVisibility(View.GONE);
            btn_send.setVisibility(View.VISIBLE);
           }

    }

    private void saveLoginPreferences(String token,String user){

        PrefManager prefManager = new PrefManager(getBaseContext());
        prefManager.setToken(token);
        prefManager.setPhoneNum(user);
        GlobalVariables.tok = token;
        GlobalVariables.phoneUser = user;
        GlobalVariables.isLogin = true;


    }
   }
