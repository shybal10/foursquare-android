package com.example.shybal.projectapp.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by shybal on 22/5/17.
 */

public class Location {
    @SerializedName("lat")
    @Expose
    public Float lat;
    @SerializedName("lng")
    @Expose
    public Float lng;
    @SerializedName("formattedAddress")
    @Expose
    public List<String> formattedAddress = null;
}
