package com.example.tukicmilan.auctions.milan.data;

import android.net.Uri;
import android.provider.BaseColumns;

import java.sql.Blob;

/**
 * Created by asski on 23-Aug-17.
 */

public final class ItemContract {
    // Content authority
    public static final String CONTENT_AUTHORITY = "com.example.tukicmilan.auctions.milan";
    // URI
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    // Table name
    public static final String PATH_ITEM = "item";

    private ItemContract() {
    }

    public static class ItemEntry implements BaseColumns {
        // Complete URI for the Item table
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ITEM);
        // Name of the database table for item
        public static final String TABLE_NAME = "item";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_PICTURE = "picture";
        public static final String COLUMN_SOLD = "sold";

        public static final int SOLD_YES = 1;
        public static final int SOLD_NO = 0;
    }

}
