package com.foodiematata.foodiematata.viewmodel;

import android.app.Application;

import com.foodiematata.foodiematata.RestaurantRepository;
import com.foodiematata.foodiematata.db.entity.RestaurantEntity;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/*
* ViewModel provides data to the UI and survives configuration changes
* Note - never pass context into ViewModel instances; Never store Activity/Fragment/View instances or context in ViewModel
* */
public class RestaurantViewModel extends AndroidViewModel {

    private RestaurantRepository mRepository;
    private LiveData<List<RestaurantEntity>> restaurants;

    /*
    * constructor; gets reference to WordRepository and gets list of all words from the repository
    * */
    public RestaurantViewModel(@NonNull Application application) {
        super(application);
        mRepository = new RestaurantRepository(application);
        restaurants = mRepository.getAllRestaurants();
    }

    /*
    * "getter" methods to get all the words from the repository and insert words into the database;
    *This hides implementation from the UI
    * */
    public LiveData<List<RestaurantEntity>> getAllRestaurants() {
        return restaurants;
    }

    public void insert(RestaurantEntity restaurant) {
        mRepository.insert(restaurant);
    }

    public void deleteAll(){
        mRepository.deleteAll();
    }
}
