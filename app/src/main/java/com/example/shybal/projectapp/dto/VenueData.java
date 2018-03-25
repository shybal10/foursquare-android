package com.example.shybal.projectapp.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by shybal on 22/5/17.
 */

public class VenueData {
    @SerializedName("response")
    @Expose
    public Response response;

    public static class Response {

        @SerializedName("groups")
        @Expose
        public List<Group> groups = null;

        public static class Group {

            @SerializedName("items")
            @Expose
            public List<Item> items = null;

            public static class Item {
                @SerializedName("venue")
                @Expose
                public Venue venue;

                public static class Venue {

                    @SerializedName("id")
                    @Expose
                    public String id;
                    @SerializedName("name")
                    @Expose
                    public String name;
                    @SerializedName("location")
                    @Expose
                    public Location location;
                    @SerializedName("categories")
                    @Expose
                    public List<Category> categories = null;
                    @SerializedName("price")
                    @Expose
                    public Price price;
                    @SerializedName("rating")
                    @Expose
                    public Float rating;
                    @SerializedName("ratingColor")
                    @Expose
                    public String ratingColor;
                    @SerializedName("photos")
                    @Expose
                    public Photos photos;

                    public static class Location {

                        @SerializedName("lat")
                        @Expose
                        public Float lat;
                        @SerializedName("lng")
                        @Expose
                        public Float lng;
                        @SerializedName("distance")
                        @Expose
                        public Integer distance;
                        @SerializedName("formattedAddress")
                        @Expose
                        public List<String> formattedAddress = null;

                    }

                    public static class Category {

                        @SerializedName("name")
                        @Expose
                        public String name;
                        @SerializedName("icon")
                        @Expose
                        public Icon icon;

                        public static class Icon {

                            @SerializedName("prefix")
                            @Expose
                            public String prefix;
                            @SerializedName("suffix")
                            @Expose
                            public String suffix;
                        }


                    }

                    public static class Photos {

                        @SerializedName("count")
                        @Expose
                        public Integer count;
                        @SerializedName("groups")
                        @Expose
                        public List<Group_> groups = null;


                        public static class Group_ {

                            @SerializedName("count")
                            @Expose
                            public Integer count;
                            @SerializedName("items")
                            @Expose
                            public List<Item___> items = null;

                            public static class Item___ {

                                @SerializedName("prefix")
                                @Expose
                                public String prefix;
                                @SerializedName("suffix")
                                @Expose
                                public String suffix;

                            }

                        }

                    }


                    public static class Price {

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


                }





            }
        }
    }
}
