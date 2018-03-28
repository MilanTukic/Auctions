package com.example.tukicmilan.auctions.milan.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tukicmilan.auctions.milan.R;
import com.example.tukicmilan.auctions.milan.adapters.AuctionsPagerAdapter;
import com.example.tukicmilan.auctions.milan.adapters.BidsInListViewAdapter;
import com.example.tukicmilan.auctions.milan.model.Auction;
import com.example.tukicmilan.auctions.milan.model.Bid;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AuctionActivity extends AppCompatActivity {
    public static Context contextOfApplication;
    public static Context getContextOfApplication() {
        return contextOfApplication;
    }
    Auction auction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAuction);
        toolbar.setTitle("Auction");

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

       // auction = (Auction) getIntent().getSerializableExtra("selectedAuction");

        contextOfApplication = getApplicationContext();

        // Get the ViewPager and setits PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.auctions_view_pager);
        viewPager.setAdapter(new AuctionsPagerAdapter(getSupportFragmentManager(), AuctionActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.auctions_sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static class AuctionDetailsFragment extends android.support.v4.app.Fragment {
        private Context ctx;
        public static final String ARG_PAGE = "ARG_PAGE";
        private int mPage;

        public static AuctionDetailsFragment newInstance(int page) {
            Bundle args = new Bundle();
            args.putInt(ARG_PAGE, page);
            AuctionDetailsFragment fragment = new AuctionDetailsFragment();
            fragment.setArguments(args);
            return fragment;
        }
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mPage = getArguments().getInt(ARG_PAGE);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            ctx = container.getContext();

            // inflate the layout
            View rootView = inflater.inflate(R.layout.auction_fragment_tab_one, container, false);
            Auction auction = (Auction) getActivity().getIntent().getSerializableExtra("selectedAuction");

            // Podaci o aukciji
            TextView textViewAuctionId = (TextView) rootView.findViewById(R.id.auction_id);
            textViewAuctionId.setText(String.format("%s", auction.getId()));

            TextView textViewAuctionStartPrice = (TextView) rootView.findViewById(R.id.auction_start_price);
            textViewAuctionStartPrice.setText(String.format("%s", auction.getStartPrice()));

            TextView textViewAuctionStartDate = (TextView) rootView.findViewById(R.id.auction_start_date);
            textViewAuctionStartDate.setText(String.format("%s", auction.getStartDate()));

            TextView textViewAuctionEndDate = (TextView) rootView.findViewById(R.id.auction_end_date);
            textViewAuctionEndDate.setText(String.format("%s", auction.getEndDate()));

            // Podaci o korisniku

            TextView userName = (TextView) rootView.findViewById(R.id.user_name);
            userName.setText(auction.getUser().getName());

            TextView userEmail = (TextView) rootView.findViewById(R.id.user_email);
            userEmail.setText(auction.getUser().getEmail());

            TextView userPhone = (TextView) rootView.findViewById(R.id.user_phone);
            userPhone.setText(auction.getUser().getPhone());

            ImageView imageView = (ImageView) rootView.findViewById(R.id.user_small_image);
            Picasso.with(ctx.getApplicationContext()).load(auction.getUser().getPicture()).error(R.drawable.esplash).into(imageView);


            // Podaci o itemu
            TextView itemName = (TextView) rootView.findViewById(R.id.list_bid_user_name);
            itemName.setText(auction.getItem().getName());

            TextView itemDescription = (TextView) rootView.findViewById(R.id.list_item_user_price);
            itemDescription.setText(auction.getItem().getDescription());
            return rootView;
        }
    }

    public static class AuctionBidsFragment extends android.support.v4.app.Fragment {

        public static final String ARG_PAGE = "ARG_PAGE";
        private int mPage;

        public static AuctionBidsFragment newInstance(int page) {
            Bundle args = new Bundle();
            args.putInt(ARG_PAGE, page);
            AuctionBidsFragment fragment = new AuctionBidsFragment();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mPage = getArguments().getInt(ARG_PAGE);

        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            // inflate the layout
            View view = inflater.inflate(R.layout.auction_bids_fragment, container, false);
            Auction auction = (Auction) getActivity().getIntent().getSerializableExtra("selectedAuction");
            List<Bid> bids = auction.getBids();
                if (bids == null) {
                    bids = new ArrayList<>();
            }

            FloatingActionButton button = (FloatingActionButton) view.findViewById(R.id.flBtn);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), AddBidActivity.class);
                    startActivity(intent);
                }
            });

            ListView auctionsBids = (ListView) view.findViewById(R.id.auctions_list_view_bids);
            auctionsBids.setEmptyView(view.findViewById(R.id.auctions_empty_state));
            auctionsBids.setAdapter(new BidsInListViewAdapter(this.getActivity(), bids));
            return view;



        }
    }
}
