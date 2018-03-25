package com.example.shybal.projectapp.model;

import android.content.Context;

import com.example.shybal.projectapp.database.LoginDataBaseAdapter;
import com.google.gson.Gson;

/**
 * Created by shybal on 26/5/17.
 */

public class DataHolder {

    private static LoginDataBaseAdapter loginDataBaseAdapter;
    public static VenueList favVenueList = new VenueList();
    public static VenueList favouritesFiltered = new VenueList();
    public static String userName = "";
    public static boolean isLoggedIn = true;
    public static Venue venue;

    public static void getFavouritesJsonString(Context context) {
        loginDataBaseAdapter = new LoginDataBaseAdapter(context);
        loginDataBaseAdapter.open();
        Gson gson = new Gson();
        String response = loginDataBaseAdapter.getVenueData(userName);
        VenueList venues = gson.fromJson(response, VenueList.class);
        favVenueList = venues;
        loginDataBaseAdapter.close();
    }

    public static void saveFavouritesToDatabase(Context context) {
        loginDataBaseAdapter = new LoginDataBaseAdapter(context);
        loginDataBaseAdapter.open();
        Gson gson = new Gson();
        String gsonFavourite = gson.toJson(favVenueList);
        loginDataBaseAdapter.updateEntry(userName, gsonFavourite);
        loginDataBaseAdapter.close();
    }
}
