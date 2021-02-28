package com.foodiematata.foodiematata.db.dao;

import com.foodiematata.foodiematata.db.entity.RestaurantImagesEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface RestaurantImageDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(RestaurantImagesEntity restaurantImagesEntity);

    @Query("SELECT * FROM restaurant_images where id == :id  ORDER BY postedAt DESC LIMIT 3")
    LiveData<List<RestaurantImagesEntity>> getLimitedImages(int id);

    @Query("SELECT * FROM restaurant_images where id == :id  ORDER BY postedAt DESC")
    LiveData<List<RestaurantImagesEntity>> getAllImages(int id);
}
