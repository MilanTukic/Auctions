package com.example.tukicmilan.auctions.milan.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.tukicmilan.auctions.milan.R;
import com.example.tukicmilan.auctions.milan.model.Auction;

import java.util.List;

/**
 * Created by asski on 24-May-17.
 */

public class AuctionsInListViewAdapter extends ArrayAdapter<Auction> {

    public AuctionsInListViewAdapter(@NonNull Activity context, List<Auction> auctions) {
        super(context, 0, auctions);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // find root view
        View listAuctionView = convertView;

        // if it's null, inflate it
        if (listAuctionView == null) {
            listAuctionView = LayoutInflater.from(getContext()).inflate(R.layout.list_auction, parent
                    , false);
        }
        // Find the current Auction object
        Auction auction = getItem(position);

        // TODO: Fill the item with data
        TextView textViewItemName = (TextView) listAuctionView.findViewById(R.id.list_auction_item_name);
        textViewItemName.setText(auction.getItem().getName());

        TextView textViewItemDesctioption = (TextView) listAuctionView.findViewById(R.id.list_auction_item_description);
        textViewItemDesctioption.setText(auction.getItem().getDescription());

        TextView textViewItemPrice = (TextView) listAuctionView.findViewById(R.id.list_auction_item_price);
        textViewItemPrice.setText(String.format("%s", auction.getStartPrice()));

        return listAuctionView;
    }
}
