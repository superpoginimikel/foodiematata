package com.foodiematata.foodiematata.db.entity;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "restaurant_images",
        foreignKeys = {
                @ForeignKey(entity = RestaurantEntity.class,
                        parentColumns = "id",
                        childColumns = "restaurantId",
                        onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = "restaurantId")
        })
public class RestaurantImagesEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int restaurantId;
    private String imagePath;
    private Date postedAt;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getRestaurantId() {
        return restaurantId;
    }
    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }
    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public Date getPostedAt() {
        return postedAt;
    }
    public void setPostedAt(Date postedAt) {
        this.postedAt = postedAt;
    }

    public RestaurantImagesEntity() {
    }

    @Ignore
    public RestaurantImagesEntity(int id, int restaurantId, String imagePath, Date postedAt) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.imagePath = imagePath;
        this.postedAt = postedAt;
    }
}
