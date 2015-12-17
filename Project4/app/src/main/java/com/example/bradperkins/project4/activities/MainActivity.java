package com.example.bradperkins.project4.activities;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CursorAdapter;

import com.example.bradperkins.project4.DataBaseUtils.MarkerDB;
import com.example.bradperkins.project4.DataBaseUtils.PhotoProvider;
import com.example.bradperkins.project4.R;
import com.example.bradperkins.project4.utilities.Photo;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_TAKE_PICTURE = 0x01001;

    FormActivity mFormActivity;
    MarkerDB mMarkerDB;

    Context context;

    private String action;
    private String dbFilter;
    SQLiteDatabase db;

    LocationManager mManager;
    private static final int REQUEST_ENABLE_GPS = 0x02001;
    double mLatitude;
    double mLongitude;

    LatLng CurrPos;

    private CursorAdapter cursorAdapter;


    private GoogleMap googleMap;
    Uri mImageUri;
    Button testBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mManager = (LocationManager) getSystemService(LOCATION_SERVICE);

//        mMarkerDB = new MarkerDB();
//        mMarkerDB.addMarker(new Photo(DBOpenHelper.PHOTO_NAME, DBOpenHelper.PHOTO_TITLE, DBOpenHelper.PHOTO_URI, DBOpenHelper.PHOTO_LATITUDE, DBOpenHelper.PHOTO_LONGITUDE));

        mFormActivity = new FormActivity();

        //mFormActivity.enableGps();

//        CurrPos = new LatLng(mLatitude, mLongitude);
        CurrPos = new LatLng(0, 0);

        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra(PhotoProvider.CONTENT_ITEM_TYPE);
//
        try {
            if (googleMap == null) {
                //for ()
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            }
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            List<Photo> photos = mMarkerDB.getMyMarkers();
            for (int i = 0; i < photos.size(); i++){
                String[] posLatLng = photos.get(i).getPosition().split(" ");
                //LatLng latLng = new LatLng(Double.valueOf(posLatLng[0]), Double.valueOf(posLatLng[1]));

                googleMap.addMarker(new MarkerOptions().title(photos.get(i).getPhotoTitle()).snippet(photos.get(i).getPhotoTitle()).position(CurrPos));
                System.out.println("//////////// " + photos.get(i).getPhotoTitle());
            }

            Marker marker = googleMap.addMarker(new MarkerOptions().position(CurrPos).title("Helloooooo"));
            //Long click to go to Form
            googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    Intent formIntent = new Intent(MainActivity.this, FormActivity.class);
                    startActivity(formIntent);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

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


}
