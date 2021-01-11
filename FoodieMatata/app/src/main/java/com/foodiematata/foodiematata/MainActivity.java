package com.foodiematata.foodiematata;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    // all activity interactions with viewModel only
    private RestaurantViewModel mRestaurantViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, com.foodiematata.foodiematata.NewWordActivity.class);
//                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
//            }
//        });

        // RecyclerView setup
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final RestaurantListAdapter adapter = new RestaurantListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // ViewModel and liveData code
        // ViewModelProviders are used to associate viewModel with UI controller
        mRestaurantViewModel = ViewModelProviders.of(this).get(RestaurantViewModel.class);
        mRestaurantViewModel.getAllWords().observe(this, new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(List<Restaurant> restaurants) {
                // update cached copy of words
                adapter.setWords(restaurants);
            }
        });

        // swiping delete functionality implemented here
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Restaurant restaurant = adapter.getWordAtPosition(position);
                Toast.makeText(MainActivity.this, "Deleting...", Toast.LENGTH_LONG).show();
                mRestaurantViewModel.deleteWord(restaurant);
            }
        });
        helper.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.clear_data) {
            Toast.makeText(this, "Clearing the data...", Toast.LENGTH_SHORT).show();
            mRestaurantViewModel.deleteAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String name = data.getStringExtra(NewRestaurantActivity.EXTRA_REPLY_NAME);
            String description = data.getStringExtra(NewRestaurantActivity.EXTRA_REPLY_DESCRIPTION);
            String location = data.getStringExtra(NewRestaurantActivity.EXTRA_REPLY_LOCATION);
            String phone = data.getStringExtra(NewRestaurantActivity.EXTRA_REPLY_PHONE);
            Restaurant restaurant = new Restaurant(name, description, location, phone, null, null);
            mRestaurantViewModel.insert(restaurant);
        } else {
            Toast.makeText(getApplicationContext(), R.string.empty_not_saved, Toast.LENGTH_LONG).show();
        }
    }
}
