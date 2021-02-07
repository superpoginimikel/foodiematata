package com.foodiematata.foodiematata.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.foodiematata.foodiematata.db.entity.RestaurantEntity;

import java.util.List;

@Dao
public interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(RestaurantEntity restaurant);

    @Query("DELETE FROM restaurant_table")
    void deleteAll();

    @Query("SELECT * FROM restaurant_table ORDER BY name ASC")
    LiveData<List<RestaurantEntity>> getAllRestaurants();

    @Query("SELECT * FROM restaurant_table where id == :id  ORDER BY name ASC")
    LiveData<RestaurantEntity> getRestaurantById(int id);
}
