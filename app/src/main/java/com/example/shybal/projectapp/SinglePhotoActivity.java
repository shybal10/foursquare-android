package com.example.shybal.projectapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shybal.projectapp.model.Venue;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class SinglePhotoActivity extends AppCompatActivity {

   private ImageView tappedImageView;
    private TextView hotelNameTextView;
    private CircleImageView userImageView;
    private TextView userNameTextView;
    private TextView dateTextView;

    private Venue currentVenue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_photo);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        tappedImageView = (ImageView) findViewById(R.id.photo_view);
        hotelNameTextView = (TextView) findViewById(R.id.hotel_name);
        userImageView = (CircleImageView) findViewById(R.id.user_image_view);
        userNameTextView = (TextView) findViewById(R.id.user_name_view);
        dateTextView = (TextView) findViewById(R.id.date_text_view);
        currentVenue = Venue.venue;


        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);

        hotelNameTextView.setText(currentVenue.getName());
        userNameTextView.setText(currentVenue.getPhotos().get(position).getUser().getFullName());
        dateTextView.setText("Added " + currentVenue.getPhotos().get(position).getFormattedDate());


        Picasso.with(SinglePhotoActivity.this)
                .load(currentVenue.getPhotos().get(position).getImageUrl())
                .into(tappedImageView);

        Picasso.with(SinglePhotoActivity.this)
                .load(currentVenue.getPhotos().get(position).getUser().getImageUrl())
                .into(userImageView);
    }

    @Override
    public void onBackPressed() {
        SinglePhotoActivity.this.finish();
        super.onBackPressed();
    }

    public void returnToParent(View view){
        onBackPressed();
    }
}
