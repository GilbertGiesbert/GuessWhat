package com.joern.guesswhat.activity.friends;

import android.content.Context;

import com.joern.guesswhat.R;

/**
 * Created by joern on 08.07.2015.
 */
public enum FriendsTabType {

    INVITES(0, R.string.friends_tab_invites_title, R.string.friends_tab_invites_subtitle),
    FRIENDS(1, R.string.friends_tab_friends_title, R.string.friends_tab_friends_subtitle),
    REQUESTS(2, R.string.friends_tab_requests_title, R.string.friends_tab_requests_subtitle);

    private int tabPosition;
    private int titleResource;
    private int subtitleResource;

    private FriendsTabType(int tabPosition, int titleResource, int subtitleResource) {
        this.tabPosition = tabPosition;
        this.titleResource = titleResource;
        this.subtitleResource = subtitleResource;
    }

    public int getTabPosition(){
        return tabPosition;
    }

    public String getTitle(Context context){
        return context.getResources().getString(titleResource);
    }

    public String getSubtitle(Context context){
        return context.getResources().getString(subtitleResource);
    }

    public static FriendsTabType valueOf(int position){

        for(FriendsTabType type: FriendsTabType.values()){
            if(type.getTabPosition() == position){
                return type;
            }
        }
        return null;
    }
}