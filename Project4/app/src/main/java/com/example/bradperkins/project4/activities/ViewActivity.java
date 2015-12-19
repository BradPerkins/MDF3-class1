package com.example.bradperkins.project4.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bradperkins.project4.R;

/**
 * Created by bradperkins on 12/16/15.
 */

public class ViewActivity extends AppCompatActivity {

    ImageView photoView;
    TextView titleText;
    TextView detailText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        photoView = (ImageView)findViewById(R.id.photo_image_view);
        titleText = (TextView)findViewById(R.id.title_text);
        detailText = (TextView)findViewById(R.id.detail_text);

        Intent intent = getIntent();
        String pTitle = intent.getStringExtra("title");
        String pDetail = intent.getStringExtra("detail");
        String pPhoto = intent.getStringExtra("photo");
        int photoRes = Integer.parseInt(pPhoto);

        titleText.setText(pTitle);
        detailText.setText(pDetail);
        photoView.setImageResource(photoRes);


    }

}
