package com.example.bradperkins.project4.fragments;

import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bradperkins.project4.R;
import com.example.bradperkins.project4.activities.FormActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by bradperkins on 12/17/15.
 */
public class MapDisplayFragment extends MapFragment {



    private static final int REQUEST_TAKE_PICTURE = 0x01001;

    FormActivity mFormActivity;

    private String action;
    private String dbFilter;
    SQLiteDatabase db;

    LocationManager mManager;
    private static final int REQUEST_ENABLE_GPS = 0x02001;
    double mLatitude;
    double mLongitude;

    LatLng CurrPos;


    private GoogleMap googleMap;
    Uri mImageUri;
    Button testBtn;

    public static MapDisplayFragment newInstance() {
        MapDisplayFragment fragment = new MapDisplayFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MapDisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_display, container, false);








        return view;
    }


}
