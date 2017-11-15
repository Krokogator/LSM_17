package com.falcon.helloandroid.Weights;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by micha on 15.11.2017.
 */

public class WeightDBhelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " +
                    WeightDB.TABLE_NAME + "  (" +
                    WeightDB._ID + " INTEGER PRIMARY KEY," +
                    WeightDB.COLUMN_NAME_WEIGHT + " INTEGER," +
                    WeightDB.COLUMN_NAME_DATE + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + WeightDB.TABLE_NAME;

    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME = "Weight.db";

    public WeightDBhelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }
}
