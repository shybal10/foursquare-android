package com.example.shybal.projectapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.shybal.projectapp.Adapters.FavouritesRecyclerViewAdapter;
import com.example.shybal.projectapp.database.LoginDataBaseAdapter;
import com.example.shybal.projectapp.model.DataHolder;
import com.example.shybal.projectapp.model.Venue;
import com.google.gson.Gson;

public class FavouritesActivity extends AppCompatActivity implements FavouritesRecyclerViewAdapter.ListItemClickListener {

    private FavouritesRecyclerViewAdapter mAdapter;
    private RecyclerView list;
    private static final int NUM_LIST_ITEMS = 100;
    LoginDataBaseAdapter loginDataBaseAdapter;
    SearchView searchView;
    String text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        searchView = (SearchView) findViewById(R.id.btn_searchVenue);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                text = newText;
               mAdapter.getFilter().filter(newText);
                return true;
            }
        });


        list = (RecyclerView) findViewById(R.id.restaurant_details);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        list.setHasFixedSize(true);
        mAdapter = new FavouritesRecyclerViewAdapter(NUM_LIST_ITEMS, this, FavouritesActivity.this);
        list.setAdapter(mAdapter);
        mAdapter.setData(DataHolder.favVenueList.getVenues());
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Log.d("click", "done");
        Venue.venue = DataHolder.favouritesFiltered.venues.get(clickedItemIndex);
        Intent datailsIntent = new Intent(FavouritesActivity.this, HotelDetailActivity.class);
        startActivity(datailsIntent);
    }

    @Override
    protected void onResume() {
        mAdapter.getFilter().filter(text);
        super.onResume();
    }

    @Override
    protected void onPause() {
        //TODO changed DataHolder.userName
        if(DataHolder.isLoggedIn) {
            DataHolder.saveFavouritesToDatabase(FavouritesActivity.this);
        }
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void returnToParent(View view){
        onBackPressed();
    }

}
