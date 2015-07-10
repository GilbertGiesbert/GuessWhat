package com.joern.guesswhat.activity.games;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

/**
 * Created by joern on 09.07.2015.
 */
public class GamesTabAdapter extends FragmentPagerAdapter {

    private Context context;
    private SparseArray<GamesTab> tabMap;

    public GamesTabAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        this.tabMap = new SparseArray<>();
    }


    @Override
    public Fragment getItem(int position) {

        GamesTab tab = new GamesTab();
        tab.init(GamesTabType.valueOf(position));
        return tab;
    }

    @Override
    public int getCount() {
        return GamesTabType.values().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        GamesTabType type = GamesTabType.valueOf(position);
        if(type != null){
            return type.getTitle(context);
        }else{
            return null;
        }
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        GamesTab tab = (GamesTab) super.instantiateItem(container, position);
        tabMap.put(position, tab);
        return tab;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        tabMap.remove(position);
        super.destroyItem(container, position, object);
    }

    public GamesTab getTab(int position){
        return tabMap.get(position);
    }
}