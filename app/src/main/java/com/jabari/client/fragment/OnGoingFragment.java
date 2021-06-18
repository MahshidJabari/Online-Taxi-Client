package com.jabari.client.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.jabari.client.R;
import com.jabari.client.custom.DigitConverter;
import com.jabari.client.custom.GlobalVariables;
import com.jabari.client.network.model.Travel;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnGoingFragment extends Fragment {

    private TextView tv_destination, tv_start_address, tv_payment_way, tv_cache,tv_alert;
    private RelativeLayout rel;

    public OnGoingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_ongoing_tab, container, false);
        tv_destination = root.findViewById(R.id.tv_destination);
        tv_start_address = root.findViewById(R.id.tv_start_location);
        tv_alert = root.findViewById(R.id.tv_alert);
        rel = root.findViewById(R.id.rel);
        tv_payment_way = root.findViewById(R.id.tv_payment_way);
        tv_cache = root.findViewById(R.id.tv_cache);
        tv_destination.setText(GlobalVariables.destination);
        tv_start_address.setText(GlobalVariables.start_address);
        if (GlobalVariables.isOnlinePayment)
            tv_payment_way.setText("آنلاین");
        else
            tv_payment_way.setText("نقدی");
        if (GlobalVariables.calculated != null) {
            tv_cache.setText(DigitConverter.convert(GlobalVariables.calculated));
        }
        else {
            rel.setVisibility(View.GONE);
            tv_alert.setVisibility(View.VISIBLE);
        }
        return root;
    }

}
