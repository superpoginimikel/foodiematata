package com.foodiematata.foodiematata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/*
* adapter class for recyclerView
* */

/*
* Adapter is used to connect data to view items
* */
public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder> {

    private final LayoutInflater mInflater;
    private List<Restaurant> restaurants;

    RestaurantListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    // inflate a view item and return the viewHolder containing it
    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new RestaurantViewHolder(itemView);
    }

    // set content of view item at a certain position
    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        if (restaurants != null){
            Restaurant current = restaurants.get(position);
//            holder.wordItemView.setText(current.getRestaurantName());
        }
        else{
//            holder.wordItemView.setText("No restaurant found");
        }
    }

    //
    void setWords(List<Restaurant> restaurants){
        this.restaurants = restaurants;
        notifyDataSetChanged();
    }

    //get word at position
    public Restaurant getWordAtPosition (int position) {
        return restaurants.get(position);
    }

    // return the number of items in the RecyclerView
    @Override
    public int getItemCount() {
        if (restaurants != null) {
            return restaurants.size();
        }
        else {
            return 0;
        }
    }

    /*
    * ViewHolder is in charge of displaying one item using the separate recyclerView layout
    * */
    class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private final ImageView restoImageView;
        private final TextView restoTitle;
        private final TextView restoCategory;
        private final TextView restoPrice;

        private RestaurantViewHolder(View itemView) {
            super(itemView);
            restoTitle = itemView.findViewById(R.id.restoTitle);
            restoCategory = itemView.findViewById(R.id.restoCategory);
            restoImageView = itemView.findViewById(R.id.restoImageView);
            restoPrice = itemView.findViewById(R.id.restoPrice);
        }
    }
}
