package com.foodiematata.foodiematata;

import android.content.Context;
import android.content.Intent;
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
public class RestaurantMainListAdapter extends RecyclerView.Adapter<RestaurantMainListAdapter.RestaurantViewHolder> {

    public static final String EXTRA_REPLY_RESTAURANT_ID = "com.foodiematata.foodiematata.REPLY_RESTURANT_ID";

    private final LayoutInflater mInflater;
    private final Context mContext;
    private List<Restaurant> restaurants;

    RestaurantMainListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    // inflate a view item and return the viewHolder containing it
    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.restaurant_main_list_item, parent, false);
        return new RestaurantViewHolder(itemView);
    }

    // set content of view item at a certain position
    @Override
    public void onBindViewHolder(@NonNull final RestaurantViewHolder holder, final int position) {
        if (restaurants != null){
            final Restaurant current = restaurants.get(position);
            holder.restoName.setText((current.getName()));
            holder.restoCategory.setText((current.getCategory()));
            holder.restoLocation.setText((current.getLocation()));
            holder.restoPrice.setText((current.getPrice()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent infoIntent = new Intent(mContext, RestaurantInfo.class);
                    infoIntent.putExtra(EXTRA_REPLY_RESTAURANT_ID, current.getId());
                    mContext.startActivity(infoIntent);
//                    Toast.makeText(mContext, "Recycle Click" + position, Toast.LENGTH_SHORT).show();
                }
            });

            String imagePath = current.getImagePath();
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            if (bitmap != null)
            {
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
    void setRestaurant(List<Restaurant> restaurants){
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
        private final TextView restoCategory;
        private final TextView restoLocation;
        private final TextView restoPrice;

        private RestaurantViewHolder(View itemView) {
            super(itemView);
            restoName = itemView.findViewById(R.id.restoName);
            restoCategory = itemView.findViewById(R.id.restoCategory);
            restoLocation = itemView.findViewById(R.id.restoLocation);
            restoPrice = itemView.findViewById(R.id.restoPrice);
            restoImageView = itemView.findViewById(R.id.restoImageView);
        }
    }
}
