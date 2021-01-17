package com.foodiematata.foodiematata;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

/*
* this class abstracts access to multiple data sources. It handles data operations and manages query threads.
* */
public class RestaurantRepository {
    private RestaurantDao restaurantDao;
    private LiveData<List<Restaurant>> restaurants;

    /*
    * constructor to get handle to the database and initialize member variables
    * */
    RestaurantRepository(Application application){
        RestaurantRoomDatabase db = RestaurantRoomDatabase.getDatabase(application);
        restaurantDao = db.restaurantDao();
        restaurants = restaurantDao.getAllRestaurants();
    }

    /*
    * wrapper method that returns cached words as liveData objects so they can be observed
    * */
    LiveData<List<Restaurant>> getAllRestaurants() {
        return restaurants;
    }

    public Restaurant getRestaurantById(int id) {
        return restaurantDao.getRestaurantById(id);
    }

    /*
    * wrapper for insert method which uses AsyncTask to run the query on a worker thread
    * */
    public void insert(Restaurant restaurant){
        new insertAsyncTask(restaurantDao).execute(restaurant);
    }

    public void deleteAll(){
        new deleteAllWordsAsyncTask(restaurantDao).execute();
    }

    public void deleteWord(Restaurant restaurant)  {
        new deleteWordAsyncTask(restaurantDao).execute(restaurant);
    }

    /*
    * AsyncTask inner class
    * */
    private static class insertAsyncTask extends AsyncTask<Restaurant, Void, Void>{

        private RestaurantDao mAsyncTaskDao;

        insertAsyncTask(RestaurantDao restaurantDao) {
            mAsyncTaskDao = restaurantDao;
        }

        @Override
        protected Void doInBackground(final Restaurant... restaurants) {
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

    private static class deleteWordAsyncTask extends AsyncTask<Restaurant, Void, Void> {
        private RestaurantDao mAsyncTaskDao;

        deleteWordAsyncTask(RestaurantDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Restaurant... params) {
            mAsyncTaskDao.deleteRestaurant(params[0]);
            return null;
        }
    }
}
