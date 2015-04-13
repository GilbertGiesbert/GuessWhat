package com.joern.guesswhat.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.joern.guesswhat.constants.PreferenceType;


/**
 * Created by joern on 13.04.2015.
 */
public class SharedPreferencesHelper {

    private static final String LOG_TAG = SharedPreferencesHelper.class.getSimpleName();

    public static String getPreference(Context context, PreferenceType prefType){
        Log.d(LOG_TAG, "getPreference()");
        Log.d(LOG_TAG, "prefType="+prefType);

        SharedPreferences preferences = context.getSharedPreferences(PreferenceType.APP_PREFERENCES+"", Context.MODE_PRIVATE);

        String foundPref = preferences.getString(prefType+"", null);
        Log.d(LOG_TAG, "foundPref="+foundPref);

        return foundPref;
    }

    public static void setPreference(Context context, PreferenceType prefType, String prefValue){
        Log.d(LOG_TAG, "setPreference()");
        Log.d(LOG_TAG, "prefType="+prefType);
        Log.d(LOG_TAG, "prefValue="+prefValue);

        SharedPreferences preferences = context.getSharedPreferences(PreferenceType.APP_PREFERENCES+"", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(prefType+"", prefValue);
        editor.apply();
    }
}