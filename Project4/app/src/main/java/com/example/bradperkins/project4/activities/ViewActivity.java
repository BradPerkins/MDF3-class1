package com.example.bradperkins.project4.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.bradperkins.project4.R;
import com.example.bradperkins.project4.fragments.ViewFragment;

public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        setTitle("View");

        getFragmentManager().beginTransaction().replace(R.id.view_fragment_container, ViewFragment.newInstance()).commit();
    }
}
