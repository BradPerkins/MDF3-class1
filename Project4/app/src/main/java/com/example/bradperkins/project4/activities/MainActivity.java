package com.example.bradperkins.project4.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.bradperkins.project4.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;



public class MainActivity extends AppCompatActivity{

    private static final int REQUEST_TAKE_PICTURE = 0x01001;

    static final LatLng CurrPos = new LatLng(40, -79);

    private GoogleMap googleMap;
    Uri mImageUri;
    Button testBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            if(googleMap == null){
                googleMap = ((com.google.android.gms.maps.MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();

            }
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            Marker marker = googleMap.addMarker(new MarkerOptions().position(CurrPos).title("Hello"));

        }catch (Exception e){
            e.printStackTrace();
        }

        testBtn = (Button)findViewById(R.id.testButton);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent(v.getContext(), ViewActivity.class);
                startActivity(viewIntent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_take_picture) {
            Intent cameraIntent = new Intent(this, FormActivity.class);
            startActivity(cameraIntent);
        }
        return true;
    }



//    @Override
//    public void onClick(View v) {
//        switch(v.getId()) {
//            case R.id.testButton:
//                Intent cameraIntent = new Intent(this, FormActivity.class);
//                startActivity(cameraIntent);
//                break;
//
//        }
//    }
}
