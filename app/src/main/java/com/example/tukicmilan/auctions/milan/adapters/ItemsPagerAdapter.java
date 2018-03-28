package com.example.tukicmilan.auctions.milan.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tukicmilan.auctions.milan.activities.ItemActivity;
import com.example.tukicmilan.auctions.milan.model.Auction;
import com.example.tukicmilan.auctions.milan.model.Item;


/**
 * Created by asski on 24-May-17.
 */

public class ItemsPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"Details", "Auctions"};
    private Context context;

    public ItemsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                //bilo je ono iz itemActiviti, nesto tabOfAuctions i to je tamo zakomentarisano
                return ItemActivity.ItemTabOfAuctions.newInstance(position);
            default:
                return ItemActivity.ItemDetailsFragment.newInstance(position);
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
