package com.joern.guesswhat.activity.friends;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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
public class PendingFriendsActivity extends NavigationDrawerActivity implements View.OnClickListener, PendingFriendsDialog.Listener {

    private static final String LOG_TAG = PendingFriendsActivity.class.getSimpleName();

    private View v_underscoreReceived, v_underscoreSent;
    private TextView tv_hintNoPendingRequests;

    private PendingFriendshipsAdapter listAdapter;

    @Override
    protected int getMainContentLayoutId() {
        return R.layout.pendingfriends_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        if(sessionUser == null) return;

        listAdapter = new PendingFriendshipsAdapter(this, FriendshipRequestType.RECEIVED);

        ListView list = (ListView) findViewById(R.id.lv_pendingRequsts);
        list.setAdapter(listAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PendingFriendsDialog dialog = new PendingFriendsDialog();
                dialog.setFriendship(listAdapter.getItem(position));
                dialog.setFriendshipRequestType(listAdapter.getFriendshipRequestType());
                dialog.show(getFragmentManager(), PendingFriendsDialog.class.getSimpleName());
            }
        });

        Button bt_received = (Button) findViewById(R.id.bt_received);
        Button bt_sent = (Button) findViewById(R.id.bt_sent);

        bt_received.setOnClickListener(this);
        bt_sent.setOnClickListener(this);

        v_underscoreReceived = findViewById(R.id.v_underscoreReceived);
        v_underscoreSent = findViewById(R.id.v_underscoreSent);

        tv_hintNoPendingRequests = (TextView) findViewById(R.id.tv_hintNoPendingRequests);
    }

    @Override
    public void onResume(){
        Log.d(LOG_TAG, "onResume()");
        super.onResume();

        FriendshipRequestType type = listAdapter.getFriendshipRequestType();

        listAdapter.setFriendshipRequestType(type);
        listAdapter.reload();
        boolean isEmptyList = listAdapter.getCount() <= 0;

        refreshButtonUnderscore(type);
        refreshEmptyListHint(type, isEmptyList);
    }

    @Override
    public void onStop(){

        User sessionUser = SessionHelper.getSessionUser(this);
        FriendshipDao dao = new FriendshipDaoImpl(this);
        List<Friendship> newOnes = dao.getFriendships(sessionUser, FriendshipRequestType.RECEIVED, FriendshipState.REQUEST_SEND);

        for(Friendship f: newOnes){
            f.setFriendshipState(FriendshipState.REQUEST_RECEIVED);
            dao.updateFriendship(f);
        }

        super.onStop();
    }


    @Override
    public void onClick(View v) {

        FriendshipRequestType type;
        boolean isEmptyList;

        switch (v.getId()){

            case R.id.bt_received:

                type = FriendshipRequestType.RECEIVED;

                listAdapter.setFriendshipRequestType(type);
                listAdapter.reload();
                isEmptyList = listAdapter.getCount() <= 0;

                refreshButtonUnderscore(type);
                refreshEmptyListHint(type, isEmptyList);
                break;

            case R.id.bt_sent:

                type = FriendshipRequestType.SENT;

                listAdapter.setFriendshipRequestType(type);
                listAdapter.reload();
                isEmptyList = listAdapter.getCount() <= 0;

                refreshButtonUnderscore(type);
                refreshEmptyListHint(type, isEmptyList);
                break;
        }
    }

    private void refreshButtonUnderscore(FriendshipRequestType type){
        if(FriendshipRequestType.RECEIVED.equals(type)){
            v_underscoreSent.setVisibility(View.GONE);
            v_underscoreReceived.setVisibility(View.VISIBLE);
        }else{
            v_underscoreSent.setVisibility(View.VISIBLE);
            v_underscoreReceived.setVisibility(View.GONE);
        }
    }

    private void refreshEmptyListHint(FriendshipRequestType type, boolean isEmptyList){


        if(isEmptyList){
            String emptyListHint = FriendshipRequestType.RECEIVED.equals(type) ?
                    getResources().getString(R.string.pendingFriends_tv_hintNoRequestsReceived) :
                    getResources().getString(R.string.pendingFriends_tv_hintNoRequestsSent);
            tv_hintNoPendingRequests.setText(emptyListHint);
            tv_hintNoPendingRequests.setVisibility(View.VISIBLE);
        }else{
            tv_hintNoPendingRequests.setText("");
            tv_hintNoPendingRequests.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDialogClose() {
        Log.d(LOG_TAG, "onDialogClose()");

        FriendshipRequestType type = listAdapter.getFriendshipRequestType();

        listAdapter.setFriendshipRequestType(type);
        listAdapter.reload();
        boolean isEmptyList = listAdapter.getCount() <= 0;

        refreshButtonUnderscore(type);
        refreshEmptyListHint(type, isEmptyList);
    }
}