package com.example.bradperkins.project3.DataUtilites;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bradperkins on 12/07/15.
 */
public class DBHelper extends SQLiteOpenHelper {

    //Constants for db name and version
    private static final String DATABASE_NAME = "contact.db";
    private static final int DATABASE_VERSION = 5;

    //Constants for identifying table and columns
    public static final String TABLE_NAMES = "contacts";
    public static final String NAME_ID = "_id";
    public static final String NAME_NAME = "name";
    public static final String  NAME_PHONE = "phone";
    public static final String NAME_EMAIL = "email";

    //All columns
    public static final String[] ALL_COLUMNS = {NAME_ID, NAME_NAME, NAME_PHONE, NAME_EMAIL};

    //SQL to create table
    private static final String TABLE_CREATE = "CREATE TABLE " +
                    TABLE_NAMES + " (" +
                    NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME_NAME + " TEXT, " +
                    NAME_PHONE + " TEXT, " +
                    NAME_EMAIL + " TEXT" +
                    ")";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMES);
        onCreate(db);
    }
}
