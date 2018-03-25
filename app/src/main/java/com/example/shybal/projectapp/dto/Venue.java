package com.example.shybal.projectapp.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by shybal on 22/5/17.
 */

public class Venue {
    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("contact")
    @Expose
    public Contact contact;


    @SerializedName("location")
    @Expose
    public Location location1;

    @SerializedName("canonicalUrl")
    @Expose
    public String canonicalUrl;

    @SerializedName("categories")
    @Expose
    public List<Category> categories = null;

    @SerializedName("price")
    @Expose
    public Price price;

    @SerializedName("dislike")
    @Expose
    public Boolean dislike;

    @SerializedName("ok")
    @Expose
    public Boolean ok;

    @SerializedName("rating")
    @Expose
    public Float rating;

    @SerializedName("ratingColor")
    @Expose
    public String ratingColor;

    @SerializedName("ratingSignals")
    @Expose
    public Integer ratingSignals;

    @SerializedName("photos")
    @Expose
    public Photos photos;

    @SerializedName("tips")
    @Expose
    public Tips tips;

    @SerializedName("attributes")
    @Expose
    public Attributes attributes;

    @SerializedName("bestPhoto")
    @Expose
    public BestPhoto bestPhoto;
}
