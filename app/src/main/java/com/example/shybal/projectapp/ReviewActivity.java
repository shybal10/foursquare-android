package com.example.shybal.projectapp;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.shybal.projectapp.Adapters.ReviewRecycleViewAdapter;
import com.example.shybal.projectapp.model.Venue;

public class ReviewActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        ReviewRecycleViewAdapter.ReviewListItemClickListener  {


    private static final int NUM_LIST_ITEMS = 100;
    private ReviewRecycleViewAdapter mAdapter;
    private RecyclerView list;
    private Venue venue;

    private TextView hotelNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        hotelNameTextView = (TextView) findViewById(R.id.hotel_name);



        //change when passed through intent
        venue = Venue.venue;
        hotelNameTextView.setText(venue.getName());

        list = (RecyclerView) findViewById(R.id.review_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        list.setHasFixedSize(true);
        mAdapter = new ReviewRecycleViewAdapter(NUM_LIST_ITEMS, this, ReviewActivity.this);
        list.setAdapter(mAdapter);
        mAdapter.setData(venue);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

    }

    @Override
    public void onBackPressed() {
        ReviewActivity.this.finish();
        super.onBackPressed();
    }

    public void returnToParent(View view){
        onBackPressed();
        //Intent hotelDetailIntent = new Intent(ReviewActivity.this,HotelDetailActivity.class);
        //ReviewActivity.this.finish();
        //startActivity(hotelDetailIntent);
    }
}
