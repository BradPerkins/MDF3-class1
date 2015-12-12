package com.example.bradperkins.project3.Activities;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.bradperkins.project3.ContactProvider;
import com.example.bradperkins.project3.DataUtilites.Contact;
import com.example.bradperkins.project3.DataUtilites.DBHelper;
import com.example.bradperkins.project3.R;

import java.util.ArrayList;


/**
 * Created by bradperkins on 12/7/15.
 */

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EDITOR_REQUEST_CODE = 9999;
    private SimpleCursorAdapter  cursorAdapter;

    public ArrayList<Contact> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = new ArrayList<Contact>();

        fillList();

        Log.i("WidgetFactory", " Contact total - -  " + contactList.size());

    }

    public void fillList() {
        //Get Data
        String[] from = {DBHelper.NAME_NAME};
        //Put select data, which is thename int to text view
        int[] to = {android.R.id.text1};

        ListView list = (ListView) findViewById(R.id.mainList);

        cursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, from, to, 0);

        list.setAdapter(cursorAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                //URI that will math the content
                Uri uri = Uri.parse(ContactProvider.CONTENT_URI + "/" + id);
                intent.putExtra(ContactProvider.CONTENT_ITEM_TYPE, uri);
                startActivityForResult(intent, EDITOR_REQUEST_CODE);
            }
        });
        getLoaderManager().initLoader(0, null, this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_activity){
            Log.i("Add", "Add Clicked");

            Intent intent = new Intent(this, AddActivity.class);
            startActivityForResult(intent, EDITOR_REQUEST_CODE);
        }
        else if (id == R.id.delete_all){
            DialogInterface.OnClickListener dialogClickListener =
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int button) {
                            if (button == DialogInterface.BUTTON_POSITIVE) {

                                getContentResolver().delete(ContactProvider.CONTENT_URI, null, null);
                                restartLoader();

                                Toast.makeText(MainActivity.this,
                                        "All Items Deleted",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to delete all contacts")
                    .setPositiveButton(getString(android.R.string.yes), dialogClickListener)
                    .setNegativeButton(getString(android.R.string.no), dialogClickListener)
                    .show();


        }
        return super.onOptionsItemSelected(item);
    }

    private void restartLoader() {
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, ContactProvider.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDITOR_REQUEST_CODE && resultCode == RESULT_OK){
            restartLoader();
        }
    }
}
