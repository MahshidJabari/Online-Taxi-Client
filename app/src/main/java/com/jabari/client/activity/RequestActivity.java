package com.jabari.client.activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.jabari.client.R;
import com.jabari.client.global.GlobalVariables;
import com.karumi.dexter.BuildConfig;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;

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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import info.androidhive.fontawesome.FontDrawable;

public class RequestActivity extends AppCompatActivity {


    private static final String TAG = RequestActivity.class.getName();
    VectorElementLayer userMarkerLayer;

    private List<Integer> vehicle_list;
    private List<String> vehicle_name;
    private Button btn_right, btn_left;
    private TextView tv_vehicle_name,tv_return;
    private EditText et_search;
    private FloatingActionButton fab_left, fab_mid, fab_right,fab_explore;
    private MapView mapView;
    // used to track request permissions
    final int REQUEST_CODE = 123;
    // location updates interval - 1 sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1000;
    // fastest updates interval - 1 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 1000;
    // User's current location
    private Location userLocation;
    private FusedLocationProviderClient fusedLocationClient;
    private SettingsClient settingsClient;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingsRequest;
    private LocationCallback locationCallback;
    private String lastUpdateTime;
    // boolean flag to toggle the ui
    private Boolean mRequestingLocationUpdates;
    final int BASE_MAP_INDEX = 0;
    final int POI_INDEX = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_request);
        tv_return= findViewById(R.id.tv_return);
        tv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RequestActivity.this,MainActivity.class));
            }
        });
        setVehicle();
        fab_explore = findViewById(R.id.fbtn_explore);
        FontDrawable drawable = new FontDrawable(this,R.string.fa_file_export_solid,true,false);
        drawable.setTextColor(ContextCompat.getColor(this, android.R.color.holo_blue_light));
        fab_explore.setImageDrawable(drawable);
        fab_explore.bringToFront();
        fab_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                focusOnUserLocation(view);
            }
        });
     }

    public void setVehicle() {
        vehicle_list = new ArrayList<>();
        vehicle_name = new ArrayList<>();
        vehicle_list.add(R.drawable.motor);
        vehicle_list.add(R.drawable.motor_peyk);
        vehicle_list.add(R.drawable.car);
        vehicle_list.add(R.drawable.van);
        vehicle_list.add(R.drawable.van2);
        vehicle_name.add("تاکسی موتور");
        vehicle_name.add("پیک موتوری");
        vehicle_name.add("ُسواری");
        vehicle_name.add("وانت سنگین");
        vehicle_name.add("وانت بار");

        tv_vehicle_name = findViewById(R.id.tv_vehicle_name);
        btn_right = findViewById(R.id.btn_right);
        btn_left = findViewById(R.id.btn_left);
        fab_left = findViewById(R.id.fab_left);
        fab_mid = findViewById(R.id.fab_mid);
        fab_right = findViewById(R.id.fab_right);

        fab_left.bringToFront();
        fab_mid.bringToFront();
        fab_right.bringToFront();


        fab_right.setImageResource(vehicle_list.get(GlobalVariables.v));
        fab_mid.setImageResource(vehicle_list.get(GlobalVariables.v + 1));
        fab_left.setImageResource(vehicle_list.get(GlobalVariables.v + 2));
        tv_vehicle_name.setText(vehicle_name.get(GlobalVariables.v + 1));
        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fab_right.setImageResource(vehicle_list.get(GlobalVariables.v));
                fab_mid.setImageResource(vehicle_list.get((GlobalVariables.v + 1) % vehicle_list.size()));
                fab_left.setImageResource(vehicle_list.get((GlobalVariables.v + 2) % vehicle_list.size()));
                tv_vehicle_name.setText(vehicle_name.get((GlobalVariables.v + 1) % vehicle_list.size()));
                GlobalVariables.v = (GlobalVariables.v + 1) % vehicle_list.size();


            }
        });
        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab_right.setImageResource(vehicle_list.get(GlobalVariables.v));
                fab_mid.setImageResource(vehicle_list.get((GlobalVariables.v + 1) % vehicle_list.size()));
                fab_left.setImageResource(vehicle_list.get((GlobalVariables.v + 2) % vehicle_list.size()));
                tv_vehicle_name.setText(vehicle_name.get((GlobalVariables.v + 1) % vehicle_list.size()));
                GlobalVariables.v = ((GlobalVariables.v - 1));
                if(GlobalVariables.v < 0)
                    GlobalVariables.v = vehicle_list.size()-1;

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // everything related to ui is initialized here
        initLayoutReferences();
        initLocation();
        startReceivingLocationUpdates();
    }
    private void initLayoutReferences() {

        mapView = findViewById(R.id.MapView);
        mapView.getLayers().add(NeshanServices.createBaseMap(NeshanMapStyle.STANDARD_DAY));
        et_search = findViewById(R.id.et_search);
        et_search.bringToFront();
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        initMap();

       }
    private void initMap(){
        // add Standard_day map to layer BASE_MAP_INDEX
        mapView.getOptions().setZoomRange(new Range(4.5f, 18f));

        // Cache base map
        // Cache size is 10 MB
        Layer baseMap = NeshanServices.createBaseMap(NeshanMapStyle.STANDARD_DAY, getCacheDir()+"/baseMap", 10);
        mapView.getLayers().insert(BASE_MAP_INDEX, baseMap);

        // Cache POI layer
        // Cache size is 10 MB
        Layer poiLayer = NeshanServices.createPOILayer(false, getCacheDir() + "/poiLayer", 10);
        mapView.getLayers().insert(POI_INDEX, poiLayer);

        // Setting map focal position to a fixed position and setting camera zoom
        mapView.setFocalPointPosition(new LngLat(51.330743, 35.767234),0 );
        mapView.setZoom(14,0);
    }


    private void initLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        settingsClient = LocationServices.getSettingsClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                userLocation = locationResult.getLastLocation();
                lastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                onLocationChange();
            }
        };

        mRequestingLocationUpdates = false;

        locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        locationSettingsRequest = builder.build();

    }
    /**
     * Starting location updates
     * Check whether location settings are satisfied and then
     * location updates will be requested
     */
    private void startLocationUpdates() {
        settingsClient
                .checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");

                        //noinspection MissingPermission
                        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

                        onLocationChange();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(RequestActivity.this, REQUEST_CODE);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);

                                Toast.makeText(RequestActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                        onLocationChange();
                    }
                });
    }
    public void stopLocationUpdates() {
        // Removing location updates
        fusedLocationClient
                .removeLocationUpdates(locationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Location updates stopped!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

public void startReceivingLocationUpdates() {
        // Requesting ACCESS_FINE_LOCATION using Dexter library
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mRequestingLocationUpdates = true;
                        startLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // open device settings when the permission is
                            // denied permanently
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }

                }).check();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.e(TAG, "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.e(TAG, "User chose not to make required location settings changes.");
                        mRequestingLocationUpdates = false;
                        break;
                }
                break;
        }
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void onLocationChange() {
        if(userLocation != null) {
            addUserMarker(new LngLat(userLocation.getLongitude(), userLocation.getLatitude()));
        }
    }

    // This method gets a LngLat as input and adds a marker on that position
    private void addUserMarker(LngLat loc){
        // Creating marker style. We should use an object of type MarkerStyleCreator, set all features on it
        // and then call buildStyle method on it. This method returns an object of type MarkerStyle
        MarkerStyleCreator markStCr = new MarkerStyleCreator();
        markStCr.setSize(20f);
        markStCr.setBitmap(BitmapUtils.createBitmapFromAndroidBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_location_green)));
        MarkerStyle markSt = markStCr.buildStyle();

        // Creating user marker
        Marker marker = new Marker(loc, markSt);

        // Clearing userMarkerLayer
        userMarkerLayer.clear();

        // Adding user marker to userMarkerLayer, or showing marker on map!
        userMarkerLayer.add(marker);
    }
    public void focusOnUserLocation(View view) {
        if(userLocation != null) {
            mapView.setFocalPointPosition(
                    new LngLat(userLocation.getLongitude(), userLocation.getLatitude()), 0.25f);
            mapView.setZoom(15, 0.25f);
        }
    }
}
