package com.jabari.client.activity.report;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jabari.client.R;
import com.jabari.client.custom.DigitConverter;
import com.jabari.client.network.model.Travel;

import org.neshan.core.LngLat;
import org.neshan.core.Range;
import org.neshan.layers.Layer;
import org.neshan.layers.VectorElementLayer;
import org.neshan.services.NeshanMapStyle;
import org.neshan.services.NeshanServices;
import org.neshan.styles.MarkerStyle;
import org.neshan.styles.MarkerStyleCreator;
import org.neshan.ui.MapView;
import org.neshan.utils.BitmapUtils;
import org.neshan.vectorelements.Marker;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ArchiveDetailActivity extends AppCompatActivity {
    private TextView tv_have_return, tv_no_return, tv_credit, tv_cache, tv_start_location,
            tv_destination, tv_accept_req_date, tv_receive_date, tv_give_date, tv_date, tv_code;
    private LinearLayout lin;
    private Travel travel;
    private MapView map;
    private boolean startPosSelected = false;
    VectorElementLayer startMarker, endMarker;
    final int POI_INDEX = 1;
    final int BASE_MAP_INDEX = 0;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);

        travel = (Travel) getIntent().getSerializableExtra("travel");
        setView();
        setText();
        initLayoutReferences();
    }

    private void setView() {
        lin = findViewById(R.id.lin1);
        lin.bringToFront();
        tv_have_return = findViewById(R.id.tv_have_return);
        tv_no_return = findViewById(R.id.tv_no_return);
        tv_credit = findViewById(R.id.tv_credit);
        tv_cache = findViewById(R.id.tv_cache);
        tv_start_location = findViewById(R.id.tv_start_location);
        tv_destination = findViewById(R.id.tv_destination);
        tv_accept_req_date = findViewById(R.id.tv_accept_req_date);
        tv_receive_date = findViewById(R.id.tv_receive_date);
        tv_give_date = findViewById(R.id.tv_give_date);
        tv_date = findViewById(R.id.tv_date);
        tv_code = findViewById(R.id.tv_code);
    }

    private void setText() {
        if (travel != null) {
            tv_accept_req_date.setText(DigitConverter.convert(travel.getCreatedAt()));
            tv_receive_date.setText(DigitConverter.convert(travel.getCreatedAt()));
            tv_give_date.setText(DigitConverter.convert(travel.getCreatedAt()));
            tv_date.setText(DigitConverter.convert(travel.getCreatedAt()));
            tv_code.setText(travel.getId());
            if (travel.isHaveReturn())
                tv_have_return.setTextColor(getResources().getColor(R.color.green));
            else
                tv_no_return.setTextColor(getResources().getColor(R.color.green));
            if (travel.isPayByRequest())
                tv_start_location.setTextColor(getResources().getColor(R.color.green));
            else
                tv_destination.setTextColor(getResources().getColor(R.color.green));
            if (travel.getCashPayment() != 0)
                tv_cache.setTextColor(getResources().getColor(R.color.green));
            else
                tv_credit.setTextColor(getResources().getColor(R.color.green));
        }

    }

    public void OnBack(View view) {
        startActivity(new Intent(ArchiveDetailActivity.this, ArchiveActivity.class));
    }

    private void initLayoutReferences() {
        map = findViewById(R.id.MapView);
        initMap();
    }

    private void initMap() {
        // Creating a VectorElementLayer(called userMarkerLayer) to add user marker to it and adding it to map's layers
        startMarker = NeshanServices.createVectorElementLayer();
        endMarker = NeshanServices.createVectorElementLayer();
        map.getLayers().add(startMarker);
        map.getLayers().add(endMarker);

        // add Standard_day map to layer BASE_MAP_INDEX
        map.getOptions().setZoomRange(new Range(4.5f, 18f));
        Layer baseMap = NeshanServices.createBaseMap(NeshanMapStyle.STANDARD_DAY, getCacheDir() + "/baseMap", 10);
        map.getLayers().insert(BASE_MAP_INDEX, baseMap);
        Layer poiLayer = NeshanServices.createPOILayer(false, getCacheDir() + "/poiLayer", 10);
        map.getLayers().insert(POI_INDEX, poiLayer);

        map.getLayers().insert(BASE_MAP_INDEX, NeshanServices.createBaseMap(NeshanMapStyle.STANDARD_DAY));

        // Setting map focal position to a fixed position and setting camera zoom
        map.setFocalPointPosition(new LngLat(51.330743, 35.767234), 0);
        if (travel.getLocation().get(0) != null & travel.getLocation().get(1) != null) {
            addMarker(new LngLat(Double.parseDouble(travel.getLocation().get(0)), Double.parseDouble(travel.getLocation().get(1))));
            startPosSelected = true;

        }
        /*if (travel.getDestination().get(0) != null & travel.getDestination().get(1) != null) {
            addMarker(new LngLat(Double.parseDouble(travel.getDestination().get(0)), Double.parseDouble(travel.getDestination().get(1))));
        }*/

        map.setZoom(15, 0);
    }

    private void addMarker(LngLat loc) {

        if (!startPosSelected) {
            MarkerStyleCreator markStCr = new MarkerStyleCreator();
            markStCr.setSize(40f);
            markStCr.setBitmap(BitmapUtils.createBitmapFromAndroidBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.location_black)));
            MarkerStyle markSt = markStCr.buildStyle();
            // Creating marker
            Marker marker = new Marker(loc, markSt);
            // Adding marker to markerLayer, or showing marker on map!
            startMarker.add(marker);
            focusOnLocation(loc);

        }
      /*  else {  MarkerStyleCreator markStCr = new MarkerStyleCreator();
            markStCr.setSize(40f);
            markStCr.setBitmap(BitmapUtils.createBitmapFromAndroidBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.location_black)));
            MarkerStyle markSt = markStCr.buildStyle();
            // Creating marker
            Marker marker = new Marker(loc, markSt);
            // Adding marker to markerLayer, or showing marker on map!
            endMarker.add(marker);}*/

    }

    public void focusOnLocation(LngLat location) {
        if (startMarker != null) {
            map.setFocalPointPosition(location, 0.25f);
            map.setZoom(15, 0.25f);
        }
    }

    public void OnSendEmailClick(View view) {

    }

}
