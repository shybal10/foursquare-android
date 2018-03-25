package com.example.shybal.projectapp.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.example.shybal.projectapp.model.DataHolder.userName;

/**
 * Created by shybal on 20/5/17.
 */

public class SaveSharedPreference {

    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "Product_Favorite";
    static final String PREF_USER_NAME= "username";
    public static String PHOTO_URL = "photo_url";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static void clearUserName(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }

    public static String getPhotoUrl(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PHOTO_URL,"");
    }

    public static void setPhotoUrl(Context ctx, String photoUrl)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PHOTO_URL, photoUrl);
        editor.commit();
    }

}
