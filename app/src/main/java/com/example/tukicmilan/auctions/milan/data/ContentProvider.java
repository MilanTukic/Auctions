package com.example.tukicmilan.auctions.milan.data;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by asski on 23-Aug-17.
 */

public class ContentProvider extends android.content.ContentProvider {

    public static final String LOG_TAG = ContentProvider.class.getSimpleName();
    /**
     * URI matcher code for the whole Users table
     */
    private static final int USERS = 100;
    /**
     * URI matcher code for a single User
     */
    private static final int USER_ID = 101;

    /**
     * URI matcher for the whole Items table
     */
    private static final int ITEMS = 200;

    /**
     * URI matcher for a single ITEM
     */
    private static final int ITEM_ID = 201;

    /**
     * URI matcher for the whole Bids table
     *
     * @return
     */
    private static final int BIDS = 300;

    /**
     * URI matcher for a single BID
     *
     * @return
     */
    private static final int BID_ID = 301;
    /**
     * URI matcher for the whole Auctions table
     *
     * @return
     */
    private static final int AUCTIONS = 400;

    /**
     * URI matcher for a single AUCTION
     *
     * @return
     */
    private static final int AUCTION_ID = 401;

    /**
     * Root URI
     */
    public static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(UserContract.CONTENT_AUTHORITY, UserContract.PATH_USER, USERS);
        sUriMatcher.addURI(UserContract.CONTENT_AUTHORITY, UserContract.PATH_USER + "/#", USER_ID);

        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_ITEM, ITEMS);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_ITEM + "/#", ITEM_ID);


        sUriMatcher.addURI(BidContract.CONTENT_AUTHORITY, BidContract.PATH_BID, BIDS);
        sUriMatcher.addURI(BidContract.CONTENT_AUTHORITY, BidContract.PATH_BID + "/#", BID_ID);

        sUriMatcher.addURI(AuctionContract.CONTENT_AUTHORITY, AuctionContract.PATH_AUCTION, AUCTIONS);
        sUriMatcher.addURI(AuctionContract.CONTENT_AUTHORITY, AuctionContract.PATH_AUCTION + "/4", AUCTION_ID);
    }

    Context context = getContext();
    private DatabaseHelper mDbHelper;
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;

    /**
     * Open the database connection
     *
     * @return
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection
     *
     * @return
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }
    //new DatabaseHelper don't use singlton
    @Override
    public boolean onCreate() {
        mDbHelper =  DatabaseHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // Get readable database
        // TODO: ubaci context u DatabaseHelper
        //ovde isto
        this.openHelper =  DatabaseHelper.getInstance(getContext());
        open();
        // Cursor
        Cursor cursor = null;
        // Match the correct URI
        int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                cursor = database.query(ItemContract.ItemEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case ITEM_ID:
                selection = ItemContract.ItemEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(ItemContract.ItemEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case USERS:
                // This will return all users with the given projection, selection, selection arguments
                // and sort order.
                cursor = database.query(UserContract.UserEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case USER_ID:
                selection = UserContract.UserEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(UserContract.UserEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case BIDS:
                // This will return all bids with the given projection, selection and other arguments
                cursor = database.query(BidContract.BidEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case BID_ID:
                selection = BidContract.BidEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(BidContract.BidEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case AUCTIONS:
                // This will return all bids with the given projection, selection and other arguments
                cursor = database.query(AuctionContract.AuctionEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case AUCTION_ID:
                selection = AuctionContract.AuctionEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(AuctionContract.AuctionEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        //close();
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            // Since we're inserting a single user, there's no need to match USER_ID URI
            case USERS:
                return insertUser(uri, values);
            case ITEMS:
                return insertItem(uri, values);
            case BIDS:
                return insertBid(uri, values);
            case AUCTIONS:
                return insertAuction(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for: " + uri);
        }
    }

    private Uri insertAuction(Uri uri, ContentValues values) {
        // DATA VALIDATION
        String startDate = values.getAsString(AuctionContract.AuctionEntry.COLUMN_START_DATE);
        String endDate = values.getAsString(AuctionContract.AuctionEntry.COLUMN_END_DATE);
        String startPrice = values.getAsString(AuctionContract.AuctionEntry.COLUMN_START_PRICE);
        String phone = values.getAsString(AuctionContract.AuctionEntry.COLUMN_PHONE);
        String itemId = values.getAsString(AuctionContract.AuctionEntry.COLUMN_ITEM_ID);
        String userId = values.getAsString(AuctionContract.AuctionEntry.COLUMN_USER_ID);

        if (startDate == null || endDate == null || startPrice == null || phone == null
                || itemId == null || userId == null) {
            throw new IllegalArgumentException("Auctions arguments cannot be empty!");
        }
        // Gain write access to the database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Insert a new auctions
        long id = database.insert(AuctionContract.AuctionEntry.TABLE_NAME, null, values);

        // check if insertion is successful, -1 = failed
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row " + uri);
            Toast.makeText(getContext(), "Failed to add a new auction", Toast.LENGTH_SHORT).show();
            return null;
        }
        return ContentUris.withAppendedId(uri, id);
    }

    private Uri insertBid(Uri uri, ContentValues values) {
        // DATA VALIDATION
        String dateTime = values.getAsString(BidContract.BidEntry.COLUMN_DATE_TIME);
        String price = values.getAsString(BidContract.BidEntry.COLUMN_PRICE);
        String auctionID = values.getAsString(BidContract.BidEntry.COLUMN_AUCTION_ID);
        String userID = values.getAsString(BidContract.BidEntry.COLUMN_USER_ID);

        if (dateTime == null || price == null || auctionID == null || userID == null) {
            throw new IllegalArgumentException("Bid arguments cannot be empty!");
        }

        // Gain write access to the database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        // Insert a new bid
        long id = database.insert(BidContract.BidEntry.TABLE_NAME, null, values);
        // check if insertion is successful, -1 = failed
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row " + uri);
            Toast.makeText(getContext(), "Failed to add a new bid", Toast.LENGTH_SHORT).show();
            return null;
        }
        return ContentUris.withAppendedId(uri, id);
    }

    private Uri insertUser(Uri uri, ContentValues values) {
        // DATA VALIDATION
        String name = values.getAsString(UserContract.UserEntry.COLUMN_NAME);
        String email = values.getAsString(UserContract.UserEntry.COLUMN_EMAIL);
        String password = values.getAsString(UserContract.UserEntry.COLUMN_PASSWORD);
        String phone = values.getAsString(UserContract.UserEntry.COLUMN_PHONE);
        String address = values.getAsString(UserContract.UserEntry.COLUMN_ADDRESS);
        String picture = values.getAsString(UserContract.UserEntry.COLUMN_PICTURE);
        if (name == null || email == null || password == null || phone == null || address == null ) {
            throw new IllegalArgumentException("User arguments cannot be empty");
        }
        // Gain write access to the database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        // Insert a new user
        long id = database.insert(UserContract.UserEntry.TABLE_NAME, null, values);
        // check if insertion is successful, -1 = failed
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row " + uri);
            Toast.makeText(getContext(), "Failed to add a new user", Toast.LENGTH_SHORT).show();
            return null;
        }
        return ContentUris.withAppendedId(uri, id);
    }

    private Uri insertItem(Uri uri, ContentValues values) {
        String name = values.getAsString(ItemContract.ItemEntry.COLUMN_NAME);
        String description = values.getAsString(ItemContract.ItemEntry.COLUMN_DESCRIPTION);
        String picture = values.getAsString(ItemContract.ItemEntry.COLUMN_PICTURE);
        String sold = values.getAsString(ItemContract.ItemEntry.COLUMN_SOLD);

        if (name == null || sold == null || description == null) {
            throw new IllegalArgumentException("Cannot be empty!");
        }

        // if the arguments are not null, try to add a new item to the db
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        // Insert a new item
        long id = database.insert(ItemContract.ItemEntry.TABLE_NAME, null, values);
        // check if the insertion was successful
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row " + uri);
            Toast.makeText(getContext(), "Failed to add a new item", Toast.LENGTH_SHORT);
            return null;
        }
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        database.delete(ItemContract.ItemEntry.TABLE_NAME, selection, selectionArgs);
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
