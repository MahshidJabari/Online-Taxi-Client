package com.jabari.client.activity.account;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jabari.client.R;
import com.jabari.client.activity.main.MainActivity;
import com.jabari.client.controller.LoginController;
import com.jabari.client.custom.ExceptionHandler;
import com.jabari.client.custom.GlobalVariables;
import com.jabari.client.custom.PrefManager;
import com.jabari.client.network.config.ApiInterface;
import com.jabari.client.network.model.User;

import java.util.regex.Pattern;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {

    private EditText et_phoneNum, et_verify_code;
    private Button btn_send;
    private FloatingActionButton fab_login;
    private CheckBox checkBox;
    private ExceptionHandler handler;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        handler = new ExceptionHandler(this);


        setView();
        setFab_loginUnClickable();
    }

    private void setView() {
        et_phoneNum = findViewById(R.id.et_phoneNum);
        et_verify_code = findViewById(R.id.et_validationcode);
        btn_send = findViewById(R.id.btn_send);
        fab_login = findViewById(R.id.btn_login);
        fab_login.bringToFront();
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
                        handler.generateError("law");
                } else
                    handler.generateError("wrong_code");
            }
        });

    }

    private boolean isValidPhone(String phone) {

        if (!Pattern.matches("^0(9)\\d{9}$", phone)) {
            et_phoneNum.getText().clear();
            btn_send.setBackground(getResources().getDrawable(R.drawable.back_thirty_radius_gray));
            return false;
        } else
            return true;
    }

    public void OnClickSendVerifyCode(View view) {
        final String phoneNumber = et_phoneNum.getText().toString();
        if (!isValidPhone(phoneNumber))
            handler.generateError("invalid_phone");
        view.setBackgroundDrawable(getResources().getDrawable(R.drawable.back_fifty_radius_gray));
        LoginController loginController = new LoginController(new ApiInterface.UserVerifyCodeCallback() {
            @Override
            public void onResponse() {
                GlobalVariables.getVerify = true;
                setFab_loginClickable();
                handler.generateSuccess("code");
            }

            @Override
            public void onFailure(String error) {
                setFab_loginUnClickable();
                GlobalVariables.getVerify = false;
                handler.generateError(error);
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
            public void onResponse(User user, String token) {
                saveLoginPreferences(token, String.valueOf(user.getMobileNum()));
                startActivity(new Intent(LoginActivity.this, MainActivity.class));

            }

            @Override
            public void onFailure(String error) {
                handler.generateError(error);
            }

        };

        LoginController loginUserController = new LoginController(loginUserCallback);
        loginUserController.check(phoneNum, verify_code);
    }

    private void saveLoginPreferences(String token, String phone) {

        PrefManager prefManager = new PrefManager(getBaseContext());
        prefManager.setToken(token);
        prefManager.setPhoneNum(phone);
        GlobalVariables.tok = token;
        GlobalVariables.phone = phone;
        GlobalVariables.isLogin = true;


    }

    public void getLaws(View view) {

        ApiInterface.GetLawsCallback getLawsCallback = new ApiInterface.GetLawsCallback() {
            @Override
            public void onResponse(String laws) {

                final Dialog dialog = new Dialog(LoginActivity.this);
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

            }

            @Override
            public void onFailure(String error) {
                handler.generateError(error);
            }
        };
        LoginController loginLawController = new LoginController(getLawsCallback);
        loginLawController.getLaws();
    }
}
