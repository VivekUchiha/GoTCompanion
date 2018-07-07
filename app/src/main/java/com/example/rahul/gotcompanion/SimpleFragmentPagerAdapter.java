package com.example.rahul.gotcompanion;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.rahul.gotcompanion.Fragments.AllFragment;
import com.example.rahul.gotcompanion.Fragments.FavouriteFragment;
import com.example.rahul.gotcompanion.Fragments.HistoryFragment;

/**
 * Provides the appropriate {@link Fragment} for a view pager.
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    public SimpleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new AllFragment();
        } else if (position == 1) {
            return new HistoryFragment();
        } else  {
            return new FavouriteFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "All";
        } else if (position == 1) {
            return "History";
        } else  {
            return "Favourites";
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
