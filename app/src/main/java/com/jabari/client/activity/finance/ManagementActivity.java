package com.jabari.client.activity.finance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jabari.client.R;
import com.jabari.client.activity.main.MainActivity;
import com.jabari.client.custom.DigitConverter;
import com.jabari.client.custom.GlobalVariables;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ManagementActivity extends AppCompatActivity {
    private TextView tv_Credit, tv_increase_credit, tv_after_increase;
    private Button btn_ten, btn_twenty, btn_thirty, btn_fifty, btn_hundred, btn_two_hundred;
    private Button previousButton = null;
    private int credit;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        setViews();
    }

    public void setViews() {
        tv_Credit = findViewById(R.id.tv_credit);
        tv_increase_credit = findViewById(R.id.tv_increase_credit);
        tv_after_increase = findViewById(R.id.tv_after_increase);
        btn_ten = findViewById(R.id.btn_ten);
        btn_twenty = findViewById(R.id.btn_twenty);
        btn_thirty = findViewById(R.id.btn_thirty);
        btn_fifty = findViewById(R.id.btn_fifty);
        btn_hundred = findViewById(R.id.btn_hundred);
        btn_two_hundred = findViewById(R.id.btn_two_hundred);
        //////set text
        tv_Credit.setText(DigitConverter.convert(GlobalVariables.credit));
        btn_ten.setText(DigitConverter.convert("10/000"));
        btn_twenty.setText(DigitConverter.convert("20/000"));
        btn_thirty.setText(DigitConverter.convert("30/000"));
        btn_fifty.setText(DigitConverter.convert("50/000"));
        btn_hundred.setText(DigitConverter.convert("100/000"));
        btn_two_hundred.setText(DigitConverter.convert("200/000"));


    }

    public void OnBackClick(View view) {
        startActivity(new Intent(ManagementActivity.this, MainActivity.class));
    }

    public void OnPaymentClick(View view) {
        startActivity(new Intent(ManagementActivity.this, PaymentActivity.class));
    }

    public void OnCreditClick(View view) {
        if (previousButton != null)
            previousButton.setBackground(getResources().getDrawable(R.drawable.back_twenty_radius_gray));
        view.setBackground(getResources().getDrawable(R.drawable.back_twenty_radius_green));
        Button b = (Button) view;
        tv_increase_credit.setText(DigitConverter.convert(b.getText().toString()));
        credit = Integer.parseInt(GlobalVariables.credit) + Integer.parseInt(b.getText().toString());
        tv_after_increase.setText(DigitConverter.convert(String.valueOf(credit)));
        previousButton = (Button) view;
    }
}
