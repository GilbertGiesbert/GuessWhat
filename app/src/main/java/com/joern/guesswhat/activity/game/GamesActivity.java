package com.joern.guesswhat.activity.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.joern.guesswhat.R;
import com.joern.guesswhat.activity.navigation.NavigationDrawerActivity;

/**
 * Created by Geheim on 24.04.2015.
 */
public class GamesActivity extends NavigationDrawerActivity {

    private static final String LOG_TAG = GamesActivity.class.getSimpleName();


    private GamesTabAdapter tabAdapter;
    private ViewPager vp_games;

    @Override
    protected int getMainContentLayoutId() {
        return R.layout.games_activity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);


        tabAdapter = new GamesTabAdapter(getSupportFragmentManager(), this);

        vp_games = (ViewPager) findViewById(R.id.vp_games);
        vp_games.setAdapter(tabAdapter);
        vp_games.setCurrentItem(1);

    }

    @Override
    public void onResume(){
        Log.d(LOG_TAG, "onResume()");
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(LOG_TAG, "onCreateOptionsMenu()");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.games_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.mi_refresh) {
            Toast.makeText(this, "refresh not implemented", Toast.LENGTH_SHORT).show();
            return true;
        }else{
            if (id == R.id.mi_createGame) {
                startActivity(new Intent(this, CreateGameActivity.class));
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private GamesTab getCurrentTab(){
        int position = vp_games.getCurrentItem();
        return tabAdapter.getTab(position);
    }
}