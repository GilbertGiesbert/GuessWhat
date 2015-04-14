package com.joern.guesswhat.common;

import android.content.Context;

import com.joern.guesswhat.constants.PreferenceType;
import com.joern.guesswhat.database.UserDao;
import com.joern.guesswhat.database.UserDaoImpl;
import com.joern.guesswhat.model.User;

/**
 * Created by joern on 13.04.2015.
 */
public class SessionHelper {

    public static User getSessionUser(Context context){

        String sessionUserMail = SharedPreferencesHelper.getPreference(context, PreferenceType.PREF_SESSION_USER_MAIL);
        UserDao userDao = new UserDaoImpl(context);
        return userDao.readUser(sessionUserMail);
    }

    public static void startSession(Context context, User user){
        SharedPreferencesHelper.setPreference(context, PreferenceType.PREF_SESSION_USER_MAIL, user.getEmail());
    }

    public static void stopSession(Context context){
        SharedPreferencesHelper.setPreference(context, PreferenceType.PREF_SESSION_USER_MAIL, "");
    }
}