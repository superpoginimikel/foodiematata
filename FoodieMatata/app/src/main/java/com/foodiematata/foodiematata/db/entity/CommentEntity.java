package com.foodiematata.foodiematata.db.entity;

import com.foodiematata.foodiematata.model.Comment;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "comments",
        foreignKeys = {
                @ForeignKey(entity = RestaurantEntity.class,
                        parentColumns = "id",
                        childColumns = "restaurantId",
                        onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = "restaurantId")
        })
public class CommentEntity implements Comment {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int restaurantId;
    private String title;
    private String text;
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
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public Date getPostedAt() {
        return postedAt;
    }
    public void setPostedAt(Date postedAt) {
        this.postedAt = postedAt;
    }

    public CommentEntity() {
    }

    @Ignore
    public CommentEntity(int id, int restaurantId, String title, String text, Date postedAt) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.title = title;
        this.text = text;
        this.postedAt = postedAt;
    }
}
