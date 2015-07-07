package com.joern.guesswhat.activity.friends;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by joern on 07.07.2015.
 */
public class FriendsPagerAdapter extends FragmentPagerAdapter {

    private static final String[] TAB_TITLES = new String[]{"1", "2", "3"};

    public FriendsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {


            case 0:
                FriendsTab t1 = new FriendsTab();
                t1.setTest("eins");
                return t1;


            case 1:
                FriendsTab t2 = new FriendsTab();
                t2.setTest("zwo");
                return t2;


            case 2:
                FriendsTab t3 = new FriendsTab();
                t3.setTest("drei");
                return t3;
        }
        return null;
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }
}