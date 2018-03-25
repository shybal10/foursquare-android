package com.example.shybal.projectapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.shybal.projectapp.Adapters.GridAdapter;
import com.example.shybal.projectapp.model.Venue;

public class MultiPhotosActivity extends AppCompatActivity {




    private static final String TAG = MultiPhotosActivity.class.getSimpleName();


    private GridAdapter gridAdapter;
    private GridView gridview;
    private TextView hotelNamTextView;

    private Venue venue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_photos);

        //change when passed through intent
        venue = Venue.venue;


        gridAdapter = new GridAdapter(MultiPhotosActivity.this, venue);
        hotelNamTextView = (TextView) findViewById(R.id.hotel_name);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        hotelNamTextView.setText(venue.getName());
        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(gridAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent photoIntent = new Intent(MultiPhotosActivity.this, SinglePhotoActivity.class);
                photoIntent.putExtra("position", position);
                startActivity(photoIntent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        MultiPhotosActivity.this.finish();
        super.onBackPressed();

    }

    public void returnToParent(View view){
        onBackPressed();
        //Intent hotelDetailIntent = new Intent(MultiPhotosActivity.this,HotelDetailActivity.class);
        //MultiPhotosActivity.this.finish();
        //startActivity(hotelDetailIntent);
    }
}
