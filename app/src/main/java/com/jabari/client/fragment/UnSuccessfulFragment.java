package com.jabari.client.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.jabari.client.R;
import com.jabari.client.custom.GlobalVariables;


public class UnSuccessfulFragment extends Fragment {
    private TextView tv_alert;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_unsuccessful, container, false);
        tv_alert = view.findViewById(R.id.tv_alert);
        if (GlobalVariables.hasReceivedResponse)
            tv_alert.setText("رلننده در حال آمدن به سمت شماست");
        return view;

    }


}
