package com.example.shybal.projectapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shybal.projectapp.Interfaces.ApiInterface;
import com.example.shybal.projectapp.Maps.HotelMapFragment;
import com.example.shybal.projectapp.database.LoginDataBaseAdapter;
import com.example.shybal.projectapp.dto.VenueDetails;
import com.example.shybal.projectapp.model.DataHolder;
import com.example.shybal.projectapp.model.FourSquareApiAuth;
import com.example.shybal.projectapp.model.Venue;
import com.google.gson.Gson;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HotelDetailActivity extends AppCompatActivity {

    private static final String TAG =HotelDetailActivity.class.getSimpleName() ;
    private Venue clickedVenue;
    private VenueDetails clickedVenueDetails;

    private ImageView iconImageView;
    private TextView nameTextView;
    private TextView categoryTextView;
    private TextView addressTextView;
    private TextView numberTextView;
    private TextView distanceTextView;
    private SimpleRatingBar ratingBar;
    private ImageButton photosButton;
    private ImageButton reviewsButton;
    private ImageButton favButton;
    public static ArrayList<Venue> venues = new ArrayList<>();
    LoginDataBaseAdapter loginDataBaseAdapter ;

    private LinearLayout iconLayout;
    private ProgressBar progressBar;
    private View decorView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);

        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        decorView = getWindow().getDecorView();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        iconImageView = (ImageView) findViewById(R.id.hotel_image);
        nameTextView = (TextView) findViewById(R.id.hotel_name);
        categoryTextView = (TextView) findViewById(R.id.textview_details);
        addressTextView = (TextView) findViewById(R.id.address_text_view);
        numberTextView = (TextView) findViewById(R.id.contact_text_view);
        distanceTextView = (TextView) findViewById(R.id.distance_text_view);
        ratingBar = (SimpleRatingBar) findViewById(R.id.hotel_rating_bar);
        photosButton = (ImageButton) findViewById(R.id.photos_button);
        reviewsButton = (ImageButton) findViewById(R.id.review_button);
        favButton = (ImageButton) findViewById(R.id.fav_button);
        iconLayout = (LinearLayout) findViewById(R.id.icon_layout);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        clickedVenueDetails = new VenueDetails();
        clickedVenue = Venue.venue;

        if(!DataHolder.isLoggedIn) {
            favButton.setVisibility(View.INVISIBLE);
        }
        showProgressBar();

        favButton.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
               boolean isFavourite = clickedVenue.isFavourite();
               if (isFavourite) {
                   favButton.setBackgroundResource(R.drawable.favourite_icon_copy);
                   clickedVenue.setFavourite(false);
                   for (int i = 0; i < DataHolder.favVenueList.venues.size(); i++) {
                       if(clickedVenue.getVenueId().equals(DataHolder.favVenueList.getVenues().get(i).getVenueId())) {
                           DataHolder.favVenueList.venues.remove(i);
                           DataHolder.saveFavouritesToDatabase(HotelDetailActivity.this);
                       }
                   }

               } else {
                   clickedVenue.setFavourite(true);
                   DataHolder.favVenueList.venues.add(clickedVenue);
                   DataHolder.saveFavouritesToDatabase(HotelDetailActivity.this);
                   favButton.setBackgroundResource(R.drawable.favourite_icon_selected);
               }
           }
        });

        onClickHandler();

        //change when passed through intent

        if(clickedVenue.isFavourite()) {
            favButton.setBackgroundResource(R.drawable.favourite_icon_selected);
        }

        double venueLatitude = clickedVenue.getLatitude();
        double venueLongitude = clickedVenue.getLongitude();
        String venueName = clickedVenue.getName();

        loadDetailsOfVenue();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HotelMapFragment mf = new HotelMapFragment();
        mf.setVenueLocation(venueLatitude, venueLongitude, venueName);
        fragmentTransaction.add(R.id.map_fragment_container1, mf);
        fragmentTransaction.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void loadDetailsOfVenue() {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.foursquare.com/v2/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<VenueDetails> call = apiInterface.fetchDetailsResponse(clickedVenue.getVenueId(), FourSquareApiAuth.getAuthToken(), FourSquareApiAuth.getVersion());

        call.enqueue(new Callback<VenueDetails>() {
            @Override
            public void onResponse(Call<VenueDetails> call, Response<VenueDetails> response) {
                clickedVenueDetails = response.body();
                clickedVenue.setDataToVenue(clickedVenueDetails);
                showIconLayout();
                showDetails();
            }

            @Override
            public void onFailure(Call<VenueDetails> call, Throwable t) {

            }

        });
    }

    public void showDetails() {

        Picasso.with(HotelDetailActivity.this)
                .load(clickedVenue.getIconImageUrl()).resize(225, 225)
                .into(iconImageView);

        nameTextView.setText(clickedVenue.getName());
        categoryTextView.setText(clickedVenue.getCategoryText());
        addressTextView.setText(clickedVenue.getAddress());
        distanceTextView.setText("Drive: " + String.format("%.2f",clickedVenue.getDistance() )+ " km");
        numberTextView.setText(clickedVenue.getContact());

        if (clickedVenue.getRating() != "NA") {
            ratingBar.setRating(Float.valueOf(clickedVenue.getRating()) / 2);
        } else {
            ratingBar.setVisibility(View.INVISIBLE);
        }

    }


    private void onClickHandler() {

        photosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photosIntent = new Intent(HotelDetailActivity.this, MultiPhotosActivity.class);
                startActivity(photosIntent);

            }
        });

        reviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reviewsIntent = new Intent(HotelDetailActivity.this, ReviewActivity.class);
                startActivity(reviewsIntent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void returnToParent(View view){
        onBackPressed();
    }

    public void showRatingPage(View view){
        String rating =  clickedVenue.getRating();
        String color = clickedVenue.getRatingColor();

        RatingActivity cdd=new RatingActivity(this,rating,color);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(cdd.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF202020")));
        cdd.getWindow().setBackgroundDrawableResource(R.drawable.blurbackground);
// cdd.getWindow().setBackgroundDrawableResource(R.drawable.display_translucent_appbar);

        cdd.show();
        cdd.getWindow().setAttributes(lp);
    }

    public void showAddReviewPage(View view){
        Intent addReviewActivityIntent = new Intent(HotelDetailActivity.this, AddReviewActivity.class);
       // HotelDetailActivity.this.finish();
        startActivity(addReviewActivityIntent);
    }

    @Override
    protected void onStop() {
        Log.i("onstop", "Hotel detail activity");
        Toast.makeText(HotelDetailActivity.this, "Onstop - Hotail detail" , Toast.LENGTH_SHORT).show();
        super.onStop();
    }
    @Override
    protected void onPause() {
        //TODO changed DataHolder.userName
        if(DataHolder.isLoggedIn) {
            DataHolder.saveFavouritesToDatabase(HotelDetailActivity.this);
        }
        super.onPause();
    }
    public void showProgressBar() {
        iconLayout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void showIconLayout() {
        iconLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

}
