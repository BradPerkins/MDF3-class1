package com.example.bradperkins.project4.utilities;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by bradperkins on 12/16/15.
 */
public class LocationListener implements android.location.LocationListener {

    public static String lat;
    public static String lon;

    @Override
    public void onLocationChanged(Location location) {

        if(location != null){
            Log.i("Latitude :", " " + location.getLatitude());
            Log.i("Longitude :", " " + location.getLongitude());

            lat = String.valueOf(location.getLatitude());
            lon = String.valueOf(location.getLongitude());

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

    }
}
