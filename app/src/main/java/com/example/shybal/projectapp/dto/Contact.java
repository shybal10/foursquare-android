package com.example.shybal.projectapp.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by shybal on 22/5/17.
 */

public class Contact {
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("formattedPhone")
    @Expose
    public String formattedPhone;
}
