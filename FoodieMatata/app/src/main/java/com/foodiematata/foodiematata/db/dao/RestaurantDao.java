package com.foodiematata.foodiematata.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.foodiematata.foodiematata.db.entity.Restaurant;

import java.util.List;

/*
 * DAO (Data Access Object) class; Here, SQL queries are declared and method calls are associated with it.
 * All queries to be executed on a worker thread.
 * DAO must be an abstract class or an interface
 * */

/*
* @Dao annotation used to tell Room that this is DAO class
* */
@Dao
public interface RestaurantDao {

    /*
    * method to insert one word
    * */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Restaurant restaurant);

    /*
    * method to delete all items from the table; since there is no generic method, a query has to be made
    * */
    @Query("DELETE FROM restaurant_table")
    void deleteAll();

    /*
    * get all the words from the table in List object
    * LiveData is used for data observation and making app responsive to data changes
    * */
    @Query("SELECT * FROM restaurant_table ORDER BY name ASC")
    LiveData<List<Restaurant>> getAllRestaurants();

    @Query("SELECT * FROM restaurant_table where id == :id  ORDER BY name ASC")
    Restaurant getRestaurantById(int id);

    /*
    * get single word
    * */
    @Query("SELECT * FROM restaurant_table LIMIT 1")
    Restaurant[] getAnyRestaurant();

    // delete single word
    @Delete
    void deleteRestaurant(Restaurant restaurant);
}
