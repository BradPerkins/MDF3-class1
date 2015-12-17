package com.example.bradperkins.project4.DataBaseUtils;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by bradperkins on 12/16/15.
 */
public class PhotoProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.bradperkins.project4";
    //private static final String AUTHORITY = "com.example.bradperkins.project4.DataBaseUtils";
    private static final String BASE_PATH = "photos";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    // Constant to identify the requested operation
    private static final int PHOTOS = 1;
    private static final int PHOTO_ID = 2;

    //Parse the URI
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public static final String CONTENT_ITEM_TYPE = "Photo";

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, PHOTOS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", PHOTO_ID);
    }

    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        DBOpenHelper helper = new DBOpenHelper(getContext());
        database = helper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (uriMatcher.match(uri) == PHOTO_ID) {
            selection = DBOpenHelper.PHOTO_ID + "=" + uri.getLastPathSegment();
        }

        return database.query(DBOpenHelper.TABLE_PHOTOS, DBOpenHelper.ALL_COLUMNS,
                selection, null, null, null,
                DBOpenHelper.PHOTO_NAME + " DESC");
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = database.insert(DBOpenHelper.TABLE_PHOTOS, null, values);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return database.delete(DBOpenHelper.TABLE_PHOTOS, selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return database.update(DBOpenHelper.TABLE_PHOTOS, values, selection,selectionArgs);
    }
}
