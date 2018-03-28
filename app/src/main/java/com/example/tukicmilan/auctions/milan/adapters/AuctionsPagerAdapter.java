package com.example.tukicmilan.auctions.milan.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tukicmilan.auctions.milan.activities.AuctionActivity;


/**
 * Created by asski on 24-May-17.
 */

public class AuctionsPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"Details", "Bids"};
    private Context context;

    public AuctionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return AuctionActivity.AuctionBidsFragment.newInstance(position);
            default:
                return AuctionActivity.AuctionDetailsFragment.newInstance(position);
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
