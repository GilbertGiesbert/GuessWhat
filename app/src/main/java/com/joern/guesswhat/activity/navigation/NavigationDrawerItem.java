package com.joern.guesswhat.activity.navigation;

import android.app.Activity;
import android.util.Log;

import com.joern.guesswhat.R;
import com.joern.guesswhat.activity.friends.FriendsActivity;
import com.joern.guesswhat.activity.main.MainActivity;

public enum NavigationDrawerItem {

    HOME            (R.mipmap.home,         R.string.navigation_item_home,   MainActivity.class),
    GAMES           (R.mipmap.puzzle,       R.string.navigation_item_games,   MainActivity.class),
    FRIENDS         (R.mipmap.friends,      R.string.navigation_item_friends,   FriendsActivity.class),
    SETTINGS        (R.mipmap.gear,         R.string.navigation_item_settings,        MainActivity.class),
    HELP_FEEDBACK   (R.drawable.question,   R.string.navigation_item_helpFeedback,  MainActivity.class),
    ABOUT           (R.mipmap.info,         R.string.navigation_item_about,             MainActivity.class);

    private static final String TAG = NavigationDrawerItem.class.getSimpleName();

    private int iconId;
    private int labelId;
    private Class targetActivityClass;

    private NavigationDrawerItem(int iconId, int labelId, Class<? extends Activity> targetActivityClass){
        this.iconId = iconId;
        this.labelId = labelId;
        this.targetActivityClass = targetActivityClass;
    }

    public int getIconId(){
        return iconId;
    }

    public int getLabelId(){
        return labelId;
    }

    public Class getTargetActivityClass(){
        return targetActivityClass;
    }

    public int getPosition(){
        return ordinal();
    }

    public static NavigationDrawerItem valueOf(Class<? extends Activity> activityClass){
        Log.d(TAG, "valueOf()");

        for(NavigationDrawerItem item: NavigationDrawerItem.values()){
            if(item.getTargetActivityClass() == activityClass){
                return item;
            }
        }
        return null;
    }
}