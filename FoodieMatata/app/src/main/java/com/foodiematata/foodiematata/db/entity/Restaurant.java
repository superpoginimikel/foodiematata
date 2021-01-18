package com.foodiematata.foodiematata.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*
* Entity class for Room database
* Room annotations used so that the entity can be used in the database
* */

/*
* @Entity --> represents an entity in a table; this annotation shows that this class in an entity
* table name is specified if it is different from class name
* */
@Entity(tableName = "restaurant_table")
public class Restaurant {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "phone")
    private String phone;

    // Update this to enum (wanna use preset price range)
    @ColumnInfo(name = "price")
    private String price;

    // Update this to enum
    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "ratingsId")
    private String ratingsId;

    @ColumnInfo(name = "imagePath")
    private String imagePath;

    /*
    * public constructor
    * */
    public Restaurant(@NonNull String name, @NonNull String location,
                      @NonNull String phone, @NonNull String price, String category, String ratingsId,
                      String imagePath) {
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.price = price;
        this.category = category;
        this.ratingsId = ratingsId;
        this.imagePath = imagePath;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /*
    * getter for member variable
    * */
    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getLocation() {
        return this.location;
    }
    public String getPhone() {
        return this.phone;
    }
    public String getPrice() {
        return this.price;
    }
    public String getCategory() {
        return this.category;
    }
    public String getRatingsId() {
        return this.ratingsId;
    }
    public String getImagePath() { return this.imagePath; }
}
