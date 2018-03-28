package com.example.tukicmilan.auctions.milan.activities;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tukicmilan.auctions.milan.R;
import com.example.tukicmilan.auctions.milan.adapters.AuctionsAdapter;
import com.example.tukicmilan.auctions.milan.adapters.AuctionsInListViewAdapter;
import com.example.tukicmilan.auctions.milan.adapters.ItemsAdapter;
import com.example.tukicmilan.auctions.milan.adapters.ItemsPagerAdapter;
import com.example.tukicmilan.auctions.milan.fragments.AuctionsFragment;
import com.example.tukicmilan.auctions.milan.model.Auction;
import com.example.tukicmilan.auctions.milan.model.Item;
import com.squareup.picasso.Picasso;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class ItemActivity extends AppCompatActivity{
    ViewPager mViewPager;
    FragmentPagerAdapter mFragmentPagerAdapter;
    Item item;
    private Context context;
    private EditText editText;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarItem);
        toolbar.setTitle("Item");

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //item = (Item) getIntent().getSerializableExtra("selectedItem");

        // Get the ViewPager and setits PagerAdapter so that it can display items
        ViewPager mViewPager = (ViewPager) findViewById(R.id.items_view_pager);
        mViewPager.setAdapter(new ItemsPagerAdapter(getSupportFragmentManager(), ItemActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.items_sliding_tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static class ItemDetailsFragment extends android.support.v4.app.Fragment {
        Context ctx;
        public static final String ARG_PAGE = "ARG_PAGE";
        private int mPage;

        public static ItemDetailsFragment newInstance(int page) {
            Bundle args = new Bundle();
            args.putInt(ARG_PAGE, page);
            ItemDetailsFragment fragment = new ItemDetailsFragment();
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
            View rootView = inflater.inflate(R.layout.item_fragment_tab_one, container, false);
            Item item = (Item) getActivity().getIntent().getSerializableExtra("selectedItem");

            TextView textViewId = (TextView) rootView.findViewById(R.id.item_details_id);
            textViewId.setText(new StringBuilder().append(item.getId()).toString());

            TextView textViewName = (TextView) rootView.findViewById(R.id.item_details_name);
            textViewName.setText(item.getName());

            TextView textViewDescription = (TextView) rootView.findViewById(R.id.item_details_description);
            textViewDescription.setText(item.getDescription());

            CheckBox isSold = (CheckBox) rootView.findViewById(R.id.item_details_sold);
            isSold.setChecked(item.isSold());

            // TODO: Add a normal picture
            ImageView picture = (ImageView) rootView.findViewById(R.id.item_details_image);
            // Picasso.with(getContext()).load(item.getPicture()).into(picture);
            Picasso.with(ctx.getApplicationContext()).load(item.getPicture()).error(R.drawable.esplash).resize(600, 600).into(picture);
            //picture.setImageResource(Integer.parseInt(String.valueOf(item.getPicture())));

            return rootView;
        }
    }
    public static class ItemTabOfAuctions extends android.support.v4.app.Fragment  {
        public static final String ARG_PAGE = "ARG_PAGE";
        private int mPage;

        public static ItemTabOfAuctions newInstance(int page) {
            Bundle args = new Bundle();
            args.putInt(ARG_PAGE, page);
            ItemTabOfAuctions fragment = new ItemTabOfAuctions();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
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
            View view = inflater.inflate(R.layout.item_fragment_tab_two, container, false);
            final Item item = (Item) getActivity().getIntent().getSerializableExtra("selectedItem");
            List<Auction> auctions = item.getAuctions();
                if (auctions == null) {
                    auctions = new ArrayList<>();
            }

            final ListView auctionsTabTwo = (ListView) view.findViewById(R.id.items_list_view_auctions);
            auctionsTabTwo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Auction auction = item.getAuctions().get(position);
                    int itemPosition = position;
                    Object selection = (Object) parent.getItemAtPosition(position);
                    // Toast.makeText(getActivity(), "Position :"+itemPosition+"  ListItem : " +selection , Toast.LENGTH_LONG).show();
                    Log.i("ItemActivity", "Position" + selection);
                    Intent intent = new Intent(getActivity(), AuctionActivity.class);
                    intent.putExtra("selectedAuction", auction);
                    startActivity(intent);
                }
            });

            //change on RecyclerView
            auctionsTabTwo.setEmptyView(view.findViewById(R.id.item_bids_empty_view));
            auctionsTabTwo.setAdapter(new AuctionsInListViewAdapter(this.getActivity(), auctions));

            return view;
        }
    }
}
