package com.example.tukicmilan.auctions.milan.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by asski on 23-Aug-17.
 */

public class BidContract {
    // Content authority
    public static final String CONTENT_AUTHORITY = "com.example.tukicmilan.auctions.milan";
    // URI
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    // Table name
    public static final String PATH_BID = "bid";

    private BidContract() {
    }

    public static class BidEntry implements BaseColumns {
        // Complete URI for the Bid table
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BID);
        // Name of the database table for bid
        public static final String TABLE_NAME = "bid";

        public static final String _ID = BaseColumns._ID;

        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_AUCTION_ID = "auction_id";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_DATE_TIME = "date_time";
    }
}
