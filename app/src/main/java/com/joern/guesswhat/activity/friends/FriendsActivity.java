package com.joern.guesswhat.activity.friends;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.joern.guesswhat.R;
import com.joern.guesswhat.activity.navigation.NavigationDrawerActivity;
import com.joern.guesswhat.common.SessionHelper;
import com.joern.guesswhat.persistence.database.FriendshipDao;
import com.joern.guesswhat.persistence.database.FriendshipDaoImpl;
import com.joern.guesswhat.model.Friendship;
import com.joern.guesswhat.model.FriendshipRequestType;
import com.joern.guesswhat.model.FriendshipState;
import com.joern.guesswhat.model.User;

import java.util.List;

/**
 * Created by joern on 14.04.2015.
 */
public class FriendsActivity extends NavigationDrawerActivity implements View.OnClickListener {

    private static final String LOG_TAG = FriendsActivity.class.getSimpleName();

    private FriendlistAdapter listAdapter;

    private TextView tv_emptyListHint;

    @Override
    protected int getMainContentLayoutId() {
        return R.layout.friends_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        Button bt_addFriend = (Button) findViewById(R.id.bt_addFriend);
        Button bt_pendingFriendRequests = (Button) findViewById(R.id.bt_pendingFriendRequests);

        bt_addFriend.setOnClickListener(this);
        bt_pendingFriendRequests.setOnClickListener(this);

        listAdapter = new FriendlistAdapter(this);
        ListView list = (ListView) findViewById(R.id.lv_friendList);
        list.setAdapter(listAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String friend = listAdapter.getItem(position).getName();

                Toast.makeText(FriendsActivity.this, friend, Toast.LENGTH_SHORT).show();
            }
        });

        tv_emptyListHint = (TextView) findViewById(R.id.tv_emptyListHint);
    }

    @Override
    public void onResume() {
        super.onResume();

        listAdapter.reload();
        refreshNewRequestsHint();
        refreshEmptyListHint();
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
            case R.id.ib_newRequestsHint:

                startActivity(new Intent(this, PendingFriendsActivity.class));

                break;
        }
    }

    private void refreshNewRequestsHint() {

        ImageButton ib_newRequestsHint = (ImageButton) findViewById(R.id.ib_newRequestsHint);
        ib_newRequestsHint.setOnClickListener(this);

        User sessionUser = SessionHelper.getSessionUser(this);
        FriendshipDao dao = new FriendshipDaoImpl(this);
        List<Friendship> fList = dao.getFriendships(sessionUser, FriendshipRequestType.RECEIVED, FriendshipState.REQUEST_SEND);

        if(fList.isEmpty()){
            ib_newRequestsHint.setVisibility(View.GONE);
        }
    }

    private void refreshEmptyListHint(){
        if(listAdapter.getCount() > 0){
            tv_emptyListHint.setVisibility(View.GONE);
        }else{
            tv_emptyListHint.setVisibility(View.VISIBLE);
        }
    }
}