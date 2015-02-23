package com.heapstack.groupbowl;

/**
 * Created by Jong on 1/7/15.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.content.Context;

import java.util.Locale;

/**
 * A {@link android.support.v13.app.FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    protected Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {

        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0:
                return new AnnouncementFragment();
            case 1:
                return new EventFragment();
            case 2:
                return new MemberFragment();
            case 3:
                return new SettingFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return "announcement".toUpperCase(l);
            case 1:
                return "event".toUpperCase(l);
            case 2:
                return "member".toUpperCase(l);
            case 3:
                return "setting".toUpperCase(l);
        }
        return null;
    }



    public int getIcon(int position) {

        switch (position) {
            case 0:
                return R.drawable.ic_news;
            case 1:
                return R.drawable.ic_calendar;
            case 2:
                return R.drawable.ic_member;
            case 3:
                return R.drawable.ic_setting;
        }

        return R.drawable.ic_news;
    }
}

