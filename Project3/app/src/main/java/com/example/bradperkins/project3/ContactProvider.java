package com.example.bradperkins.project3;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.bradperkins.project3.DataUtilites.DBHelper;

/**
 * Created by bradperkins on 12/9/15.
 */
public class ContactProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.bradperkins.project3";
    private static final String BASE_PATH = "contacts";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );

    // Constant to identify the requested operation
    private static final int NAMES = 1;
    private static final int NAMES_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public static final String CONTENT_ITEM_TYPE = "Contact";

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, NAMES);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", NAMES_ID );
    }


    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {

        DBHelper helper = new DBHelper(getContext());
        database = helper.getWritableDatabase();

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        if (uriMatcher.match(uri) == NAMES_ID){
            selection = DBHelper.NAME_ID + "=" + uri.getLastPathSegment();
        }


        return database.query(DBHelper.TABLE_NAMES, DBHelper.ALL_COLUMNS, selection, null, null, null, DBHelper.NAME_NAME + " DESC");
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = database.insert(DBHelper.TABLE_NAMES, null, values);

        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return database.delete(DBHelper.TABLE_NAMES, selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return database.update(DBHelper.TABLE_NAMES, values, selection,selectionArgs);
    }
}
