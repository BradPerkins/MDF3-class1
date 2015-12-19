package com.example.bradperkins.project4.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bradperkins.project4.R;
import com.example.bradperkins.project4.utilities.Photo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

/**
 * Created by bradperkins on 12/16/15.
 */

public class MainActivity extends AppCompatActivity implements LocationListener, GoogleMap.OnMarkerClickListener {

    private static final int REQUEST_ENABLE_GPS = 0x02001;

    private static final String FILENAME = "photo_data.txt";
    FormActivity mFormActivity;

    LocationManager mManager;

    private GoogleMap googleMap;

    String mCoordinates;
    String[] coordsSplit;

    double currLat;
    double currLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        openMap();

        //Hard Code Data
        final ArrayList<Photo> photos = new ArrayList<>();

        LatLng cinLoc = new LatLng(39.0954, -84.5160);
        LatLng cleLoc = new LatLng(41.5061, -81.6994);
        LatLng balLoc = new LatLng(39.2779, -76.6226);
        LatLng pitLoc = new LatLng(40.4467, -80.0158);

        photos.add(new Photo("Bengals", "Paul Brown Stadium", R.drawable.bengals, cinLoc));
        photos.add(new Photo("Browns", "Cleveland Stadium", R.drawable.browns, cleLoc));
        photos.add(new Photo("Ravens", "Ravens Stadium", R.drawable.ravens, balLoc));
        photos.add(new Photo("Steelers", "Steelers Stadium", R.drawable.steelers, pitLoc));


        for (int i=0; i<photos.size(); i++){
            googleMap.addMarker(new MarkerOptions()
                            .title(photos.get(i).getName())
                            .snippet(photos.get(i).getDetail())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                            .position(photos.get(i).getCoordinates())
            );

            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    int markerPos = Integer.parseInt(marker.getId().substring(1));

                    String titleString = "" + markerPos;
                    String markerName = photos.get(markerPos).getName().toString();
                    String markerDetail = photos.get(markerPos).getDetail().toString();
                    String markerPhoto = String.valueOf(photos.get(markerPos).getUri());

                    Log.i("Click", " //////////// " + markerPhoto);

                    Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                    intent.putExtra("title", markerName);
                    intent.putExtra("detail", markerDetail);
                    intent.putExtra("photo", markerPhoto);

                    startActivity(intent);

                }
            });

        }

        mFormActivity = new FormActivity();

        //LongClick to go to form
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Intent formIntent = new Intent(MainActivity.this, FormActivity.class);
                startActivity(formIntent);
            }
        });
    }

    //Enables the GPS
    public void enableGps() {
        if (mManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, (LocationListener) this);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
            }
            Location loc = mManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(loc != null) {
                mCoordinates = loc.getLatitude() + "," + loc.getLongitude();

                //Grabs the lat and lng
                coordsSplit = mCoordinates.split(",");
                currLat = Double.parseDouble(coordsSplit[0]);
                currLng= Double.parseDouble(coordsSplit[1]);

                //Zooms to current loaction
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currLat, currLng), 5));

                Log.i("Location", " " + loc.getLatitude() + "    " +loc.getLongitude());
            }

        } else {
            new AlertDialog.Builder(this)
                    .setTitle("GPS Unavailable")
                    .setMessage("Please enable GPS in the system settings.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(settingsIntent, REQUEST_ENABLE_GPS);
                        }

                    })
                    .show();


        }
        //Hard codes lat and lng if gps not availiable
        mCoordinates = "39.0954,-84.5160";
        currLat = Double.parseDouble(coordsSplit[0]);
        currLng= Double.parseDouble(coordsSplit[1]);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currLat, currLng), 5));
    }

    @Override
    protected void onPause() {
        //mMarkerHelper.close();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        enableGps();
    }


    //OPen Map Method
    private void openMap(){
        googleMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();

        googleMap.setMyLocationEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_take_picture) {
            Intent cameraIntent = new Intent(this, FormActivity.class);
            startActivity(cameraIntent);
        }
        return true;
    }



    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            currLat = location.getLatitude();
            currLng = location.getLongitude();
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

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }



    //Read the data stored
    public void readFromFile() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        ArrayList<Photo> photos = null;
        try {
            fis = this.openFileInput(FILENAME);
            ois = new ObjectInputStream(fis);
            photos = (ArrayList<Photo>) ois.readObject();
            ois.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (fis != null) {
                fis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
