package com.foodiematata.foodiematata;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class RestaurantInfo extends AppCompatActivity {

    private Restaurant restaurant;

    private RestaurantViewModel mRestaurantViewModel;
    private ImageView imageView;
    private TextView restoName;
    private TextView restoCategory;
    private TextView restoPrice;
    private TextView restoLocation;
    private TextView restoPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_info);
        cacheViews();

        Intent intent = getIntent();
        Integer restaurantId = intent.getIntExtra(RestaurantMainListAdapter.EXTRA_REPLY_RESTAURANT_ID, 0);
        mRestaurantViewModel = ViewModelProviders.of(this).get(RestaurantViewModel.class);
        restaurant = mRestaurantViewModel.getRestaurantById(restaurantId);
        addValuesToView();
    }

    private void cacheViews()
    {
        imageView = findViewById(R.id.restoImageView);
        restoName = findViewById(R.id.restoName);
        restoCategory = findViewById(R.id.restoCategory);
        restoPrice = findViewById(R.id.restoPrice);
        restoLocation = findViewById(R.id.restoLocation);
        restoPhone = findViewById(R.id.restoPhone);
    }

    private void addValuesToView()
    {
        String imagePath = restaurant.getImagePath();
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        if (bitmap != null)
        {
            imageView.setImageBitmap(bitmap);
        }
        else
        {
            // Show a placeholder image to add
        }

        restoName.setText(restaurant.getName());
        restoCategory.setText(restaurant.getCategory());
        restoPrice.setText(restaurant.getPrice());
        restoLocation.setText(restaurant.getLocation());
        restoPhone.setText(restaurant.getPhone());
    }
}