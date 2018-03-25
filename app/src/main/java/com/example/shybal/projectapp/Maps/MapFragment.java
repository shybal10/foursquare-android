package com.example.shybal.projectapp.Maps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Toast;

import com.example.shybal.projectapp.Adapters.RecyclerViewAdapter;
import com.example.shybal.projectapp.HotelDetailActivity;
import com.example.shybal.projectapp.R;
import com.example.shybal.projectapp.model.Venue;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static com.example.shybal.projectapp.R.id.map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback{

    GoogleMap mGoogleMap;
    View mView;
    MapView mMapView;
    public double Latitude = 13.3784606;
    public double Longitude = 74.7400902;

    double[] lat = new double[4];
    double[] lon = new double[4];
    String[] name = new String[4];

    private BroadcastReceiver broadcastReceiver;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Latitude =  (double) intent.getExtras().get("latitude");
                    Longitude = (double) intent.getExtras().get("longitude");
                    Toast.makeText(getActivity(),String.valueOf(Latitude),Toast.LENGTH_LONG).show();

                }
            };

        }
        getActivity().registerReceiver(broadcastReceiver,new IntentFilter("location_update"));


    }


    @Override
    public void onDestroy() {
        super.onDestroy();

            if(broadcastReceiver != null){
                getActivity().unregisterReceiver(broadcastReceiver);
            }
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_map, container, false);

        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Latitude =  (double) intent.getExtras().get("latitude");
                    Longitude = (double) intent.getExtras().get("longitude");

                }
            };


        }
        getActivity().registerReceiver(broadcastReceiver,new IntentFilter("location_update"));

        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Latitude =  (double) intent.getExtras().get("latitude");
                    Longitude = (double) intent.getExtras().get("longitude");

                }
            };

        }
        getActivity().registerReceiver(broadcastReceiver,new IntentFilter("location_update"));

        mMapView = (MapView) mView.findViewById(map);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Latitude =  (double) intent.getExtras().get("latitude");
                    Longitude = (double) intent.getExtras().get("longitude");

                }
            };

        }
        getActivity().registerReceiver(broadcastReceiver,new IntentFilter("location_update"));


        mGoogleMap = googleMap;
        MapsInitializer.initialize(getContext());
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        ArrayList<Marker> markers  = new ArrayList<>();
        ArrayList<MarkerOptions> markerOptions = new ArrayList<>();

        markerOptions.add(new MarkerOptions().position(new LatLng(lat[0],lon[0])).title(name[0]));
        markerOptions.add(new MarkerOptions().position(new LatLng(lat[1],lon[1])).title(name[1]));
        markerOptions.add(new MarkerOptions().position(new LatLng(lat[2],lon[2])).title(name[2]));
        markerOptions.add(new MarkerOptions().position(new LatLng(lat[3],lon[3])).title(name[3]));
        markerOptions.add(new MarkerOptions().position(new LatLng(Latitude,Longitude)).title("you are here!").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        LatLngBounds.Builder builder = new LatLngBounds.Builder();


        markers.add(googleMap.addMarker(markerOptions.get(0)));
        markers.add(googleMap.addMarker(markerOptions.get(1)));
        markers.add(googleMap.addMarker(markerOptions.get(2)));
        markers.add(googleMap.addMarker(markerOptions.get(3)));
        markers.add(googleMap.addMarker(markerOptions.get(4)));


        googleMap.setPadding(0,130,0,0);
        for(Marker marker : markers){
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();
        CameraUpdate mapCamera = CameraUpdateFactory.newLatLngBounds(bounds, 10);
        googleMap.moveCamera(mapCamera);

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                //String title = marker.getTitle();
                LatLng position = marker.getPosition();
                double latitude = position.latitude;
                double longitude = position.longitude;
                latitude = Math.round(latitude * 100000.0) / 100000.0;
                longitude = Math.round(longitude * 100000.0) / 100000.0;

                for (int i = 0; i < RecyclerViewAdapter. venues.size(); i++) {
                    double lat = Math.round(RecyclerViewAdapter.venues.get(i).getLatitude() * 100000.0) / 100000.0;
                    double lng = Math.round(RecyclerViewAdapter.venues.get(i).getLongitude() * 100000.0) / 100000.0;

                    if (lat == latitude && lng == longitude) {
                        Venue clickedVenue = RecyclerViewAdapter.venues.get(i);
                        Venue.venue = clickedVenue;
                        Intent intent1 = new Intent(getContext(), HotelDetailActivity.class);
                        startActivity(intent1);
                    }
                }
            }
        });

}

    public void setUpMap(double[] latitude, double[] longitude, String[] names){
        for(int i =0; i < 4;i++){
            lat[i] = latitude[i];
            lon[i] = longitude[i];
            name[i] = names[i];
        }
    }


    public void setCurrentLocation(double myLatitude,double myLongitude){
        Latitude = myLatitude;
        Longitude = myLongitude;

    }

}
