package com.example.tukicmilan.auctions.milan.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by asski on 23-Aug-17.
 */

public final class UserContract {
    // Content authority
    public static final String CONTENT_AUTHORITY = "com.example.tukicmilan.auctions.milan";
    // URI
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    // Table name
    public static final String PATH_USER = "user";

    private UserContract() {
    }

    /**
     * Inner class that defines constant values for the user database table.
     * Each entry in the table represents a single user
     */

    public static class UserEntry implements BaseColumns {
        // Complete URI for the User table
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_USER);

        // Name of the database table for user
        public static final String TABLE_NAME = "user";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_PICTURE = "picture";
    }
}
