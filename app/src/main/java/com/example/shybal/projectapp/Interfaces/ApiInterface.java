package com.example.shybal.projectapp.Interfaces;

import com.example.shybal.projectapp.dto.VenueData;
import com.example.shybal.projectapp.dto.VenueDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by shybal on 22/5/17.
 */

public interface ApiInterface {
    @GET("venues/explore")
    Call<VenueData> fetchVenues(@Query("ll")String latitudes, @Query("oauth_token")String oauth, @Query("v")String version, @Query("venuePhotos") String venuePhotos, @Query("sortByDistance") String sortByDistance);


    @GET("venues/explore")
    Call<VenueData> fetchVenuesBySection(@Query("ll")String latitudes, @Query("oauth_token")String oauth, @Query("v")String version, @Query("venuePhotos") String venuePhotos, @Query("section") String section);


    @GET("venues/explore")
    Call<VenueData> fetchVenuesByCheckins(@Query("ll")String latitudes, @Query("oauth_token")String oauth, @Query("v")String version, @Query("venuePhotos") String venuePhotos, @Query("section") String checkins);

    @GET("venues/{VENUE_ID}")
    Call<VenueDetails> fetchDetailsResponse(@Path("VENUE_ID") String venueId, @Query("oauth_token") String authToken, @Query("v") String apiVersion);
}
