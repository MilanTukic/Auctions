package com.example.tukicmilan.auctions.milan.data;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.util.Log;


import com.example.tukicmilan.auctions.milan.model.Auction;
import com.example.tukicmilan.auctions.milan.model.Bid;
import com.example.tukicmilan.auctions.milan.model.Item;
import com.example.tukicmilan.auctions.milan.model.User;

import java.sql.Blob;
import java.util.ArrayList;

/**
 * Created by asski on 10-Sep-17.
 */

public class HelperDatabaseRead {
        private ArrayList<Item> items = new ArrayList<>();

        public ArrayList<Item> loadItemsFromDatabase(Activity activity) {
        Log.i("ActivityName", activity.toString());
        // Definisi projekciju pomocu koje ces da ucitas podatke iz tabele item
        String[] projection = {
                ItemContract.ItemEntry._ID,
                ItemContract.ItemEntry.COLUMN_NAME,
                ItemContract.ItemEntry.COLUMN_DESCRIPTION,
                ItemContract.ItemEntry.COLUMN_PICTURE,
                ItemContract.ItemEntry.COLUMN_SOLD
        };
        String sortOrder = ItemContract.ItemEntry._ID + " DESC";
        Cursor cursor = activity.getContentResolver().query(ItemContract.ItemEntry.CONTENT_URI, projection, null, null, null);

        try {

            int idColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_NAME);
            int descriptionColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_DESCRIPTION);
            int pictureColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_PICTURE);

            int soldColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_SOLD);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String name = cursor.getString(nameColumnIndex);
                String desctiption = cursor.getString(descriptionColumnIndex);
                String picture = cursor.getString(pictureColumnIndex);

                int sold = cursor.getInt(soldColumnIndex);
                ArrayList<Auction> auctionsAll = new ArrayList<>();
                ArrayList<Auction> auctionsFiltered = new ArrayList<>();
                auctionsAll = loadAuctionsFromDatabase(activity);
                for (Auction auction : auctionsAll) {
                    if (auction.getItem().getId() == currentID) {
                        auctionsFiltered.add(auction);
                    }
                }

                items.add(new Item(name, desctiption, picture, (sold == 1), auctionsFiltered, currentID));
            }
        } finally {
            cursor.close();
        }

        return items;
    }

    public ArrayList<Auction> loadAuctionsFromDatabase(Activity activity) {
        // Definisi projekciju pomocu koje ces da ucitas podatke iz tabele auction
        String[] projection = {
                AuctionContract.AuctionEntry._ID,
                AuctionContract.AuctionEntry.COLUMN_START_PRICE,
                AuctionContract.AuctionEntry.COLUMN_START_DATE,
                AuctionContract.AuctionEntry.COLUMN_END_DATE,
                AuctionContract.AuctionEntry.COLUMN_ITEM_ID,
                AuctionContract.AuctionEntry.COLUMN_USER_ID
        };
        // Sort order
        String sortOrder = AuctionContract.AuctionEntry._ID + " DESC";

        ContentResolver contentResolver = activity.getContentResolver();
        Log.i("ActivityName", activity.toString());
        Cursor cursor = contentResolver.query(AuctionContract.AuctionEntry.CONTENT_URI, projection, null, null, null);

        ArrayList<Auction> auctions = new ArrayList<>();
        // Uzmi relevantne podatke za svaku aukciju
        try {
            // Prvo nadji index svake kolone
            int idColumnIndex = cursor.getColumnIndex(AuctionContract.AuctionEntry._ID);
            int startPriceColumnIndex = cursor.getColumnIndex(AuctionContract.AuctionEntry.COLUMN_START_PRICE);
            int startDateColumnIndex = cursor.getColumnIndex(AuctionContract.AuctionEntry.COLUMN_START_DATE);
            int endDateColumnIndex = cursor.getColumnIndex(AuctionContract.AuctionEntry.COLUMN_END_DATE);
            int userIdColumnIndex = cursor.getColumnIndex(AuctionContract.AuctionEntry.COLUMN_USER_ID);
            int itemIdColumnIndex = cursor.getColumnIndex(AuctionContract.AuctionEntry.COLUMN_ITEM_ID);

            while (cursor.moveToNext()) {
                int currentId = cursor.getInt(idColumnIndex);
                double price = cursor.getDouble(startPriceColumnIndex);
                int i = (int) price;
                String startDate = cursor.getString(startDateColumnIndex);
                String endDate = cursor.getString(endDateColumnIndex);
                int userId = Integer.parseInt(cursor.getString(userIdColumnIndex));

                int itemId = Integer.parseInt(cursor.getString(itemIdColumnIndex));

                User user = findUser(userId, activity);
                Item item = findItem(itemId, activity);
                ArrayList<Bid> bidsAll = findBids(currentId, activity);

                auctions.add(new Auction(i, startDate, endDate, user, item, bidsAll, currentId));
            }

        } finally {
            cursor.close();
        }
        return auctions;
    }

    public Item findItem(int itemId, Activity activity) {
        Item item = null;
        // Definisi projekciju pomocu koje ces da ucitas podatke iz tabele item
        String[] projection = {
                ItemContract.ItemEntry._ID,
                ItemContract.ItemEntry.COLUMN_NAME,
                ItemContract.ItemEntry.COLUMN_DESCRIPTION,
                ItemContract.ItemEntry.COLUMN_PICTURE,
                ItemContract.ItemEntry.COLUMN_SOLD
        };
        // Sort order
        String sortOrder = ItemContract.ItemEntry._ID + " DESC";
        Cursor cursor = activity.getContentResolver().query(ItemContract.ItemEntry.CONTENT_URI, projection, null, null, null);

        // Uzmi relevatne podatke za svaki item
        try {
            // Prvo nadji index svake kolone
            int idColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_NAME);
            int descriptionColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_DESCRIPTION);
            int pictureColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_PICTURE);

            int soldColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_SOLD);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String name = cursor.getString(nameColumnIndex);
                String description = cursor.getString(descriptionColumnIndex);
                String picture = cursor.getString(pictureColumnIndex);

                int sold = cursor.getInt(soldColumnIndex);
                if (currentID == itemId) {
                    item = new Item(name, description, picture, (sold == 1), null, currentID);
                    break;
                }
            }
        } finally {
            cursor.close();
        }
        return item;
    }
    public User findUser(int userId, Activity activity) {
        User user = null;
        // Definisi projekciju pomocu koje ces da ucitas podatke iz tabele item
        String[] projection = {
                UserContract.UserEntry._ID,
                UserContract.UserEntry.COLUMN_NAME,
                UserContract.UserEntry.COLUMN_EMAIL,
                UserContract.UserEntry.COLUMN_PASSWORD,
                UserContract.UserEntry.COLUMN_ADDRESS,
                UserContract.UserEntry.COLUMN_PHONE,
                UserContract.UserEntry.COLUMN_PICTURE
        };
        // Sort order
        String sortOrder = UserContract.UserEntry._ID + " DESC";
        Cursor cursor = activity.getContentResolver().query(UserContract.UserEntry.CONTENT_URI, projection, null, null, null);
        // Uzmi relevatne podatke za svakog usera
        try {

            // Prvo nadji index svake kolone
            int idColumnIndex = cursor.getColumnIndex(UserContract.UserEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_NAME);
            int emailColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_EMAIL);
            int addressColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_ADDRESS);
            int phoneColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_PHONE);
            int pictureColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_PICTURE);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String name = cursor.getString(nameColumnIndex);
                String email = cursor.getString(emailColumnIndex);
                String address = cursor.getString(addressColumnIndex);
                String phone = cursor.getString(phoneColumnIndex);
                String picture = cursor.getString(pictureColumnIndex);

                if (currentID == userId) {
//                    ArrayList<Auction> auctionsAll = new ArrayList<>();
//                    ArrayList<Auction> auctionsFiltered = new ArrayList<>();
//                    auctionsAll = loadAuctionsFromDatabase(activity);
//                    for (Auction auction : auctionsAll) {
//                        if (auction.getUser().getId() == currentID) {
//                            auctionsFiltered.add(auction);
//                        }
//                    }
//
//                    ArrayList<Bid> bidsAll = new ArrayList<>();
//                    ArrayList<Bid> bidsFiltered = new ArrayList<>();
//                    bidsAll = loadBidsFromDatabase(activity);
//                    for (Bid bid : bidsAll) {
//                        if (bid.getUser().getId() == currentID) {
//                            bidsFiltered.add(bid);
//                        }
//                    }
                    user = new User(userId, name, email, null, picture, address, phone, null, null);
                    break;
                }
            }
        } finally {
            cursor.close();
        }
        return user;
    }
    public ArrayList<Bid> findBids(int auctionIdToMatch, Activity activity) {
        ArrayList<Bid> bids = new ArrayList<>();
        // Definisi projekciju pomocu koje ces da ucitas podatke iz tabele item
        String[] projection = {
                BidContract.BidEntry._ID,
                BidContract.BidEntry.COLUMN_AUCTION_ID,
                BidContract.BidEntry.COLUMN_PRICE,
                BidContract.BidEntry.COLUMN_DATE_TIME,
                BidContract.BidEntry.COLUMN_USER_ID
        };
        // Sort order
        String sortOrder = UserContract.UserEntry._ID + " DESC";
        Cursor cursor = activity.getContentResolver().query(BidContract.BidEntry.CONTENT_URI, projection, null, null, null);

        // Uzmi relevatne podatke za svakog usera
        try {

            // Prvo nadji index svake kolone
            int idColumnIndex = cursor.getColumnIndex(BidContract.BidEntry._ID);
            int auctionIdColumnIndex = cursor.getColumnIndex(BidContract.BidEntry.COLUMN_AUCTION_ID);
            int priceColumnIndex = cursor.getColumnIndex(BidContract.BidEntry.COLUMN_PRICE);
            int dateTimeColumnIndex = cursor.getColumnIndex(BidContract.BidEntry.COLUMN_DATE_TIME);
            int userIdColumnIndex = cursor.getColumnIndex(BidContract.BidEntry.COLUMN_USER_ID);

            while (cursor.moveToNext()) {
                int currentId = cursor.getInt(idColumnIndex);
                int auctionId = cursor.getInt(auctionIdColumnIndex);
                double price = cursor.getDouble(priceColumnIndex);
                int i = (int) price;
                String dateTime = cursor.getString(dateTimeColumnIndex);
                int userId = Integer.parseInt(cursor.getString(userIdColumnIndex));

                if (auctionId == auctionIdToMatch) {

                    // TODO: Zavrsi konstruktor
                    bids.add(new Bid(currentId, i, dateTime, null, findUser(userId, activity)));
                }
            }
        } finally {
            cursor.close();
        }
        return bids;
    }
    public Auction findAuction(int auctionId, Activity activity) {
        Auction auction = null;
        // Definisi projekciju pomocu koje ces da ucitas podatke iz tabele item
        String[] projection = {
                AuctionContract.AuctionEntry._ID,
                AuctionContract.AuctionEntry.COLUMN_ITEM_ID,
                AuctionContract.AuctionEntry.COLUMN_START_PRICE,
                AuctionContract.AuctionEntry.COLUMN_USER_ID,
                AuctionContract.AuctionEntry.COLUMN_START_DATE,
                AuctionContract.AuctionEntry.COLUMN_END_DATE,
        };

        // Sort order
        String sortOrder = AuctionContract.AuctionEntry._ID + " DESC";
        Cursor cursor = activity.getContentResolver().query(AuctionContract.AuctionEntry.CONTENT_URI, projection, null, null, null);

        // Uzmi relevatne podatke za svaku aukciju
        try {
            // Prvo nadji index svake kolone
            int idColumnIndex = cursor.getColumnIndex(AuctionContract.AuctionEntry._ID);
            int itemIdColumnIndex = cursor.getColumnIndex(AuctionContract.AuctionEntry.COLUMN_ITEM_ID);
            int startPriceColumnIndex = cursor.getColumnIndex(AuctionContract.AuctionEntry.COLUMN_START_PRICE);
            int userIdColumnIndex = cursor.getColumnIndex(AuctionContract.AuctionEntry.COLUMN_USER_ID);
            int startDateColumnIndex = cursor.getColumnIndex(AuctionContract.AuctionEntry.COLUMN_START_DATE);
            int endDateColumnIndex = cursor.getColumnIndex(AuctionContract.AuctionEntry.COLUMN_END_DATE);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                int itemId = cursor.getInt(itemIdColumnIndex);
                double startPrice = cursor.getDouble(startPriceColumnIndex);
                int i = (int) startPrice;
                int userId = cursor.getInt(userIdColumnIndex);
                String startDate = cursor.getString(startDateColumnIndex);
                String endDate = cursor.getString(endDateColumnIndex);

                if (currentID == auctionId) {
                    User user = findUser(userId, activity);
                    Item item = findItem(itemId, activity);
                    ArrayList<Bid> bidsAll = loadBidsFromDatabase(activity);
                    ArrayList<Bid> bidsFiltered = new ArrayList<>();
                    for (Bid bid : bidsAll) {
                        if (bid.getAuction().getId() == auctionId) {
                            bidsFiltered.add(bid);
                        }
                    }
                    auction = new Auction(i, startDate, endDate, user, item, bidsFiltered, auctionId);
                    break;
                }
            }
        } finally {
            cursor.close();
        }
        return auction;
    }
    public ArrayList<Bid> loadBidsFromDatabase(Activity activity) {
        // Log.i("Activity name", getActivity().toString());
        String[] projection = {
                BidContract.BidEntry._ID,
                BidContract.BidEntry.COLUMN_AUCTION_ID,
                BidContract.BidEntry.COLUMN_PRICE,
                BidContract.BidEntry.COLUMN_DATE_TIME,
                BidContract.BidEntry.COLUMN_USER_ID
        };

        // Sort order
        String sortOrder = BidContract.BidEntry._ID + " DESC";

        ContentResolver contentResolver = activity.getContentResolver();
        Log.i("ActivityName", activity.toString());
        Cursor cursor = contentResolver.query(BidContract.BidEntry.CONTENT_URI, projection, null, null, null);

        ArrayList<Bid> bids = new ArrayList<>();
        // Uzmi relevantne podatke za svaku aukciju
        try {
            // Prvo nadji index svake kolone
            int idColumnIndex = cursor.getColumnIndex(BidContract.BidEntry._ID);
            int auctionIdColumnIndex = cursor.getColumnIndex(BidContract.BidEntry.COLUMN_AUCTION_ID);
            int priceColumnIndex = cursor.getColumnIndex(BidContract.BidEntry.COLUMN_PRICE);
            int dateTimeColumnIndex = cursor.getColumnIndex(BidContract.BidEntry.COLUMN_DATE_TIME);
            int userIdColumnIndex = cursor.getColumnIndex(BidContract.BidEntry.COLUMN_USER_ID);

            while (cursor.moveToNext()) {
                int currentId = cursor.getInt(idColumnIndex);
                int auctionId = cursor.getInt(auctionIdColumnIndex);
                double price = cursor.getDouble(priceColumnIndex);
                int i = (int) price;
                String dateTime = cursor.getString(dateTimeColumnIndex);
                int userId = Integer.parseInt(cursor.getString(userIdColumnIndex));


                User user = findUser(userId, activity);
                Auction auction = findAuction(auctionId, activity);
                bids.add(new Bid(currentId, i, dateTime, auction, user));
            }

        } finally {
            cursor.close();
        }
        return bids;
    }
}
