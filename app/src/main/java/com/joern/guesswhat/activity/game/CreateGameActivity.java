package com.joern.guesswhat.activity.game;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.joern.guesswhat.R;
import com.joern.guesswhat.activity.navigation.NavigationDrawerActivity;

/**
 * Created by joern on 27.04.2015.
 */
public class CreateGameActivity extends NavigationDrawerActivity implements View.OnLayoutChangeListener {

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


        ImageView v = (ImageView) findViewById(R.id.iv_gamePicture);
        v.addOnLayoutChangeListener(this);
    }



    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        Log.d(LOG_TAG, "onLayoutChange()");
        Log.d(LOG_TAG, "v:"+getResources().getResourceEntryName(v.getId()));

        if(v.getId() == R.id.iv_gamePicture){

            // values are measured in pixels???
            int w = v.getWidth();
            int h = v.getHeight();

            Toast.makeText(this, "w:" + w + ",h:" + h, Toast.LENGTH_SHORT).show();
        }


    }
}
