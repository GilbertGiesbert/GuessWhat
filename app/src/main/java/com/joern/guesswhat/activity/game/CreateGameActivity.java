package com.joern.guesswhat.activity.game;

import android.os.Bundle;
import android.util.Log;

import com.joern.guesswhat.R;
import com.joern.guesswhat.activity.navigation.NavigationDrawerActivity;

/**
 * Created by joern on 27.04.2015.
 */
public class CreateGameActivity extends NavigationDrawerActivity {

    private static final String LOG_TAG = CreateGameActivity.class.getSimpleName();

    @Override
    protected int getMainContentLayoutId() {
        return R.layout.creategame_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        if(sessionUser == null) return;

    }
}
