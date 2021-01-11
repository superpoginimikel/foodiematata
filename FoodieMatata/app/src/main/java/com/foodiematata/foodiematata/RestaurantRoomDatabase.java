package com.foodiematata.foodiematata;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

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
@Database(entities = {Restaurant.class}, version = 1, exportSchema = false)
public abstract class RestaurantRoomDatabase extends RoomDatabase {

    /*
     * all the DAOs that work with the database come here;
     * Here, wordDao() works like a 'getter' for the DAO
     * */
    public abstract RestaurantDao restaurantDao();

    /*
     * Making the database singleton so that there is only one instance of Room database.
     * */
    private static RestaurantRoomDatabase INSTANCE;

    public static RestaurantRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RestaurantRoomDatabase.class) {
                if (INSTANCE == null) {
                    // database creation goes here

                    /*
                     * use databaseBuilder() to build Room database named "word_database".
                     * It uses destructive database migration (if database schema is changed, destroy and recreate database)
                     * Destructive migration is not preferred in real world apps
                     * */
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RestaurantRoomDatabase.class, "restaurant_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // run asyncTask whenever app is opened
    private static Callback sRoomDatabaseCallback = new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    /*
     * asyncTask to delete all existing entries and repopulating the database with the contents of string array
     * */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final RestaurantDao mDao;
        // Change this to restaurant hardcode values
//        String[] words = {"dolphin", "crocodile", "cobra"};

        PopulateDbAsync(RestaurantRoomDatabase db) {
            mDao = db.restaurantDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
//            if (mDao.getAnyWord().length < 1) {
//                for (int i = 0; i <= words.length - 1; i++) {
//                    Restaurant restaurant = new Restaurant(words[i]);
//                    mDao.insert(word);
//                }
//            }
            return null;
        }
    }
}

