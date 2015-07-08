package com.joern.guesswhat.activity.friends;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by joern on 07.07.2015.
 */
public class FriendsPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public FriendsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        FriendsTab tab = new FriendsTab();
        tab.init(FriendsTabType.values()[position]);
        return tab;
    }

    @Override
    public int getCount() {
        return FriendsTabType.values().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return FriendsTabType.values()[position].getName(context);
    }
}