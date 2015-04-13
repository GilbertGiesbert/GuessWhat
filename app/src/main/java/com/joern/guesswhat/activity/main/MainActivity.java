package com.joern.guesswhat.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.joern.guesswhat.R;
import com.joern.guesswhat.activity.login.LoginActivity;
import com.joern.guesswhat.common.SessionHelper;
import com.joern.guesswhat.database.User;


public class MainActivity extends ActionBarActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User sessionUser = SessionHelper.getSessionUser(this);
        if(sessionUser == null){
            go2Login();

        }else{

            String userInfo =
                    "User info:" + "\n" +
                    "Name: "+sessionUser.getName() + "\n" +
                    "Mail: "+sessionUser.getEmail() + "\n" +
                    "Pswd: "+sessionUser.getPassword();


            TextView tv_userInfo = (TextView) findViewById(R.id.tv_userInfo);
            tv_userInfo.setText(userInfo);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_logout) {

            SessionHelper.stopSession(this);
            go2Login();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void go2Login(){
        Log.d(LOG_TAG, "go2Login()");

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}