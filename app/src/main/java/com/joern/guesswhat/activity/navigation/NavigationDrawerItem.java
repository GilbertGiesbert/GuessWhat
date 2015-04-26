package com.joern.guesswhat.activity.navigation;

import android.app.Activity;
import android.util.Log;

import com.joern.guesswhat.R;
import com.joern.guesswhat.activity.about.AboutActivity;
import com.joern.guesswhat.activity.friends.FriendsActivity;
import com.joern.guesswhat.activity.login.LoginActivity;
import com.joern.guesswhat.activity.play.PlayActivity;
import com.joern.guesswhat.activity.settings.SettingsActivity;

public enum NavigationDrawerItem {

    PLAY        (PlayActivity.class,        R.mipmap.gamepad,       R.string.navigation_item_play),
    FRIENDS     (FriendsActivity.class,     R.mipmap.friends,       R.string.navigation_item_friends),
    SETTINGS    (SettingsActivity.class,    R.mipmap.gear,          R.string.navigation_item_settings),
    ABOUT       (AboutActivity.class,       R.mipmap.info,          R.string.navigation_item_about),
    LOGOUT      (LoginActivity.class,       R.mipmap.logout_left,   R.string.navigation_item_logout);

    private static final String LOG_TAG = NavigationDrawerItem.class.getSimpleName();

    private Class targetActivityClass;
    private int iconId;
    private int labelId;


    private NavigationDrawerItem(Class<? extends Activity> targetActivityClass, int iconId, int labelId){
        this.targetActivityClass = targetActivityClass;
        this.iconId = iconId;
        this.labelId = labelId;
    }

    public Class getTargetActivityClass(){
        return targetActivityClass;
    }

    public int getIconId(){
        return iconId;
    }

    public int getLabelId(){
        return labelId;
    }

    public static NavigationDrawerItem valueOf(Class<? extends Activity> activityClass){
        Log.d(LOG_TAG, "valueOf()");

        for(NavigationDrawerItem item: NavigationDrawerItem.values()){
            if(item.getTargetActivityClass() == activityClass){
                return item;
            }
        }
        return null;
    }
}