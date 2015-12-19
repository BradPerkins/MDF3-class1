package com.example.bradperkins.project4.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bradperkins.project4.R;

/**
 * Created by bradperkins on 12/16/15.
 */

public class ViewFragment extends Fragment {

    public ImageView photoView;
    public TextView titleText;
    public TextView detailText;


    public static ViewFragment newInstance() {
        ViewFragment fragment = new ViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    public interface OnDataPass {
        public void onDataPass(String titleText);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view, container, false);

        photoView = (ImageView)view.findViewById(R.id.photo_image_view);
        titleText = (TextView)view.findViewById(R.id.title_text);
        detailText = (TextView)view.findViewById(R.id.detail_text);

        titleText.setText("");

        return view;
    }



}
