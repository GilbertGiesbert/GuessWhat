package com.joern.guesswhat.common;

import android.content.Context;

import com.joern.guesswhat.constants.PreferenceType;
import com.joern.guesswhat.persistence.database.UserDao;
import com.joern.guesswhat.persistence.database.UserDaoImpl;
import com.joern.guesswhat.model.User;

/**
 * Created by joern on 13.04.2015.
 */
public class SessionHelper {

    public static User getSessionUser(Context context){

        String sessionUser = SharedPreferencesHelper.getPreference(context, PreferenceType.PREF_SESSION_USER);
        UserDao userDao = new UserDaoImpl(context);
        return userDao.readUser(sessionUser);
    }

    public static void startSession(Context context, User user){
        SharedPreferencesHelper.setPreference(context, PreferenceType.PREF_SESSION_USER, user.getName());
    }

    public static void stopSession(Context context){
        SharedPreferencesHelper.setPreference(context, PreferenceType.PREF_SESSION_USER, "");
    }
}