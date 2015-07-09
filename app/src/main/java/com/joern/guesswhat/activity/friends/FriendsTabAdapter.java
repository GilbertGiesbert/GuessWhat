package com.joern.guesswhat.activity.friends;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

/**
 * Created by joern on 07.07.2015.
 */
public class FriendsTabAdapter extends FragmentPagerAdapter {

    private Context context;
    private SparseArray<FriendsTab> tabMap;

    public FriendsTabAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        this.tabMap = new SparseArray<>();
    }

    @Override
    public Fragment getItem(int position) {

        FriendsTab tab = new FriendsTab();
        tab.init(FriendsTabType.valueOf(position));
        return tab;
    }

    @Override
    public int getCount() {
        return FriendsTabType.values().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        FriendsTabType type = FriendsTabType.valueOf(position);
        if(type != null){
            return type.getName(context);
        }else{
            return null;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        FriendsTab tab = (FriendsTab) super.instantiateItem(container, position);
        tabMap.put(position, tab);
        return tab;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        tabMap.remove(position);
        super.destroyItem(container, position, object);
    }

    public FriendsTab getTab(int position){
        return tabMap.get(position);
    }
}