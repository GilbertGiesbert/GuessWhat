package com.joern.guesswhat.activity.friends;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.joern.guesswhat.R;

/**
 * Created by joern on 14.04.2015.
 */
public class PendingFriendsActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String LOG_TAG = PendingFriendsActivity.class.getSimpleName();

    private Button bt_received, bt_sent;
    private View v_underscoreReceived, v_underscoreSent;

    String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
            "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
            "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
            "Android", "iPhone", "WindowsMobile" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pendingfriends_activity);

        ListView l1 = (ListView) findViewById(R.id.lv_pendingRequsts);

        ArrayAdapter<String> a1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);

        l1.setAdapter(a1);

        bt_received = (Button) findViewById(R.id.bt_received);
        bt_sent = (Button) findViewById(R.id.bt_sent);

        bt_received.setOnClickListener(this);
        bt_sent.setOnClickListener(this);

        v_underscoreReceived = (View) findViewById(R.id.v_underscoreReceived);
        v_underscoreSent = (View) findViewById(R.id.v_underscoreSent);
        v_underscoreSent.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.bt_received:
            case R.id.bt_sent:

                toggle();

                break;
        }

    }

    private void toggle(){

        if(v_underscoreSent.getVisibility() == View.GONE){

            v_underscoreSent.setVisibility(View.VISIBLE);
            v_underscoreReceived.setVisibility(View.GONE);

        }else{

            v_underscoreSent.setVisibility(View.GONE);
            v_underscoreReceived.setVisibility(View.VISIBLE);

        }
    }
}