package com.example.bradperkins.project4.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.bradperkins.project4.R;
import com.example.bradperkins.project4.activities.FormActivity;


public class FormFragment extends Fragment {

    private static final int REQUEST_TAKE_PICTURE = 0x01001;


    Button takePic;
    Button savePic;

    public static ImageView imageView;
    public static EditText nameET;
    public static EditText titleET;
    public static double photoLng;
    public static double photoLat;

    FormActivity mFormActivity;

    public static FormFragment newInstance() {
        FormFragment fragment = new FormFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public FormFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_form, container, false);

        nameET = (EditText)view.findViewById(R.id.name_edit_text);
        titleET = (EditText)view.findViewById(R.id.title_edit_text);

        imageView = (ImageView) view.findViewById(R.id.imageView);

        savePic = (Button) view.findViewById(R.id.save_pic);
        takePic = (Button) view.findViewById(R.id.take_pic);

        return view;
    }



}
