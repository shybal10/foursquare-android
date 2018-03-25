package com.example.shybal.projectapp.model;

import com.example.shybal.projectapp.dto.VenueData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import static android.R.attr.id;

/**
 * Created by shybal on 25/5/17.
 */

public class VenueList {


    public ArrayList<Venue> venues;

    public ArrayList<Venue> getVenues() {
        return venues;
    }

    public VenueList() {
        venues = new ArrayList<>();
    }

    public void setDataToVenueList (VenueData venueData){

        venues = new ArrayList<>();
        for (int i = 0; i < venueData.response.groups.get(0).items.size(); i++) {

            VenueData.Response.Group.Item.Venue venue = venueData.response.groups.get(0).items.get(i).venue;
            Venue newVenue = new Venue();

            newVenue.setVenueId(venue.id);

            newVenue.setName(venue.name);

            if(venue.location.formattedAddress != null) {
                String formattedAddress = "";
                int size = venue.location.formattedAddress.size();
                for(int j = 0; j < size - 1; j++) {
                    formattedAddress = formattedAddress + venue.location.formattedAddress.get(j) + ", ";
                }
                formattedAddress = formattedAddress + venue.location.formattedAddress.get(size - 1) + ". ";
                newVenue.setAddress(formattedAddress);
            } else {
                newVenue.setAddress("NA");
            }


            if(venue.rating != null) {
                newVenue.setRating(Float.toString(venue.rating));
                newVenue.setRatingColor("#" + venue.ratingColor);
            } else {
                newVenue.setRating("NA");
                newVenue.setRatingColor("#FFFF00");
            }

            if(venue.photos.count == 0) {
                newVenue.setIconImageUrl(venue.categories.get(0).icon.prefix + "bg_88" + venue.categories.get(0).icon.suffix);
            } else {
                newVenue.setIconImageUrl(venue.photos.groups.get(0).items.get(0).prefix + "500x500" + venue.photos.groups.get(0).items.get(0).suffix);
            }
            newVenue.setLatitude(venue.location.lat);
            newVenue.setLongitude(venue.location.lng);
            newVenue.setCategoryName(venue.categories.get(0).name);


            if(venue.price != null) {
                newVenue.setCurrency(venue.price.currency);
                newVenue.setTier(venue.price.tier);
            } else {
                newVenue.setCurrency("");
                newVenue.setTier(0);
            }

            if(venue.location.distance != null) {
                newVenue.setDistance((venue.location.distance.floatValue() / 1000));
            } else {
                newVenue.setDistance(0);
            }

            String str ="";
            for (int k = 0; k < newVenue.getTier(); k++) {
                str = str + newVenue.getCurrency();
            }

            if(str.equals("")){
                String brief = newVenue.getCategoryName();
                newVenue.setBrief(brief);

            } else  {
                String brief = newVenue.getCategoryName();
                brief = brief + " . " + str;
                newVenue.setBrief(brief);
            }



            //Always the last statement
            this.venues.add(newVenue);
        }

    }

    public void sortByDistance() {
        Collections.sort(venues, new Comparator<Venue>() {
            @Override
            public int compare(Venue venue, Venue t1) {
                int returnVal = 0;
                if(venue.getDistance() > t1.getDistance()) {
                    returnVal = 1;
                } else if(venue.getDistance() < t1.getDistance()) {
                    returnVal = -1;
                } else {
                    returnVal = 0;
                }
                return returnVal;
            }
        });
    }
}
