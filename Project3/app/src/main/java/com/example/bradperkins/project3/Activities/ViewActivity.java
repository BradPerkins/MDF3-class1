package com.example.bradperkins.project3.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.bradperkins.project3.ContactProvider;
import com.example.bradperkins.project3.DataUtilites.DBHelper;
import com.example.bradperkins.project3.R;

/**
 * Created by bradperkins on 12/9/15.
 */

public class ViewActivity extends AppCompatActivity {

    private String name;
    private String phone;
    private String email;

    TextView nameTV;
    TextView phoneTV;
    TextView emailTV;

    private String action;
    private String dbFilter;

    public static final String EXTRA_ITEM = "com.example.bradperkins.ViewActivity.EXTRA_ITEM";



    TextView nameTV2;
    TextView phoneTV2;
    TextView emailTV2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);


        Intent intent = getIntent();
//        Contact content = (Contact)intent.getSerializableExtra(EXTRA_ITEM);
//        if(content == null) {
//            finish();
//            return;
//        }
//
//        nameTV2 = (TextView)findViewById(R.id.name_text_view);
//        phoneTV2 = (TextView)findViewById(R.id.phone_text_view);
//        emailTV2 = (TextView)findViewById(R.id.email_text_view);
//
//
//        nameTV2.setText(content.getContactName());
//        phoneTV2.setText(content.getContactPhone());
//        emailTV2.setText(content.getContactEmail());


        nameTV = (TextView)findViewById(R.id.name_text_view);
        phoneTV = (TextView)findViewById(R.id.phone_text_view);
        emailTV = (TextView)findViewById(R.id.email_text_view);


        Uri uri = intent.getParcelableExtra(ContactProvider.CONTENT_ITEM_TYPE);

        if (uri == null){
            action = Intent.ACTION_INSERT;
            setTitle("New contact");
        }
        else {
            //grabs row
            dbFilter = DBHelper.NAME_ID + "=" + uri.getLastPathSegment();
            //Gets column data
            Cursor cursor = getContentResolver().query(uri, DBHelper.ALL_COLUMNS, dbFilter, null, null);
            cursor.moveToFirst();

            name = cursor.getString(cursor.getColumnIndex(DBHelper.NAME_NAME));
            phone = cursor.getString(cursor.getColumnIndex(DBHelper.NAME_PHONE));
            email = cursor.getString(cursor.getColumnIndex(DBHelper.NAME_EMAIL));

            //Sets the labels
            nameTV.setText(name);
            phoneTV.setText(phone);
            emailTV.setText(email);
        }




    }


}
