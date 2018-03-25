package com.example.shybal.projectapp.model;

/**
 * Created by shybal on 23/5/17.
 */

public class Review {

    private String reviewText;
    private int createdAtTimeStamp;
    private String formattedDate;
    private User user;

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public int getCreatedAtTimeStamp() {
        return createdAtTimeStamp;
    }

    public void setCreatedAtTimeStamp(int createdAtTimeStamp) {
        this.createdAtTimeStamp = createdAtTimeStamp;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
