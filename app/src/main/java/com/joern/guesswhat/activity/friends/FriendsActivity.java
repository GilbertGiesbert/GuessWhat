package com.joern.guesswhat.activity.friends;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.joern.guesswhat.R;

/**
 * Created by joern on 14.04.2015.
 */
public class FriendsActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String LOG_TAG = FriendsActivity.class.getSimpleName();

    private Button bt_addFriend, bt_pendingFriendRequests;
    private ImageButton ib_pendingFriendRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.friends_activity);

        bt_addFriend = (Button) findViewById(R.id.bt_addFriend);
        bt_pendingFriendRequests = (Button) findViewById(R.id.bt_pendingFriendRequests);
        ib_pendingFriendRequests = (ImageButton) findViewById(R.id.ib_pendingFriendRequests);

        bt_addFriend.setOnClickListener(this);
        bt_pendingFriendRequests.setOnClickListener(this);
        ib_pendingFriendRequests.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Log.d(LOG_TAG, "onCreate()");

        Log.d(LOG_TAG, "v="+getResources().getResourceEntryName(v.getId()));

        switch (v.getId()){

            case R.id.bt_addFriend:

                break;

            case R.id.bt_pendingFriendRequests:

                ib_pendingFriendRequests.setVisibility(View.VISIBLE);

                break;

            case R.id.ib_pendingFriendRequests:

                ib_pendingFriendRequests.setVisibility(View.GONE);

                break;
        }





    }
}