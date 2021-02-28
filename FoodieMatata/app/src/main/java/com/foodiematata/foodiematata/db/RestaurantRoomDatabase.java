package com.foodiematata.foodiematata.db;

import android.content.Context;

import com.foodiematata.foodiematata.db.converter.DateConverter;
import com.foodiematata.foodiematata.db.dao.CommentDao;
import com.foodiematata.foodiematata.db.dao.RestaurantDao;
import com.foodiematata.foodiematata.db.dao.RestaurantImageDao;
import com.foodiematata.foodiematata.db.entity.RestaurantEntity;
import com.foodiematata.foodiematata.db.entity.CommentEntity;
import com.foodiematata.foodiematata.db.entity.RestaurantImagesEntity;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/*
 * Room database child class; this class is a layer on SQLiteDatabase;
 * It uses DAO for queries, doesn't allow running on main thread, and provides compile time checks for SQL
 * statements.
 * Must be abstract and only one required for the whole app
 * */

/*
 * @Database annotation tells that this is a room database; it consists of only one entity(word.class);
 * version number is 1; export schema keeps history of schema versions (disabled because database migration not needed)
 * */
@Database(entities = {RestaurantEntity.class, CommentEntity.class, RestaurantImagesEntity.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class RestaurantRoomDatabase extends RoomDatabase {

    public abstract RestaurantDao restaurantDao();
    public abstract RestaurantImageDao restaurantImagesDao();
    public abstract CommentDao commentDao();

    /*
     * Making the database singleton so that there is only one instance of Room database.
     * */
    private static RestaurantRoomDatabase INSTANCE;

    public static RestaurantRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RestaurantRoomDatabase.class) {
                if (INSTANCE == null) {
                    
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RestaurantRoomDatabase.class, "restaurant_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

