package com.jabari.client.custom;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;

import java.text.DateFormat;
import java.util.Date;

public class UserLocation {

    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private Location userLocation;
    private FusedLocationProviderClient fusedLocationClient;
    private SettingsClient settingsClient;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingsRequest;
    private LocationCallback locationCallback;
    private String lastUpdateTime;
    private Context context;

    public UserLocation(Context context) {
        this.context = context;
    }

    public Location getLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        settingsClient = LocationServices.getSettingsClient(context);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                userLocation = locationResult.getLastLocation();
                lastUpdateTime = DateFormat.getTimeInstance().format(new Date());

            }
        };


        locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        locationSettingsRequest = builder.build();

        return userLocation;
    }

}
