package com.example.tukicmilan.auctions.milan.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tukicmilan.auctions.milan.R;
import com.example.tukicmilan.auctions.milan.model.Auction;

import java.util.List;

/**
 * Created by TukicMilan on 2/23/2018.
 */

public class AuctionsAdapter extends RecyclerView.Adapter<AuctionsAdapter.MyViewHolder> {

    public interface OnItemClickListener{
        void onItemClick(Auction auction, int position);
    }

    Context context;
    private List<Auction> listAuctions;
    private OnItemClickListener listener;

    public void setOnClikListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView name, description, price;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.list_auction_item_name);
            description = (TextView) itemView.findViewById(R.id.list_auction_item_description);
            price = (TextView) itemView.findViewById(R.id.list_auction_item_price);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Auction auction = listAuctions.get(position);
            if(listener != null){
                listener.onItemClick(auction, position);
            }
        }
    }

    public AuctionsAdapter(@NonNull Activity context, List<Auction> listAuctions) {
        this.context = context;
        this.listAuctions = listAuctions;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_auction, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Auction auction = listAuctions.get(position);
        holder.name.setText(auction.getItem().getName());
        holder.description.setText(auction.getItem().getDescription());
        holder.price.setText(String.format("%s",  auction.getStartPrice()));
    }

    @Override
    public int getItemCount() {
        return listAuctions.size();
    }

    public void refresh(List<Auction> auctionFiltered){
        this.listAuctions = auctionFiltered;
        notifyDataSetChanged();
    }
}
