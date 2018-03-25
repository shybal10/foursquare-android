package com.example.shybal.projectapp.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by shybal on 22/5/17.
 */

public class Photos {
    @SerializedName("count")
    @Expose
    public Integer count;
    @SerializedName("groups")
    @Expose
    public List<Group > groups = null;

    public static class Group {

        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("count")
        @Expose
        public Integer count;
        @SerializedName("items")
        @Expose
        public List<Item> items = null;

        public static class Item {

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

            @SerializedName("user")
            @Expose
            public User user;

            public static class User {

                @SerializedName("id")
                @Expose
                public String id;
                @SerializedName("firstName")
                @Expose
                public String firstName;
                @SerializedName("lastName")
                @Expose
                public String lastName;
                @SerializedName("gender")
                @Expose
                public String gender;
                @SerializedName("photo")
                @Expose
                public Photo photo;

                public static class Photo {

                    @SerializedName("prefix")
                    @Expose
                    public String prefix;
                    @SerializedName("suffix")
                    @Expose
                    public String suffix;

                }

            }

        }





    }
}
