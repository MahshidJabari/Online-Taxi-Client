package com.jabari.client.activity.main;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.jabari.client.custom.GlobalVariables;
import com.jabari.client.custom.UserLocation;
import com.karumi.dexter.BuildConfig;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;

import org.neshan.core.LngLat;
import org.neshan.core.LngLatVector;
import org.neshan.core.Range;
import org.neshan.geometry.LineGeom;
import org.neshan.graphics.ARGB;
import org.neshan.layers.Layer;
import org.neshan.layers.VectorElementLayer;
import org.neshan.services.NeshanMapStyle;
import org.neshan.services.NeshanServices;
import org.neshan.styles.LineStyle;
import org.neshan.styles.LineStyleCreator;
import org.neshan.styles.MarkerStyle;
import org.neshan.styles.MarkerStyleCreator;
import org.neshan.ui.ClickData;
import org.neshan.ui.ClickType;
import org.neshan.ui.MapEventListener;
import org.neshan.ui.MapView;
import org.neshan.utils.BitmapUtils;
import org.neshan.vectorelements.Line;
import org.neshan.vectorelements.Marker;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EndPosActivity extends AppCompatActivity {

    private static final String TAG = StartPosActivity.class.getName();
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    final int REQUEST_CODE = 123;
    final int POI_INDEX = 1;
    final int BASE_MAP_INDEX = 0;

    VectorElementLayer userMarkerLayer, startMarker, endMarker, lineLayer;
    UserLocation user = new UserLocation(this);
    private List<Integer> vehicle_list;
    private List<String> vehicle_name;
    private Button btn_right, btn_left;
    private TextView tv_vehicle_name;
    private EditText search;
    private FloatingActionButton fab_left, fab_mid, fab_right, fab_explore;
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
    private MapView map;
    private LinearLayout lin_progress;
    private View v, v2;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        setVehicle();
        setUpSearchIcon();
        setUpFabExplore();
        initLayoutReferences();
        initLocation();
        startReceivingLocationUpdates();
        handleInputArgs();


    }

    public void setUpFabExplore() {
        fab_explore = findViewById(R.id.fbtn_explore);
        fab_explore.bringToFront();
        fab_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                focusOnUserLocation();
            }
        });

    }

    public void setUpBack(View View) {
        GlobalVariables.end = null;
        startActivity(new Intent(EndPosActivity.this, StartPosActivity.class));
    }

    public void setUpSearchIcon() {
        search = findViewById(R.id.et_search);
        v = findViewById(R.id.view);
        v.bringToFront();
        search.bringToFront();


        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER) {
                    startSearchActivity(search.getText().toString());
                }
                return false;
            }
        });

    }

    public void setVehicle() {
        lin_progress = findViewById(R.id.lin_progress);

        vehicle_list = new ArrayList<>();
        vehicle_name = new ArrayList<>();
        vehicle_list.add(R.drawable.motor);
        vehicle_list.add(R.drawable.motor_peyk);
        vehicle_list.add(R.drawable.car);
        vehicle_list.add(R.drawable.van);
        vehicle_list.add(R.drawable.van2);
        vehicle_name.add("تاکسی موتور");
        vehicle_name.add("پیک موتوری");
        vehicle_name.add(" سواری");
        vehicle_name.add("وانت سنگین");
        vehicle_name.add("وانت بار");

        tv_vehicle_name = findViewById(R.id.tv_vehicle_name);
        btn_right = findViewById(R.id.btn_right);
        btn_left = findViewById(R.id.btn_left);
        fab_left = findViewById(R.id.fab_left);
        fab_mid = findViewById(R.id.fab_mid);
        fab_right = findViewById(R.id.fab_right);
        v2 = findViewById(R.id.view2);

        v2.bringToFront();
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
                if (GlobalVariables.v < 0)
                    GlobalVariables.v = vehicle_list.size() - 1;

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void initLayoutReferences() {
        map = findViewById(R.id.MapView);
        initMap();
        map.setMapEventListener(new MapEventListener() {
            @Override
            public void onMapClicked(ClickData mapClickInfo) {
                if (mapClickInfo.getClickType() == ClickType.CLICK_TYPE_LONG) {
                    // by calling getClickPos(), we can get position of clicking (or tapping)
                    LngLat clickedLocation = mapClickInfo.getClickPos();
                    // addMarker adds a marker (pretty self explanatory :D) to the clicked location
                }
            }
        });
    }

    private void initMap() {
        // Creating a VectorElementLayer(called userMarkerLayer) to add user marker to it and adding it to map's layers

        userMarkerLayer = NeshanServices.createVectorElementLayer();
        startMarker = NeshanServices.createVectorElementLayer();
        lineLayer = NeshanServices.createVectorElementLayer();
        map.getLayers().add(startMarker);
        map.getLayers().add(userMarkerLayer);
        map.getLayers().add(lineLayer);

        // add Standard_day map to layer BASE_MAP_INDEX
        map.getOptions().setZoomRange(new Range(4.5f, 18f));
        Layer baseMap = NeshanServices.createBaseMap(NeshanMapStyle.STANDARD_DAY, getCacheDir() + "/baseMap", 10);
        map.getLayers().insert(BASE_MAP_INDEX, baseMap);
        Layer poiLayer = NeshanServices.createPOILayer(false, getCacheDir() + "/poiLayer", 10);
        map.getLayers().insert(POI_INDEX, poiLayer);

        map.getLayers().insert(BASE_MAP_INDEX, NeshanServices.createBaseMap(NeshanMapStyle.STANDARD_DAY));

        // Setting map focal position to a fixed position and setting camera zoom
        map.setFocalPointPosition(new LngLat(51.330743, 35.767234), 0);
        map.setZoom(14, 0);
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
                Log.d("location", userLocation.toString());
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
                        Location location = user.getLocation();
                        if (location != null) {
                            Log.d("user", location.toString());
                        }

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
                                    rae.startResolutionForResult(EndPosActivity.this, REQUEST_CODE);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);

                                Toast.makeText(EndPosActivity.this, errorMessage, Toast.LENGTH_LONG).show();
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
        if (userLocation != null) {
            addUserMarker(new LngLat(userLocation.getLongitude(), userLocation.getLatitude()));
        }
    }

    private void addUserMarker(LngLat loc) {
        // Creating marker style. We should use an object of type MarkerStyleCreator, set all features on it
        // and then call buildStyle method on it. This method returns an object of type MarkerStyle
        MarkerStyleCreator markStCr = new MarkerStyleCreator();
        markStCr.setSize(20);
        markStCr.setBitmap(BitmapUtils.createBitmapFromAndroidBitmap
                (BitmapFactory.decodeResource(this.getResources(), R.drawable.user_pinned)));
        MarkerStyle markSt = markStCr.buildStyle();

        // Creating user marker
        Marker marker = new Marker(loc, markSt);

        // Clearing userMarkerLayer
        userMarkerLayer.clear();

        // Adding user marker to userMarkerLayer, or showing marker on map!
        userMarkerLayer.add(marker);
    }

    public void focusOnUserLocation() {
        if (userLocation != null) {
            map.setFocalPointPosition(
                    new LngLat(userLocation.getLongitude(), userLocation.getLatitude()), 0.25f);
            map.setZoom(15, 0.25f);
        }
    }

    private void startSearchActivity(String text) {
        Intent intent = new Intent(EndPosActivity.this, SearchActivity.class);
        Bundle args = new Bundle();
        args.putString("index", text);
        args.putDouble("lat", map.getFocalPointPosition().getY());
        args.putDouble("lng", map.getFocalPointPosition().getX());
        intent.putExtras(args);
        startActivity(intent);
        finish();
    }

    public void handleInputArgs() {
        Bundle args = getIntent().getExtras();
        if (args != null) {
            GlobalVariables.end = new LngLat(args.getDouble("lng"), args.getDouble("lat"));
            addMarker(GlobalVariables.start);
            addMarker(GlobalVariables.end);
            drawLineGeom(GlobalVariables.start, GlobalVariables.end);

        }

    }

    public void focusOnLocation(LngLat location) {
        if (startMarker != null) {
            map.setFocalPointPosition(location, 0.25f);
            map.setZoom(15, 0.25f);
        }
    }

    private void addMarker(LngLat loc) {


        MarkerStyleCreator markStCr = new MarkerStyleCreator();
        markStCr.setSize(40f);
        markStCr.setBitmap(BitmapUtils.createBitmapFromAndroidBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.location)));

        MarkerStyle markSt = markStCr.buildStyle();

        // Creating marker
        Marker marker = new Marker(loc, markSt);

        startMarker.clear();
        // Adding marker to markerLayer, or showing marker on map!
        startMarker.add(marker);
    }

    public LineGeom drawLineGeom(LngLat start, LngLat end) {
        // we clear every line that is currently on map
        lineLayer.clear();
        // Adding some LngLat points to a LngLatVector
        LngLatVector lngLatVector = new LngLatVector();
        lngLatVector.add(start);
        lngLatVector.add(end);
        // Creating a lineGeom from LngLatVector
        LineGeom lineGeom = new LineGeom(lngLatVector);
        // Creating a line from LineGeom. here we use getLineStyle() method to define line styles
        Line line = new Line(lineGeom, getLineStyle());
        // adding the created line to lineLayer, showing it on map
        lineLayer.add(line);
        // focusing camera on first point of drawn line
        map.setFocalPointPosition(new LngLat((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2), 0.25f);
        map.setZoom(12, 0.25f);
        return lineGeom;
    }

    private LineStyle getLineStyle() {
        LineStyleCreator lineStCr = new LineStyleCreator();
        lineStCr.setColor(new ARGB((short) 2, (short) 119, (short) 189, (short) 190));
        lineStCr.setWidth(4f);
        lineStCr.setStretchFactor(0f);
        return lineStCr.buildStyle();
    }

    private void Calculate(){


    }
    @Override
    public void onBackPressed() {
        GlobalVariables.end = null;
        super.onBackPressed();
    }

    public void OnLocationSendListener(View view) {
        startActivity(new Intent(EndPosActivity.this, DetailsActivity.class));
    }

}
