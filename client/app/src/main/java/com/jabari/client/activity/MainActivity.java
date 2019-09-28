package com.jabari.client.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.jabari.client.R;
import com.jabari.client.adapter.SearchAdapter;
import com.jabari.client.entities.Item;
import com.jabari.client.network.model.NeshanSearch;
import com.jabari.client.network.view.ApiClient;
import com.jabari.client.network.view.ApiInterface;

import org.neshan.core.LngLat;
import org.neshan.services.NeshanMapStyle;
import org.neshan.services.NeshanServices;
import org.neshan.ui.MapView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private MapView map;
    private List<Item> items;
    private SearchAdapter adapter;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpNavigationView();

        map = findViewById(R.id.map);
        //set map focus position
        LngLat focalPoint = new LngLat(51.336434, 35.6990015);
        map.setFocalPointPosition(focalPoint, 0f);
        map.setZoom(15f, 1);
        //add basemap layer
        map.getLayers().add(NeshanServices.createBaseMap(NeshanMapStyle.STANDARD_DAY));
    }

    private void doSearch(String term) {
        final double lat = map.getFocalPointPosition().getY();
        final double lng = map.getFocalPointPosition().getX();
        final String requestURL = "https://api.neshan.org/v1/search?term=" + term + "&lat=" + lat + "&lng=" + lng;

        ApiInterface api = ApiClient.getRetrofitInstance().create(ApiInterface.class);
        Call<NeshanSearch> call = api.getNeshanSearch(requestURL);

        call.enqueue(new Callback<NeshanSearch>() {
            @Override
            public void onResponse(Call<NeshanSearch> call, Response<NeshanSearch> response) {
                if (response.isSuccessful()) {
                    NeshanSearch neshanSearch = response.body();
                    items = neshanSearch.getItems();
                    adapter.updateList(items);
                } else {
                    Toast.makeText(MainActivity.this, "خطا در برقراری ارتباط!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NeshanSearch> call, Throwable t) {
                Toast.makeText(MainActivity.this, "ارتباط برقرار نشد!", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void setUpNavigationView(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setHomeAsUpIndicator(R.drawable.ic_list_white_24dp);
        actionbar.setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.credit_management:
                startActivity(new Intent(this,ManagementActivity.class));
                break;
            case R.id.req_list :
                startActivity(new Intent(this,RequestActivity.class));
                break;
            case R.id.activities_archive:
                startActivity(new Intent(this,ArchiveActivity.class));
                break;
            case R.id.selected_addresses:
                startActivity(new Intent(this,AddressActivity.class));
                break;
        }
        return true;
    }
}
