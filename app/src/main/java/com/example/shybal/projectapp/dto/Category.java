package com.example.shybal.projectapp.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by shybal on 22/5/17.
 */

public class Category {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("pluralName")
    @Expose
    public String pluralName;
    @SerializedName("shortName")
    @Expose
    public String shortName;
    @SerializedName("icon")
    @Expose
    public Icon icon;
    @SerializedName("primary")
    @Expose
    public Boolean primary;

    public static class Icon {

        @SerializedName("prefix")
        @Expose
        public String prefix;
        @SerializedName("suffix")
        @Expose
        public String suffix;
    }
}
