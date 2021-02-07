package com.foodiematata.foodiematata.db.dao;

import com.foodiematata.foodiematata.db.entity.CommentEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

public interface CommentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CommentEntity comment);

    @Query("SELECT * FROM comments where id == :id  ORDER BY postedAt DESC LIMIT 3")
    LiveData<List<CommentEntity>> getLimitedComments(int id);

    @Query("SELECT * FROM comments where id == :id  ORDER BY postedAt DESC")
    LiveData<List<CommentEntity>> getAllComments(int id);
}
