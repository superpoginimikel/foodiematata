package com.foodiematata.foodiematata;


import android.app.Application;
import android.os.AsyncTask;

import com.foodiematata.foodiematata.db.RestaurantRoomDatabase;
import com.foodiematata.foodiematata.db.dao.CommentDao;
import com.foodiematata.foodiematata.db.dao.RestaurantDao;
import com.foodiematata.foodiematata.db.dao.RestaurantImageDao;
import com.foodiematata.foodiematata.db.entity.CommentEntity;
import com.foodiematata.foodiematata.db.entity.RestaurantEntity;
import com.foodiematata.foodiematata.db.entity.RestaurantImagesEntity;

import androidx.lifecycle.LiveData;

import java.util.List;

/*
* this class abstracts access to multiple data sources. It handles data operations and manages query threads.
* */
public class RestaurantRepository {
    private RestaurantDao restaurantDao;
    private RestaurantImageDao restaurantImageDao;
    private CommentDao commentDao;
    private LiveData<List<RestaurantEntity>> restaurants;
    /*
    * constructor to get handle to the database and initialize member variables
    * */
    public RestaurantRepository(Application application){
        RestaurantRoomDatabase db = RestaurantRoomDatabase.getDatabase(application);
        restaurantDao = db.restaurantDao();
        restaurants = restaurantDao.getAllRestaurants();
    }

    /*
    * wrapper method that returns cached words as liveData objects so they can be observed
    * */
    public LiveData<List<RestaurantEntity>> getAllRestaurants() {
        return restaurants;
    }

    public LiveData<RestaurantEntity> getRestaurantById(int id) {
        return restaurantDao.getRestaurantById(id);
    }

    public LiveData<List<RestaurantImagesEntity>> getLimitedImages(int id) {
        return restaurantImageDao.getLimitedImages(id);
    }

    public LiveData<List<CommentEntity>> getLimitedComments(int id) {
        return commentDao.getAllComments(id);
    }

    /*
    * wrapper for insert method which uses AsyncTask to run the query on a worker thread
    * */
    public void insert(RestaurantEntity restaurant){
        new insertAsyncTask(restaurantDao).execute(restaurant);
    }

    public void deleteAll(){
        new deleteAllWordsAsyncTask(restaurantDao).execute();
    }

    /*
    * AsyncTask inner class
    * */
    private static class insertAsyncTask extends AsyncTask<RestaurantEntity, Void, Void>{

        private RestaurantDao mAsyncTaskDao;

        insertAsyncTask(RestaurantDao restaurantDao) {
            mAsyncTaskDao = restaurantDao;
        }

        @Override
        protected Void doInBackground(final RestaurantEntity... restaurants) {
            mAsyncTaskDao.insert(restaurants[0]);
            return null;
        }
    }

    private static class deleteAllWordsAsyncTask extends AsyncTask<Void, Void, Void> {
        private RestaurantDao mAsyncTaskDao;

        deleteAllWordsAsyncTask(RestaurantDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
}
