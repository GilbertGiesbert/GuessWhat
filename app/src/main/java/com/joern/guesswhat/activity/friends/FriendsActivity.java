package com.joern.guesswhat.activity.friends;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.joern.guesswhat.R;
import com.joern.guesswhat.common.SessionHelper;
import com.joern.guesswhat.database.FriendshipDao;
import com.joern.guesswhat.database.FriendshipDaoImpl;
import com.joern.guesswhat.model.Friendship;
import com.joern.guesswhat.model.User;

import java.util.List;

/**
 * Created by joern on 14.04.2015.
 */
public class FriendsActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String LOG_TAG = FriendsActivity.class.getSimpleName();

    private Button bt_addFriend, bt_pendingFriendRequests;
    private ImageButton ib_pendingFriendRequestsHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.friends_activity);

        bt_addFriend = (Button) findViewById(R.id.bt_addFriend);
        bt_pendingFriendRequests = (Button) findViewById(R.id.bt_pendingFriendRequests);
        ib_pendingFriendRequestsHint = (ImageButton) findViewById(R.id.ib_pendingFriendRequestsHint);

        bt_addFriend.setOnClickListener(this);
        bt_pendingFriendRequests.setOnClickListener(this);
        ib_pendingFriendRequestsHint.setOnClickListener(this);

        setPendingHintVisibility();

    }

    @Override
    public void onClick(View v) {
        Log.d(LOG_TAG, "onCreate()");

        Log.d(LOG_TAG, "v="+getResources().getResourceEntryName(v.getId()));

        switch (v.getId()){

            case R.id.bt_addFriend:

                DialogFragment newFragment = new AddFriendDialog();
                newFragment.show(getFragmentManager(), AddFriendDialog.class.getSimpleName());

                break;

            case R.id.bt_pendingFriendRequests:
            case R.id.ib_pendingFriendRequestsHint:

                startActivity(new Intent(this, PendingFriendsActivity.class));

                break;
        }
    }

    private void setPendingHintVisibility() {

        User sessionUser = SessionHelper.getSessionUser(this);
        FriendshipDao dao = new FriendshipDaoImpl(this);
        List<Friendship> fList = dao.getAllFriendshipsRequestedByOthers(sessionUser);

        if(fList.isEmpty()){
            ib_pendingFriendRequestsHint.setVisibility(View.GONE);
        }
    }
}