package com.foodiematata.foodiematata.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.foodiematata.foodiematata.R;
import com.foodiematata.foodiematata.db.entity.RestaurantEntity;
import com.foodiematata.foodiematata.viewmodel.RestaurantViewModel;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RestaurantViewModel mRestaurantViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final RestaurantMainListAdapter adapter = new RestaurantMainListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRestaurantViewModel = ViewModelProviders.of(this).get(RestaurantViewModel.class);
        mRestaurantViewModel.getAllRestaurants().observe(this, new Observer<List<RestaurantEntity>>() {
            @Override
            public void onChanged(List<RestaurantEntity> restaurants) {
                adapter.setRestaurant(restaurants);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.clear_data)
        {
            Toast.makeText(this, "Clearing the data...", Toast.LENGTH_SHORT).show();
            mRestaurantViewModel.deleteAll();
            return true;
        }
        else if (id == R.id.new_restaurant)
        {
            Intent intent = new Intent(MainActivity.this, NewRestaurantActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String name = data.getStringExtra(NewRestaurantActivity.EXTRA_REPLY_NAME);
            String category = data.getStringExtra(NewRestaurantActivity.EXTRA_REPLY_CATEGORY);
            String location = data.getStringExtra(NewRestaurantActivity.EXTRA_REPLY_LOCATION);
            String phone = data.getStringExtra(NewRestaurantActivity.EXTRA_REPLY_PHONE);
            String price = data.getStringExtra(NewRestaurantActivity.EXTRA_REPLY_PRICE);

            String imageFilePath = data.getStringExtra(NewRestaurantActivity.EXTRA_REPLY_IMAGE);
            RestaurantEntity restaurant = new RestaurantEntity(name, location, phone, price, category, null, imageFilePath);
            mRestaurantViewModel.insert(restaurant);
        } else {
            Toast.makeText(getApplicationContext(), R.string.empty_not_saved, Toast.LENGTH_LONG).show();
        }
    }
}
