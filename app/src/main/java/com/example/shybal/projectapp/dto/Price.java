package com.example.shybal.projectapp.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by shybal on 22/5/17.
 */

public class Price {
    @SerializedName("tier")
    @Expose
    public Integer tier;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("currency")
    @Expose
    public String currency;
}
