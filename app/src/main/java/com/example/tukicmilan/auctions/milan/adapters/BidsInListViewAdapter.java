package com.example.tukicmilan.auctions.milan.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tukicmilan.auctions.milan.R;
import com.example.tukicmilan.auctions.milan.model.Bid;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by asski on 11-Sep-17.
 */

public class BidsInListViewAdapter extends ArrayAdapter<Bid> {
    private Context context;

    public BidsInListViewAdapter(@NonNull Activity context, List<Bid> bids) {
        super(context, 0, bids);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // find root view
        View listBidsView = convertView;

        // if it's null inflate it
        if (listBidsView == null) {
            listBidsView = LayoutInflater.from(getContext()).inflate(R.layout.list_bid, parent, false);
        }

        // find the current Bid object
        Bid bid = getItem(position);

        TextView textViewUserName = (TextView) listBidsView.findViewById(R.id.list_bid_user_name);
        textViewUserName.setText(bid.getUser().getName());

        TextView textViewUsersPrice = (TextView) listBidsView.findViewById(R.id.list_bid_user_price);
        textViewUsersPrice.setText(String.format("%s", bid.getPrice()));

        TextView textViewUsersDate = (TextView) listBidsView.findViewById(R.id.list_bid_user_date);
        textViewUsersDate.setText(String.format("%s", bid.getDateTime()));

        ImageView imageView = (ImageView) listBidsView.findViewById(R.id.list_item_image);
        Picasso.with(context.getApplicationContext()).load(bid.getUser().getPicture()).error(R.drawable.esplash).into(imageView);


        return listBidsView;
    }

}
