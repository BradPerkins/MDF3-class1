package com.example.bradperkins.project4.DataBaseUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bradperkins on 12/15/15.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    //Constants for db name and version
    private static final String DATABASE_NAME = "photoData.db";
    private static final int DATABASE_VERSION = 2;

    //Constants for identifying table and columns
    public static final String TABLE_PHOTOS = "photos";
    public static final String PHOTO_ID = "id";
    public static final String PHOTO_NAME = "photoName";
    public static final String PHOTO_TITLE = "photoTitle";
    public static final String PHOTO_URI = "uri";
    public static final String PHOTO_LATITUDE = "latitude";
    public static final String PHOTO_LONGITUDE = "longitude";

    //All columns
    public static final String[] ALL_COLUMNS = {PHOTO_ID, PHOTO_NAME, PHOTO_TITLE, PHOTO_URI, PHOTO_LATITUDE, PHOTO_LONGITUDE};

    //SQL to create table
    private static final String TABLE_CREATE = "CREATE TABLE " +
                    TABLE_PHOTOS + " (" +
                    PHOTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PHOTO_NAME + " TEXT, " +
                    PHOTO_TITLE + " TEXT, " +
                    PHOTO_URI + " TEXT, " +
                    PHOTO_LATITUDE + " DOUBLE, " +
                    PHOTO_LONGITUDE + " DOUBLE" +
                    ")";



    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHOTOS);
        onCreate(db);
    }
}
