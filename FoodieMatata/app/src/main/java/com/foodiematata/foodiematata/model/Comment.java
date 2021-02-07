package com.foodiematata.foodiematata.model;

import java.util.Date;

public interface Comment {
    int getId();
    int getRestaurantId();
    String getTitle();
    String getText();
    Date getPostedAt();
}
