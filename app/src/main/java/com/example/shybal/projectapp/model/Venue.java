package com.example.shybal.projectapp.model;

import com.example.shybal.projectapp.dto.Photos;
import com.example.shybal.projectapp.dto.VenueDetails;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

/**
 * Created by shybal on 22/5/17.
 */

public class Venue {

    public static Venue venue;


    //Details got from explore
    private String venueId;
    private String name;
    private String address;
    private float latitude, longitude;
    private String iconImageUrl;
    private float distance;
    private String rating;
    private String ratingColor;
    private String contact;
    private int tier;
    private String currency;
    private String brief;
    private String categoryName;

    //Set during second retrofit call using venue Id
    private String categoryText;
    private ArrayList<Photo> photos;
    private ArrayList<Review> reviews;

    //set according to the local user
    private boolean isFavourite;

    //Getters and setters
    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public String getCategoryText() {
        return categoryText;
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public Venue() {
        categoryText = new String();
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBrief() {
        return brief;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float lattitude) {
        this.latitude = lattitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getIconImageUrl() {
        return iconImageUrl;
    }

    public void setIconImageUrl(String iconImageUrl) {
        this.iconImageUrl = iconImageUrl;
    }

    public float getDistance() {
        return distance;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRatingColor() {
        return ratingColor;
    }

    public void setRatingColor(String ratingColor) {
        this.ratingColor = ratingColor;
    }
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }


    /*
     * Set the data to the
     */
    public void setDataToVenue(VenueDetails venueDetails) {

        if(venueDetails.response.venue.contact != null) {
            venue.setContact(venueDetails.response.venue.contact.formattedPhone);
        }

        setCategoryText(venueDetails);
        setPhotos(venueDetails);
        setReviews(venueDetails);
    }

    private void setCategoryText(VenueDetails venueDetails) {

        int categorySize = venueDetails.response.venue.categories.size();
        String categoryText = "";

        for (int i = 0; i < categorySize - 1; i++) {
            categoryText = categoryText + venueDetails.response.venue.categories.get(i).name + ", ";
        }
        categoryText = categoryText + venueDetails.response.venue.categories.get(categorySize - 1).name;

        this.categoryText = categoryText;
    }

    private void setPhotos(VenueDetails venueDetails) {
        photos = new ArrayList<>();

        if(venueDetails.response.venue.photos.groups.size() != 0) {

            int numberOfPhotos = venueDetails.response.venue.photos.groups.get(0).items.size();

            for (int i = 0; i < numberOfPhotos; i++) {

                Photo photo = new Photo();

                String prefix = venueDetails.response.venue.photos.groups.get(0).items.get(i).prefix;
                String suffix = venueDetails.response.venue.photos.groups.get(0).items.get(i).suffix;
                String imageUrl = prefix + "500x500" + suffix;
                photo.setImageUrl(imageUrl);

                int createdAt = venueDetails.response.venue.photos.groups.get(0).items.get(i).createdAt;
                photo.setCreatedAtTimeStamp(createdAt);

                String formattedDate = "";

                Date netDate = (new Date(createdAt * 1000L));
                Calendar cal = Calendar.getInstance();
                cal.setTime(netDate);

                int day = cal.get(Calendar.DAY_OF_MONTH);
                int year = cal.get(Calendar.YEAR);
                String month = String.format(Locale.UK,"%tB",cal);

                formattedDate = formattedDate + month + " " + day + ", " + year;
                photo.setFormattedDate(formattedDate);
                photo.setFormattedDate(formattedDate);

                //data of user corresponding to the photo
                User user = new User();

                String firstName = "";
                String lastName = "";

                if(venueDetails.response.venue.photos.groups.get(0).items.get(i).user != null) {
                    if(venueDetails.response.venue.photos.groups.get(0).items.get(i).user.firstName != null) {
                        firstName = venueDetails.response.venue.photos.groups.get(0).items.get(i).user.firstName;
                    }
                    if(venueDetails.response.venue.photos.groups.get(0).items.get(i).user.lastName != null) {
                        lastName = venueDetails.response.venue.photos.groups.get(0).items.get(i).user.lastName;
                    }

                    String fullName = firstName + " " + lastName;
                    user.setFullName(fullName);

                    prefix = venueDetails.response.venue.photos.groups.get(0).items.get(i).user.photo.prefix;
                    suffix = venueDetails.response.venue.photos.groups.get(0).items.get(i).user.photo.suffix;
                    imageUrl = prefix + "500x500" + suffix;
                    user.setImageUrl(imageUrl);

                } else {
                    user.setImageUrl("https://igx.4sqi.net/img/user/500x500/blank_boy.png");
                    user.setFullName("Unknown");
                }

                photo.setUser(user);

                photos.add(photo);
                this.sortPhotosByDate();
            }
        }

    }

    private void setReviews(VenueDetails venueDetails) {

        reviews = new ArrayList<>();

        if (venueDetails.response.venue.tips.groups != null) {

            int size = venueDetails.response.venue.tips.groups.size();

            for (int i = 0; i < size; i++) {

                if (venueDetails.response.venue.tips.groups.get(i).name.equals("All tips")) {

                    int numberOfReviews = venueDetails.response.venue.tips.groups.get(i).items.size();

                    for (int j = 0; j < numberOfReviews; j++) {

                        Review review = new Review();

                        String reviewText = venueDetails.response.venue.tips.groups.get(i).items.get(j).text;
                        review.setReviewText(reviewText);

                        int createdAt = venueDetails.response.venue.tips.groups.get(i).items.get(j).createdAt;
                        review.setCreatedAtTimeStamp(createdAt);

                        String formattedDate = "";

                        Date netDate = (new Date(createdAt * 1000L));
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(netDate);

                        int day = cal.get(Calendar.DAY_OF_MONTH);
                        int year = cal.get(Calendar.YEAR);
                        String month = String.format(Locale.UK,"%tB",cal);

                        formattedDate = formattedDate + month + " " + day + ", " + year;
                        review.setFormattedDate(formattedDate);

                        //data of user corresponding to the photo
                        User user = new User();

                        String firstName = "";
                        String lastName = "";

                        if(venueDetails.response.venue.tips.groups.get(i).items.get(j).user != null) {
                            if(venueDetails.response.venue.tips.groups.get(i).items.get(j).user.firstName != null) {
                                firstName = venueDetails.response.venue.tips.groups.get(i).items.get(j).user.firstName;
                            }
                            if(venueDetails.response.venue.tips.groups.get(i).items.get(j).user.lastName != null) {
                                lastName = venueDetails.response.venue.tips.groups.get(i).items.get(j).user.lastName;
                            }
                            String fullName = firstName + " " + lastName;
                            user.setFullName(fullName);

                            String prefix = venueDetails.response.venue.tips.groups.get(i).items.get(j).user.photo.prefix;
                            String suffix = venueDetails.response.venue.tips.groups.get(i).items.get(j).user.photo.suffix;
                            String imageUrl = prefix + "500x500" + suffix;
                            user.setImageUrl(imageUrl);
                        } else {
                            user.setImageUrl("https://igx.4sqi.net/img/user/500x500/blank_boy.png");
                            user.setFullName("Unknown");
                        }

                        review.setUser(user);
                        reviews.add(review);
                        this.sortReviewsByDate();
                    }
                }
            }
        }
    }

    public void sortReviewsByDate() {
        Collections.sort(reviews, new Comparator<Review>() {
            @Override
            public int compare(Review review, Review t1) {
                int returnVal = 0;
                if(review.getCreatedAtTimeStamp() > t1.getCreatedAtTimeStamp()) {
                    returnVal = -1;
                } else if(review.getCreatedAtTimeStamp() < t1.getCreatedAtTimeStamp()) {
                    returnVal = 1;
                } else {
                    returnVal = 0;
                }
                return returnVal;
            }
        });
    }

    public void sortPhotosByDate() {
        Collections.sort(photos, new Comparator<Photo>() {
            @Override
            public int compare(Photo photo, Photo t1) {
                int returnVal = 0;
                if(photo.getCreatedAtTimeStamp() > t1.getCreatedAtTimeStamp()) {
                    returnVal = -1;
                } else if(photo.getCreatedAtTimeStamp() < t1.getCreatedAtTimeStamp()) {
                    returnVal = 1;
                } else {
                    returnVal = 0;
                }
                return returnVal;
            }
        });

    }
}

