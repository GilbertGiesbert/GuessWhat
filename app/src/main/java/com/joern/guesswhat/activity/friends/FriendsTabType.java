package com.joern.guesswhat.activity.friends;

import android.content.Context;

import com.joern.guesswhat.R;

/**
 * Created by joern on 08.07.2015.
 */
public enum FriendsTabType {

    INVITES(0, R.string.friends_tab_invites_title, R.string.friends_tab_invites_subheading),
    FRIENDS(1, R.string.friends_tab_friends_title, R.string.friends_tab_friends_subheading),
    REQUESTS(2, R.string.friends_tab_requests_title, R.string.friends_tab_requests_subheading);

    private int position;
    private int nameResource;
    private int subheadingResource;

    private FriendsTabType(int position, int nameResource, int subheadingResource) {
        this.position = position;
        this.nameResource = nameResource;
        this.subheadingResource = subheadingResource;
    }

    public int getPosition(){
        return position;
    }

    public String getName(Context context){
        return context.getResources().getString(nameResource);
    }

    public String getSubheading(Context context){
        return context.getResources().getString(subheadingResource);
    }

    public static FriendsTabType valueOf(int position){

        for(FriendsTabType type: FriendsTabType.values()){
            if(type.getPosition() == position){
                return type;
            }
        }
        return null;
    }
}