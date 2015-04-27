package com.joern.guesswhat.activity.game;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.joern.guesswhat.R;
import com.joern.guesswhat.activity.navigation.NavigationDrawerActivity;

/**
 * Created by Geheim on 24.04.2015.
 */
public class GamesActivity extends NavigationDrawerActivity implements View.OnClickListener{

    private enum State{
        GAMES_TO_PLAY, GAMES_CREATED
    }

    private State state;

    private static final String LOG_TAG = GamesActivity.class.getSimpleName();

    private MenuItem mi_refresh, mi_createGame;
    private TextView tv_hintEmptyList;
    View v_underscorePlay, v_underscoreCreate;

    @Override
    protected int getMainContentLayoutId() {
        return R.layout.games_activity;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        state = State.GAMES_TO_PLAY;

        Button bt_play = (Button) findViewById(R.id.bt_play);
        Button bt_create = (Button) findViewById(R.id.bt_create);

        bt_play.setOnClickListener(this);
        bt_create.setOnClickListener(this);

        v_underscorePlay = findViewById(R.id.v_underscorePlay);
        v_underscoreCreate = findViewById(R.id.v_underscoreCreate);

        tv_hintEmptyList = (TextView) findViewById(R.id.tv_hintEmptyList);

        refreshButtonUnderscore();
        refreshEmptyListHint();
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

        mi_refresh = menu.findItem(R.id.mi_refresh);
        mi_createGame = menu.findItem(R.id.mi_createGame);

        refreshActionBarIcons();
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

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.bt_play:
                state = State.GAMES_TO_PLAY;
                refreshActionBarIcons();
                refreshButtonUnderscore();
                refreshEmptyListHint();
                break;

            case R.id.bt_create:
                state = State.GAMES_CREATED;
                refreshActionBarIcons();
                refreshButtonUnderscore();
                refreshEmptyListHint();
                break;
        }
    }

    private void refreshActionBarIcons(){

        if(mi_refresh != null && mi_createGame != null){
            if(State.GAMES_TO_PLAY.equals(state)){
                mi_refresh.setVisible(true);
                mi_createGame.setVisible(false);
            }else{
                mi_refresh.setVisible(false);
                mi_createGame.setVisible(true);
            }
        }
    }

    private void refreshButtonUnderscore(){
        if(State.GAMES_TO_PLAY.equals(state)){
            v_underscorePlay.setVisibility(View.VISIBLE);
            v_underscoreCreate.setVisibility(View.GONE);
        }else{
            v_underscorePlay.setVisibility(View.GONE);
            v_underscoreCreate.setVisibility(View.VISIBLE);
        }
    }

    private void refreshEmptyListHint(){

        if(State.GAMES_TO_PLAY.equals(state)){
            tv_hintEmptyList.setText(getResources().getString(R.string.games_hintNoGamesToPlay));
        }else{
            tv_hintEmptyList.setText(getResources().getString(R.string.games_hintNoGamesCreated));
        }
    }
}