package com.example.shybal.projectapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shybal.projectapp.Adapters.RecyclerViewAdapter;
import com.example.shybal.projectapp.Interfaces.ApiInterface;
import com.example.shybal.projectapp.Maps.MapFragment;
import com.example.shybal.projectapp.database.LoginDataBaseAdapter;
import com.example.shybal.projectapp.database.SaveSharedPreference;
import com.example.shybal.projectapp.dto.VenueData;
import com.example.shybal.projectapp.dto.VenueDetails;
import com.example.shybal.projectapp.gps.GpsService;
import com.example.shybal.projectapp.model.DataHolder;
import com.example.shybal.projectapp.model.FourSquareApiAuth;
import com.example.shybal.projectapp.model.Venue;
import com.example.shybal.projectapp.model.VenueList;
import com.facebook.login.LoginManager;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.example.shybal.projectapp.R.drawable.user;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        RecyclerViewAdapter.ListItemClickListener {


    private static final int NUM_LIST_ITEMS = 100;
    private static final String TOP_PICK = "topPick";
    private static final String LUNCH = "food";
    private static final String COFFEE = "coffee";
    private static final String CHECKIN = "checkin";
    private static final String NUM_OF_PHOTOS = "1";
    private static final String SORT_BY_DISTANCE = "1";


    private static final int NEAR_YOU_BUTTON = 1;
    private static final int TOP_PICK_BUTTON = 2;
    private static final int POPULAR_BUTTON = 3;
    private static final int LUNCH_BUTTON = 4;
    private static final int COFFEE_BUTTON = 5;

    private RecyclerViewAdapter mAdapter;
    private RecyclerView list;
    private BroadcastReceiver broadcastReceiver;
    private ProgressBar progressBar;
    private VenueList venueList;
    double latitude;
    double longitude;
    boolean IsLoggedIn;
    private Retrofit.Builder builder;
    private Retrofit retrofit;
    private ApiInterface apiInterface;
    private int clicked = NEAR_YOU_BUTTON;
    private ImageView userPhoto;
    private RelativeLayout mapLayout;

    private Button nearYouButton,topPickButton,popularButton,lunchButton,coffeeButton;


    LoginDataBaseAdapter loginDataBaseAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        loginDataBaseAdapter.open();
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        nearYouButton = (Button)findViewById(R.id.near_you_button);
        topPickButton = (Button)findViewById(R.id.top_pick_button);
        popularButton = (Button)findViewById(R.id.popular_button);
        lunchButton = (Button)findViewById(R.id.lunch_button);
        coffeeButton =(Button)findViewById(R.id.coffee_button);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mapLayout = (RelativeLayout) findViewById(R.id.map_fragment_container);

        builder = new Retrofit.Builder()
                .baseUrl("https://api.foursquare.com/v2/")
                .addConverterFactory(GsonConverterFactory.create());

        retrofit = builder.build();
        apiInterface = retrofit.create(ApiInterface.class);
        venueList = new VenueList();

        list = (RecyclerView) findViewById(R.id.restaurant_details);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        list.setHasFixedSize(true);
        mAdapter = new RecyclerViewAdapter(NUM_LIST_ITEMS, this, HomeActivity.this);
        list.setAdapter(mAdapter);

        topPickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressBar();
                topPickButton.setTextColor(Color.WHITE);
                nearYouButton.setTextColor(Color.parseColor("#87797F"));
                popularButton.setTextColor(Color.parseColor("#87797F"));
                lunchButton.setTextColor(Color.parseColor("#87797F"));
                coffeeButton.setTextColor(Color.parseColor("#87797F"));
                showTopPicks();
                clicked = TOP_PICK_BUTTON;
            }
        });

        nearYouButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressBar();
                nearYouButton.setTextColor(Color.WHITE);
                topPickButton.setTextColor(Color.parseColor("#87797F"));
                popularButton.setTextColor(Color.parseColor("#87797F"));
                lunchButton.setTextColor(Color.parseColor("#87797F"));
                coffeeButton.setTextColor(Color.parseColor("#87797F"));
                showVenuesNearYou();
                clicked = NEAR_YOU_BUTTON;

            }
        });

        popularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressBar();
                popularButton.setTextColor(Color.WHITE);
                topPickButton.setTextColor(Color.parseColor("#87797F"));
                nearYouButton.setTextColor(Color.parseColor("#87797F"));
                lunchButton.setTextColor(Color.parseColor("#87797F"));
                coffeeButton.setTextColor(Color.parseColor("#87797F"));
                showVenuesByPopularity();
                clicked = POPULAR_BUTTON;

            }
        });

        lunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressBar();
                lunchButton.setTextColor(Color.WHITE);
                topPickButton.setTextColor(Color.parseColor("#87797F"));
                nearYouButton.setTextColor(Color.parseColor("#87797F"));
                coffeeButton.setTextColor(Color.parseColor("#87797F"));
                popularButton.setTextColor(Color.parseColor("#87797F"));
                showVenuesForLunch();
                clicked = LUNCH_BUTTON;


            }
        });

        coffeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressBar();
                coffeeButton.setTextColor(Color.WHITE);
                topPickButton.setTextColor(Color.parseColor("#87797F"));
                nearYouButton.setTextColor(Color.parseColor("#87797F"));
                popularButton.setTextColor(Color.parseColor("#87797F"));
                lunchButton.setTextColor(Color.parseColor("#87797F"));
                showVenuesForCoffee();
                clicked = COFFEE_BUTTON;

            }
        });


        Toolbar tool_bar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tool_bar);
        //tool_bar.setLogo(R.drawable.logo);
        Intent startService = new Intent(getApplicationContext(), GpsService.class);
        startService(startService);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        IsLoggedIn = getIntent().getBooleanExtra(LoginActivity.EXTRA_ANSWER, true);

        if (!IsLoggedIn) {
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.favourites_id).setVisible(false);
           nav_Menu.findItem(R.id.log_out_id).setVisible(false);
        }
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        //String user = getIntent().getStringExtra(LoginActivity.USER_NAME);
        userPhoto = (ImageView) hView.findViewById(R.id.imageView1);
        String photoUrl = SaveSharedPreference.getPhotoUrl(getApplicationContext());
        TextView nav_user = (TextView) hView.findViewById(R.id.user_name_id);
        nav_user.setText(DataHolder.userName);
        if(photoUrl != "") {
            Picasso.with(getApplicationContext()).load(photoUrl).resize(113, 113).into(userPhoto);
        }


    }


   @Override
    protected void onResume() {
       super.onResume();
       if (broadcastReceiver == null) {
           broadcastReceiver = new BroadcastReceiver() {
               @Override
               public void onReceive(Context context, Intent intent) {
                   latitude = (double) intent.getExtras().get("latitude");
                   longitude = (double) intent.getExtras().get("longitude");
                   reloadPage();
               }
           };
       } else {
           reloadPage();
       }
       registerReceiver(broadcastReceiver, new IntentFilter("location_update"));
   }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            Intent stopService = new Intent(getApplicationContext(), GpsService.class);
            stopService(stopService);
            HomeActivity.this.finish();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.favourites_id) {
            Intent favouritesIntent = new Intent(HomeActivity.this, FavouritesActivity.class);
            startActivity(favouritesIntent);
        } else if(id ==R.id.feedback_id) {
            Intent feedBackIntent = new Intent(HomeActivity.this, Feedback.class);
            startActivity(feedBackIntent);
        } else if(id ==R.id.about_us) {
            Intent aboutUsIntent = new Intent(HomeActivity.this, AboutUsActivity.class);
            startActivity(aboutUsIntent);
        } else if(id == R.id.log_out_id) {
        String user = getIntent().getStringExtra(LoginActivity.USER_NAME);
        Gson gson = new Gson();
        String gsonFavourite = gson.toJson(DataHolder.favVenueList);
        loginDataBaseAdapter.updateEntry(user, gsonFavourite);

        SaveSharedPreference.clearUserName(getApplicationContext());
        Intent i = new Intent(HomeActivity.this, LoginActivity.class);
        Intent stopService = new Intent(getApplicationContext(), GpsService.class);
        stopService(stopService);
        LoginManager.getInstance().logOut();
        HomeActivity.this.finish();
        startActivity(i);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Venue.venue = venueList.getVenues().get(clickedItemIndex);
        Intent detailsIntent = new Intent(HomeActivity.this, HotelDetailActivity.class);
        startActivity(detailsIntent);
    }

    private void setMarkersOnMap(double myLat,double myLon) {

        double[] latitude = new double[4];
        double[] longitude = new double[4];
        String[] names = new String[4];

        for (int i = 0; i < 4; i++) {
            latitude[i] = venueList.getVenues().get(i).getLatitude();
            longitude[i] = venueList.getVenues().get(i).getLongitude();
            names[i] = venueList.getVenues().get(i).getName();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MapFragment mf = new MapFragment();
        mf.setCurrentLocation(myLat,myLon);
        mf.setUpMap(latitude,longitude,names);
        fragmentTransaction.add(R.id.map_fragment_container, mf);
        fragmentTransaction.commit();

    }



    @Override
    protected void onPause() {
        //TODO changed DataHolder.userName
        if(DataHolder.isLoggedIn) {
           DataHolder.saveFavouritesToDatabase(HomeActivity.this);
        }
        super.onPause();
    }



    public void showTopPicks() {
        fetchVenuesBySection(TOP_PICK);
    }

    public void showVenuesForLunch() {
        fetchVenuesBySection(LUNCH);
    }

    public void showVenuesForCoffee(){
        fetchVenuesBySection(COFFEE);
    }


    private void showVenuesNearYou() {

        Call<VenueData> call = apiInterface.fetchVenues(String.valueOf(latitude) + "," + String.valueOf(longitude), FourSquareApiAuth.getAuthToken(), FourSquareApiAuth.getVersion(), NUM_OF_PHOTOS, SORT_BY_DISTANCE);


        call.enqueue(new Callback<VenueData>() {
            @Override
            public void onResponse(Call<VenueData> call, Response<VenueData> response) {

                VenueData venueData = response.body();
                venueList.setDataToVenueList(venueData);
                //venueList.sortByDistance();
                mAdapter.setData(venueList.getVenues());
                setMarkersOnMap(latitude,longitude);
                showVenues();
            }

            @Override
            public void onFailure(Call<VenueData> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void showVenuesByPopularity() {

        Call<VenueData> call = apiInterface.fetchVenuesByCheckins(String.valueOf(latitude) + "," + String.valueOf(longitude), FourSquareApiAuth.getAuthToken(), FourSquareApiAuth.getVersion(), NUM_OF_PHOTOS, CHECKIN);


        call.enqueue(new Callback<VenueData>() {
            @Override
            public void onResponse(Call<VenueData> call, Response<VenueData> response) {

                VenueData venueData = response.body();
                venueList.setDataToVenueList(venueData);
                venueList.sortByDistance();
                mAdapter.setData(venueList.getVenues());
                setMarkersOnMap(latitude,longitude);
                showVenues();
            }

            @Override
            public void onFailure(Call<VenueData> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fetchVenuesBySection(String section) {

        Call<VenueData> call = apiInterface.fetchVenuesBySection(String.valueOf(latitude) + "," + String.valueOf(longitude), FourSquareApiAuth.getAuthToken(), FourSquareApiAuth.getVersion(), NUM_OF_PHOTOS, section);

        call.enqueue(new Callback<VenueData>() {
            @Override
            public void onResponse(Call<VenueData> call, Response<VenueData> response) {

                VenueData venueData = response.body();
                venueList.setDataToVenueList(venueData);
                venueList.sortByDistance();
                mAdapter.setData(venueList.getVenues());
                setMarkersOnMap(latitude,longitude);
                showVenues();
            }

            @Override
            public void onFailure(Call<VenueData> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void reloadPage() {
        switch(clicked) {
            case NEAR_YOU_BUTTON : showVenuesNearYou();
                                     break;
            case TOP_PICK_BUTTON : showTopPicks();
                                     break;
            case POPULAR_BUTTON : showVenuesByPopularity();
                                     break;
            case LUNCH_BUTTON : showVenuesForLunch();
                                     break;
            case COFFEE_BUTTON : showVenuesForCoffee();
                                     break;
        }
    }

    public void showVenues() {
        progressBar.setVisibility(View.GONE);
        list.setVisibility(View.VISIBLE);
        mapLayout.setVisibility(View.VISIBLE);
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        list.setVisibility(View.INVISIBLE);
        mapLayout.setVisibility(View.INVISIBLE);
    }
}