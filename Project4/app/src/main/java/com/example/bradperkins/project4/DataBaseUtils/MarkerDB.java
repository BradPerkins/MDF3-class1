package com.example.bradperkins.project4.DataBaseUtils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bradperkins.project4.utilities.Photo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bradperkins on 12/17/15.
 */
public class MarkerDB {

    DBOpenHelper mDBOpenHelper;
    SQLiteDatabase db;

    String[] photoData = {DBOpenHelper.PHOTO_NAME, DBOpenHelper.PHOTO_TITLE, DBOpenHelper.PHOTO_URI, DBOpenHelper.PHOTO_LATITUDE, DBOpenHelper.PHOTO_LONGITUDE};

    public void addMarker(Photo photo){
        ContentValues values = new ContentValues();

        values.put(DBOpenHelper.PHOTO_NAME, photo.getPhotoName());
        values.put(DBOpenHelper.PHOTO_TITLE, photo.getPhotoTitle());
        values.put(DBOpenHelper.PHOTO_URI, photo.getPhotoUri());
        values.put(DBOpenHelper.PHOTO_LATITUDE, photo.getPhotoLat());
        values.put(DBOpenHelper.PHOTO_LONGITUDE, photo.getPhotoLng());

        db.insert(DBOpenHelper.TABLE_PHOTOS, null, values);

    }

    public List<Photo> getMyMarkers(){
        List<Photo> markers = new ArrayList<Photo>();

        Cursor cursor = db.query(DBOpenHelper.TABLE_PHOTOS, photoData, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Photo m = cursorToMarker(cursor);
            markers.add(m);
            cursor.moveToNext();
        }
        cursor.close();


        return markers;
    }

    private Photo cursorToMarker(Cursor cursor) {
        Photo photo = new Photo(DBOpenHelper.PHOTO_NAME, DBOpenHelper.PHOTO_TITLE, DBOpenHelper.PHOTO_URI, DBOpenHelper.PHOTO_LATITUDE, DBOpenHelper.PHOTO_LONGITUDE);
        photo.setPhotoName(cursor.getString(0));
        photo.setPhotoTitle(cursor.getString(1));
        photo.setPhotoUri(cursor.getString(2));
        photo.setPhotoLat(cursor.getString(3));
        photo.setPhotoLng(cursor.getString(4));
        return photo;
    }

    public void deleteMarker(Photo photo){
        db.delete(DBOpenHelper.TABLE_PHOTOS, DBOpenHelper.PHOTO_ID + " = '" + photo.getPosition() + "'", null);
    }


    public void open() throws SQLException {
        db = mDBOpenHelper.getWritableDatabase();
    }


}
