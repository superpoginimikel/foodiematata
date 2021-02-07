package com.foodiematata.foodiematata.viewmodel;

import android.app.Application;

import com.foodiematata.foodiematata.RestaurantRepository;
import com.foodiematata.foodiematata.db.entity.CommentEntity;
import com.foodiematata.foodiematata.db.entity.RestaurantEntity;
import com.foodiematata.foodiematata.db.entity.RestaurantImagesEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class RestaurantInfoViewModel extends AndroidViewModel {

    private RestaurantRepository mRepository;

    public RestaurantInfoViewModel(@NonNull Application application) {
        super(application);
        mRepository = new RestaurantRepository(application);
    }

    public LiveData<RestaurantEntity> getRestaurantById(int id)  {
        return mRepository.getRestaurantById(id);
    }

    public LiveData<List<RestaurantImagesEntity>> getLimitedImages(int id)  {
        return mRepository.getLimitedImages(id);
    }

    public LiveData<List<CommentEntity>> getLimitedComments(int id)  {
        return mRepository.getLimitedComments(id);
    }
}
