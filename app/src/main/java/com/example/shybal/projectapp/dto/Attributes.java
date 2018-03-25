package com.example.shybal.projectapp.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by shybal on 22/5/17.
 */

public class Attributes {
    @SerializedName("groups")
    @Expose
    public List<Group> groups = null;

    public static class Group {
        @SerializedName("items")
        @Expose
        public List<Item> items = null;

        public static class Item {

            @SerializedName("displayName")
            @Expose
            public String displayName;
            @SerializedName("displayValue")
            @Expose
            public String displayValue;
            @SerializedName("priceTier")
            @Expose
            public Integer priceTier;

        }
    }
}
