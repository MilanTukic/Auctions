package com.example.tukicmilan.auctions.milan.data;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;

import com.example.tukicmilan.auctions.milan.model.Bid;

import static com.example.tukicmilan.auctions.milan.data.ItemContract.ItemEntry._ID;
import static com.example.tukicmilan.auctions.milan.data.ItemContract.PATH_ITEM;

/**
 * Created by TukicMilan on 3/7/2018.
 */

public class DAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private HelperDatabaseRead helperDatabaseRead;
    private String[] allColumns = {};



    public void deleteItem(int position){
        database = dbHelper.getWritableDatabase();
        database.execSQL("DELETE FROM " + PATH_ITEM + " WHERE " +
                _ID + " = " + position + ";");

        database.close();
    }
}
