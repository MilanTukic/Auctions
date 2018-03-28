package com.example.tukicmilan.auctions.milan.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by asski on 23-Aug-17.
 */

public class AuctionContract {
    // Content authority
    public static final String CONTENT_AUTHORITY = "com.example.tukicmilan.auctions.milan";
    // URI
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    // Table name
    public static final String PATH_AUCTION = "auction";

    private AuctionContract() {
    }

    public static class AuctionEntry implements BaseColumns {
        // Complete URI for the Auction table
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_AUCTION);
        // Name of the database table
        public static final String TABLE_NAME = "auction";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_ITEM_ID = "item_id";
        public static final String COLUMN_START_PRICE = "start_price";
        public static final String COLUMN_START_DATE = "start_date";
        public static final String COLUMN_END_DATE = "end_date";
        public static final String COLUMN_PHONE = "phone";
    }
}
