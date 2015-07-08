package com.joern.guesswhat.activity.friends;

import android.content.Context;

import com.joern.guesswhat.R;

/**
 * Created by joern on 08.07.2015.
 */
public enum FriendsTabType {

    INVITES(R.string.friends_tab_invites_title, R.string.friends_tab_invites_subheading),
    FRIENDS(R.string.friends_tab_friends_title, R.string.friends_tab_friends_subheading),
    REQUESTS(R.string.friends_tab_requests_title, R.string.friends_tab_requests_subheading);

    private int nameResource;
    private int subheadingResource;

    private FriendsTabType(int nameResource, int subheadingResource) {
        this.nameResource = nameResource;
        this.subheadingResource = subheadingResource;
    }

    public String getName(Context context){
        return context.getResources().getString(nameResource);
    }

    public String getSubheading(Context context){
        return context.getResources().getString(subheadingResource);
    }
}
