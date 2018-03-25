package com.example.shybal.projectapp.gps;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;

/**
 * Created by shybal on 17/5/17.
 */

public class GpsService extends Service {
    private LocationListener locationListener;
    private LocationManager locationManager;
    private boolean locationReceived = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                if(!locationReceived){
                    locationReceived = true;
                    Intent i = new Intent("location_update");
                    i.putExtra("coordinates",location.getLatitude()+" "+location.getLongitude());
                    i.putExtra("latitude",location.getLatitude());
                    i.putExtra("longitude",location.getLongitude());
                    sendBroadcast(i);

                }


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            }
        };

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        //noinspection MissingPermission
        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,3000,0,locationListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(locationManager != null){
            locationManager.removeUpdates(locationListener);
        }
    }
}
