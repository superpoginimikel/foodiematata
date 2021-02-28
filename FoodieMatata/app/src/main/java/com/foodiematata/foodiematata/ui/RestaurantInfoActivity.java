package com.foodiematata.foodiematata.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodiematata.foodiematata.HelperClass;
import com.foodiematata.foodiematata.R;
import com.foodiematata.foodiematata.db.entity.RestaurantEntity;
import com.foodiematata.foodiematata.db.entity.RestaurantImagesEntity;
import com.foodiematata.foodiematata.viewmodel.RestaurantInfoViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class RestaurantInfoActivity extends AppCompatActivity {

    private RestaurantEntity restaurant;
    private int restaurantId;

    private RestaurantInfoViewModel mRestaurantInfoViewModel;
    private ImageView imageView;
    private TextView restoName;
    private TextView restoCategory;
    private TextView restoPrice;
    private TextView restoLocation;
    private TextView restoPhone;

    // Limited images in list view
    private ImageView imagePreview1;
    private ImageView imagePreview2;
    private ImageView imagePreview3;
    private List<ImageView> imagePreviews = new ArrayList<ImageView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_info);
        cacheViews();

        Intent intent = getIntent();
        restaurantId = intent.getIntExtra(RestaurantMainListAdapter.EXTRA_REPLY_RESTAURANT_ID, 0);
        mRestaurantInfoViewModel = ViewModelProviders.of(this).get(RestaurantInfoViewModel.class);
        subscribeRestaurantInfo();
        subscribeLimitedImages();
        subscribeLimitedComments();
    }

    private void cacheViews()
    {
        imageView = findViewById(R.id.restoImageView);
        restoName = findViewById(R.id.restoName);
        restoCategory = findViewById(R.id.restoCategory);
        restoPrice = findViewById(R.id.restoPrice);
        restoLocation = findViewById(R.id.restoLocation);
        restoPhone = findViewById(R.id.restoPhone);

        imagePreview1 = findViewById(R.id.popularDishesImage1);
        imagePreview2 = findViewById(R.id.popularDishesImage2);
        imagePreview3 = findViewById(R.id.popularDishesImage3);
        imagePreviews.add(imagePreview1);
        imagePreviews.add(imagePreview2);
        imagePreviews.add(imagePreview3);
    }

    private void subscribeRestaurantInfo()
    {
        mRestaurantInfoViewModel.getRestaurantById(restaurantId).observe(this, new Observer<RestaurantEntity>() {
            @Override
            public void onChanged(RestaurantEntity restaurantEntity) {
                restaurant = restaurantEntity;

                String imagePath = restaurant.getImagePath();
                HelperClass.setImage(imageView, imagePath);

                restoName.setText(restaurant.getName());
                restoCategory.setText(restaurant.getCategory());
                restoPrice.setText(restaurant.getPrice());
                restoLocation.setText(restaurant.getLocation());
                restoPhone.setText(restaurant.getPhone());
            }
        });
    }

    public void viewMorePhotos(View view) {

    }

    private void subscribeLimitedImages() {
        mRestaurantInfoViewModel.getLimitedImages(restaurantId).observe(this, new Observer<List<RestaurantImagesEntity>>() {
            @Override
            public void onChanged(List<RestaurantImagesEntity> restaurantImagesEntities) {
                int imageCount = restaurantImagesEntities.size();
                if (imageCount > 0)
                {
                    for (int ctr = 0; ctr < imageCount; imageCount++)
                    {
                        HelperClass.setImage(imagePreviews.get(ctr), restaurantImagesEntities.get(ctr).getImagePath());
                    }
                }
                else
                {
                    // TODO: Set a whole view to say no images available
                }
            }
        });
    }

    private void subscribeLimitedComments() {

    }
}