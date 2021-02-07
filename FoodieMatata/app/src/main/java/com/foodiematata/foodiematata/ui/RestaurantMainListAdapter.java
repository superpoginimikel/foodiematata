package com.foodiematata.foodiematata.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodiematata.foodiematata.HelperClass;
import com.foodiematata.foodiematata.R;
import com.foodiematata.foodiematata.db.entity.RestaurantEntity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RestaurantMainListAdapter extends RecyclerView.Adapter<RestaurantMainListAdapter.RestaurantViewHolder> {

    public static final String EXTRA_REPLY_RESTAURANT_ID = "com.foodiematata.foodiematata.REPLY_RESTURANT_ID";

    private final LayoutInflater mInflater;
    private final Context mContext;
    private List<RestaurantEntity> restaurants;

    public RestaurantMainListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.restaurant_main_list_item, parent, false);
        return new RestaurantViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RestaurantViewHolder holder, final int position) {
        if (restaurants != null){
            final RestaurantEntity current = restaurants.get(position);
            holder.restoName.setText((current.getName()));
            holder.restoCategory.setText((current.getCategory()));
            holder.restoLocation.setText((current.getLocation()));
            holder.restoPrice.setText((current.getPrice()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent infoIntent = new Intent(mContext, RestaurantInfoActivity.class);
                    infoIntent.putExtra(EXTRA_REPLY_RESTAURANT_ID, current.getId());
                    mContext.startActivity(infoIntent);
                }
            });

            String imagePath = current.getImagePath();
            HelperClass.setImage(holder.restoImageView, imagePath);
        }
        else {
//            holder.wordItemView.setText("No restaurant found");
        }
    }

    //
    public void setRestaurant(List<RestaurantEntity> restaurants){
        this.restaurants = restaurants;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (restaurants != null) {
            return restaurants.size();
        }
        else {
            return 0;
        }
    }

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
