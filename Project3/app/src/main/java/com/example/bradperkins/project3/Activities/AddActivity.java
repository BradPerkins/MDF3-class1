package com.example.bradperkins.project3.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bradperkins.project3.ContactProvider;
import com.example.bradperkins.project3.DataUtilites.DBHelper;
import com.example.bradperkins.project3.R;

/**
 * Created by bradperkins on 12/9/15.
 */

public class AddActivity extends AppCompatActivity {

    private String action;
    private EditText nameET;
    private EditText phoneET;
    private EditText emailET;



    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        nameET = (EditText) findViewById(R.id.name_edit_text);
        phoneET = (EditText) findViewById(R.id.phone_edit_text);
        emailET = (EditText) findViewById(R.id.email_edit_text);

        saveBtn = (Button) findViewById(R.id.saveBtn);


        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra(ContactProvider.CONTENT_ITEM_TYPE);

        if (uri == null){
            action = Intent.ACTION_INSERT;
            setTitle("New Contact");
        }


    }


    public void saveData(View view) {

        String nameText = nameET.getText().toString().trim();
        String phoneText = phoneET.getText().toString().trim();
        String emailText = emailET.getText().toString().trim();

        if (nameText.isEmpty() || phoneText.isEmpty() || emailText.isEmpty()) {
            Toast.makeText(AddActivity.this, "Enter Valid Data", Toast.LENGTH_SHORT).show();
        }
        else {
            insertName(nameText, phoneText, emailText);
            Toast.makeText(AddActivity.this, "New Contact Added", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

        }
        finish();

    }

    private void finishAdd(){
        String nameText = nameET.getText().toString().trim();
        String winsText = phoneET.getText().toString().trim();
        String lossesText = emailET.getText().toString().trim();

        switch (action){
            case Intent.ACTION_INSERT:
                if(nameText.length() == 0){
                    setResult(RESULT_CANCELED);
                }
                else {
                    insertName(nameText, winsText, lossesText);
                }
        }
        finish();
    }


    //Inserts the values into the db
    public void insertName(String nameText, String winsText, String lossesText) {

        ContentValues values = new ContentValues();
        values.put(DBHelper.NAME_NAME, nameText);
        values.put(DBHelper.NAME_PHONE, winsText);
        values.put(DBHelper.NAME_EMAIL, lossesText);

        getContentResolver().insert(ContactProvider.CONTENT_URI, values);
        setResult(RESULT_OK);
    }

    @Override
    public void onBackPressed() {
        finishAdd();
    }



}
