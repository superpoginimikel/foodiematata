package com.foodiematata.foodiematata;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
            holder.restoName.setText((current.getName()));
            holder.restoDescription.setText((current.getDescription()));
            holder.restoLocation.setText((current.getLocation()));
            holder.restoPrice.setText((current.getPrice()));
            holder.restoPhone.setText((current.getPhone()));

            byte[] imageByte = current.getImage();
            if (imageByte != null)
            {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                holder.restoImageView.setImageBitmap(bitmap);
            }
            else
            {
                // Show a placeholder image to add
            }
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
        private final TextView restoName;
        private final TextView restoDescription;
        private final TextView restoLocation;
        private final TextView restoPhone;
        private final TextView restoPrice;

        private RestaurantViewHolder(View itemView) {
            super(itemView);
            restoName = itemView.findViewById(R.id.restoName);
            restoDescription = itemView.findViewById(R.id.restoDescription);
            restoLocation = itemView.findViewById(R.id.restoLocation);
            restoPhone = itemView.findViewById(R.id.restoPhone);
            restoPrice = itemView.findViewById(R.id.restoPrice);
            restoImageView = itemView.findViewById(R.id.restoImageView);
        }
    }
}
