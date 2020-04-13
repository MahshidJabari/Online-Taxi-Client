package com.jabari.client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jabari.client.R;
import com.jabari.client.activity.main.SearchActivity;
import com.jabari.client.network.model.Item;
import com.jabari.client.network.model.Location;

import org.neshan.core.LngLat;

import java.util.List;

public class SearchAdapter extends
        RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private Context context;
    private RecyclerView recyclerView;
    private List<Item> resultList;
    private SearchActivity.IOnSearchItemListener iOnSearchItemListener;

    public SearchAdapter(Context context, RecyclerView recyclerView, List<Item> list, SearchActivity.IOnSearchItemListener iOnSearchItemListener) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.resultList = list;
        this.iOnSearchItemListener = iOnSearchItemListener;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_search, parent, false);
        return new SearchAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int i) {

        if (resultList.get(i).getRegion() != null)
            holder.tv_address.setText(resultList.get(i).getRegion());
        holder.tv_title.setText(resultList.get(i).getTitle());
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public void updateList(List<Item> items) {
        this.resultList = items;
        notifyDataSetChanged();
    }


    public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_address, tv_title;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_address = itemView.findViewById(R.id.tv_search_address);
            tv_title = itemView.findViewById(R.id.tv_search_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Location location = resultList.get(getAdapterPosition()).getLocation();
            LngLat lngLat = new LngLat(location.getX(), location.getY());
            String address = resultList.get(getAdapterPosition()).getTitle();
            iOnSearchItemListener.onSearchItemClick(lngLat, address);
        }
    }

}
