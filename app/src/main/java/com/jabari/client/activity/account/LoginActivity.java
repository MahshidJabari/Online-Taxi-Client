package com.jabari.client.activity.account;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jabari.client.R;
import com.jabari.client.activity.main.MainActivity;
import com.jabari.client.controller.LoginController;
import com.jabari.client.custom.GeneralResponse;
import com.jabari.client.custom.GlobalVariables;
import com.jabari.client.custom.PrefManager;
import com.jabari.client.network.config.ApiInterface;
import com.jabari.client.network.model.User;

import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {

    private EditText et_phoneNum, et_verify_code;
    private Button btn_send;
    private TextView tv_laws;
    private FloatingActionButton fab_login;
    private CheckBox checkBox;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setView();
        setFab_loginUnClickable();
        getLawsOnclick();

    }

    private void setView() {
        et_phoneNum = findViewById(R.id.et_phoneNum);
        et_verify_code = findViewById(R.id.et_validationcode);
        btn_send = findViewById(R.id.btn_send);
        fab_login = findViewById(R.id.btn_login);
        fab_login.bringToFront();
        tv_laws = findViewById(R.id.tv_laws);
        checkBox = findViewById(R.id.checkbox);

    }

    public void fabOnclick(View view) {
        fab_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GlobalVariables.getVerify) {
                    if (checkBox.isChecked())
                        check_login(et_phoneNum.getText().toString()
                                , et_verify_code.getText().toString());
                    else
                        Toasty.error(LoginActivity.this, "لطفا قوانین و مقررات را تایید کنید!", Toasty.LENGTH_LONG).show();
                } else
                    Toasty.error(LoginActivity.this, "کد فعالسازی به درستی وارد نشده!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean isValidPhone(String phone) {

        if (!Pattern.matches("^0(9)\\d{9}$", phone)) {
            Toasty.error(LoginActivity.this, "شماره ی موبایل اشتباه وارد شده!", Toast.LENGTH_LONG).show();
            et_phoneNum.getText().clear();
            btn_send.setBackground(getResources().getDrawable(R.drawable.back_thirty_radius_gray));
            return false;
        } else
            return true;
    }

    public void OnClickSendVerifyCode(View view) {
        final String phoneNumber = et_phoneNum.getText().toString();
        if (!isValidPhone(phoneNumber))
            Toast.makeText(this, "Phone number is not valid!", Toast.LENGTH_SHORT);

        LoginController loginController = new LoginController(new ApiInterface.UserVerifyCodeCallback() {
            @Override
            public void onResponse(GeneralResponse generalResponse) {
                GlobalVariables.getVerify = true;
                setFab_loginClickable();
                Toast.makeText(LoginActivity.this, "کد فعالسازی ارسال شد", Toast.LENGTH_LONG).show();
                Toast.makeText(LoginActivity.this, phoneNumber.substring(6, 11), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(String error) {
                setFab_loginUnClickable();
                GlobalVariables.getVerify = false;
                Toast.makeText(LoginActivity.this, "مجددا تلاش کنید", Toast.LENGTH_LONG).show();
            }
        });

        loginController.VerifyCode(phoneNumber);

    }

    private void setFab_loginClickable() {
        fab_login.setBackgroundTintList(getResources().getColorStateList(R.color.green));
        fab_login.setClickable(true);

    }

    private void setFab_loginUnClickable() {

        fab_login.setBackgroundTintList(getResources().getColorStateList(R.color.darkGray));
        fab_login.setClickable(false);
    }

    private void check_login(final String phoneNum, final String verify_code) {


        ApiInterface.LoginUserCallback loginUserCallback = new ApiInterface.LoginUserCallback() {

            @Override
            public void onResponse(GeneralResponse generalResponse, User user, String token) {


                if (generalResponse.getSuccess()) {

                    saveLoginPreferences(token, String.valueOf(user.getMobileNum()));

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }

            @Override
            public void onFailure(String error) {

            }

        };

        LoginController loginUserController = new LoginController(loginUserCallback);
        loginUserController.Do(phoneNum, verify_code);
    }

    private void saveLoginPreferences(String token, String user) {

        PrefManager prefManager = new PrefManager(getBaseContext());
        prefManager.setToken(token);
        prefManager.setPhoneNum(user);
        GlobalVariables.tok = token;
        GlobalVariables.phoneUser = user;
        GlobalVariables.isLogin = true;


    }

    private void getLawsOnclick() {

        tv_laws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showLawsDialog(getLaws());
            }
        });
    }

    public void showLawsDialog(String laws) {

        if (laws != null) {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.alertdialog);
            TextView body = dialog.findViewById(R.id.tv_dialog);
            body.setText(laws);
            Button button = dialog.findViewById(R.id.btn_ok);
            dialog.show();
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        } else
            Toasty.error(LoginActivity.this, "خطا در بارگذاری اطلاعات", Toasty.LENGTH_LONG).show();

    }

    private String getLaws() {

        ApiInterface.GetLawsCallback getLawsCallback = new ApiInterface.GetLawsCallback() {
            @Override
            public void onResponse(GeneralResponse generalResponse) {

            }

            @Override
            public void onFailure(String error) {

            }
        };
        LoginController loginLawController = new LoginController(getLawsCallback);
        return loginLawController.getLaws();
    }
}
