package com.joern.guesswhat.activity.login;

import android.util.Log;

import com.joern.guesswhat.model.User;

/**
 * Created by joern on 24.04.2015.
 */
public class PasswordFactory {

    private static final String LOG_TAG = PasswordFactory.class.getSimpleName();

    public static final String SALT = "Abrakadabra, Simsalabim und dreimal schwarzer Kater!";

    public static Integer buildPasswordHash(String password, User user){
        return buildPasswordHash(password, user.getName(), user.getEmail());
    }

    public static Integer buildPasswordHash(String password, String userName, String userEmail){
        Log.d(LOG_TAG, "buildPasswordHash()");

        if(userName == null || userEmail == null ||password == null){
            Log.d(LOG_TAG, "failed to build password hash, check provided null arguments");
            return null;
        }

        password += getSalt(userName, userEmail);

        return password.hashCode();
    }

    private static String getSalt(String userName, String userEmail) {

        String dynamicSalt = userName+"_"+userEmail;
        dynamicSalt = new StringBuilder(dynamicSalt).reverse().toString();

        return SALT+dynamicSalt;
    }
}