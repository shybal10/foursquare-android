package com.example.shybal.projectapp.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by shybal on 22/5/17.
 */

public class BestPhoto {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("createdAt")
    @Expose
    public Integer createdAt;

    @SerializedName("prefix")
    @Expose
    public String prefix;
    @SerializedName("suffix")
    @Expose
    public String suffix;
    @SerializedName("width")
    @Expose
    public Integer width;
    @SerializedName("height")
    @Expose
    public Integer height;
    @SerializedName("visibility")
    @Expose
    public String visibility;
}
