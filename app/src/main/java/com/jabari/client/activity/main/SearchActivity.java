package com.jabari.client.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jabari.client.R;
import com.jabari.client.adapter.SearchAdapter;
import com.jabari.client.controller.SearchController;
import com.jabari.client.custom.GlobalVariables;
import com.jabari.client.network.config.ApiInterface;
import com.jabari.client.network.model.Item;

import org.neshan.core.LngLat;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SearchActivity extends AppCompatActivity {

    private String text;
    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private double lat;
    private double lng;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Bundle args = getIntent().getExtras();
        text = args.getString("index");
        String t = text.replace("\n", "");
        lat = args.getDouble("lat");
        lng = args.getDouble("lng");
        search(t, lat, lng);

    }

    private void search(String term, final double lat, final double lng) {

        ApiInterface.SearchCallback searchCallback = new ApiInterface.SearchCallback() {
            @Override
            public void onResponse(List<Item> list) {

                setUpSearchRecycler(list);
            }

            @Override
            public void onFailure(String err) {
                Toast.makeText(SearchActivity.this, "ارتباط برقرار نشد!", Toast.LENGTH_SHORT).show();
            }
        };
        SearchController controller = new SearchController(searchCallback);
        controller.Do(term, lat, lng);

    }

    private void setUpSearchRecycler(final List<Item> list) {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.search_recycler);
        recyclerView.setLayoutManager(manager);
        adapter = new SearchAdapter(this, recyclerView, list, new SearchActivity.IOnSearchItemListener() {
            @Override
            public void onSearchItemClick(LngLat lngLat,String address) {

                Bundle bundle = new Bundle();
                bundle.putDouble("lat", lngLat.getY());
                bundle.putDouble("lng", lngLat.getX());
                bundle.putString("address",address);
                if (GlobalVariables.start == null) {
                    Intent intent = new Intent(SearchActivity.this, StartPosActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SearchActivity.this, EndPosActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public interface IOnSearchItemListener {
        void onSearchItemClick(LngLat lngLat,String address);
    }

}
