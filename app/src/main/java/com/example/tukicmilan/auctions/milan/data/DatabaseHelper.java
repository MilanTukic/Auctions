package com.example.tukicmilan.auctions.milan.data;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


/**
 * Created by asski on 23-Aug-17.
 */

public class DatabaseHelper extends SQLiteAssetHelper {
    private static DatabaseHelper mInstance = null;
    /**
     * name file of database
     */
    public static final String DATABASE_NAME = "licitacije.db";
    /**
     * Database version
     */
    public static final int DATABASE_VERSION = 1;
    public final String LOG_TAG = getClass().getSimpleName();

    public static DatabaseHelper getInstance(Context ctx) {

        if (mInstance == null) {
            mInstance = new DatabaseHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }
    /**
     * Constructor
     */
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
