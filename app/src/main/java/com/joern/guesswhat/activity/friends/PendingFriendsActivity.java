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
import com.joern.guesswhat.model.FriendshipState;
import com.joern.guesswhat.model.User;
import com.joern.guesswhat.persistence.database.FriendshipDao;
import com.joern.guesswhat.persistence.database.FriendshipDaoImpl;

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

        listAdapter = new PendingFriendshipsAdapter(this, FriendshipState.REQUEST_RECEIVED);

        ListView list = (ListView) findViewById(R.id.lv_pendingRequsts);
        list.setAdapter(listAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PendingFriendsDialog dialog = new PendingFriendsDialog();
                dialog.setFriendship(listAdapter.getItem(position));
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

        listAdapter.reload();
        boolean isEmptyList = listAdapter.getCount() <= 0;

        FriendshipState state = listAdapter.getState();
        refreshButtonUnderscore(state);
        refreshEmptyListHint(state, isEmptyList);
    }

    @Override
    public void onStop(){

        User sessionUser = SessionHelper.getSessionUser(this);
        FriendshipDao dao = new FriendshipDaoImpl(this);
//        List<Friendship> newOnes = dao.getFriendships(sessionUser, FriendshipRequestType.RECEIVED, FriendshipState.REQUEST_SENT);

//        for(Friendship f: newOnes){
//            f.setFriendshipState(FriendshipState.REQUEST_RECEIVED);
//            dao.updateFriendship(f);
//        }

        super.onStop();
    }


    @Override
    public void onClick(View v) {

        FriendshipState state;
        boolean isEmptyList;

        switch (v.getId()){

            case R.id.bt_received:

                state = FriendshipState.REQUEST_RECEIVED;

                listAdapter.setState(state);
                listAdapter.reload();
                isEmptyList = listAdapter.getCount() <= 0;

                refreshButtonUnderscore(state);
                refreshEmptyListHint(state, isEmptyList);
                break;

            case R.id.bt_sent:

                state = FriendshipState.REQUEST_SENT;

                listAdapter.setState(state);
                listAdapter.reload();
                isEmptyList = listAdapter.getCount() <= 0;

                refreshButtonUnderscore(state);
                refreshEmptyListHint(state, isEmptyList);
                break;
        }
    }

    private void refreshButtonUnderscore(FriendshipState state){

        if(FriendshipState.REQUEST_RECEIVED.equals(state)){
            v_underscoreSent.setVisibility(View.GONE);
            v_underscoreReceived.setVisibility(View.VISIBLE);

        }if(FriendshipState.REQUEST_SENT.equals(state)){
            v_underscoreSent.setVisibility(View.VISIBLE);
            v_underscoreReceived.setVisibility(View.GONE);
        }
    }

    private void refreshEmptyListHint(FriendshipState state, boolean isEmptyList){


        if(isEmptyList){

            if(FriendshipState.REQUEST_RECEIVED.equals(state)){
                tv_hintNoPendingRequests.setText(getResources().getString(R.string.pendingFriends_tv_hintNoRequestsReceived));
                tv_hintNoPendingRequests.setVisibility(View.VISIBLE);
            }else if(FriendshipState.REQUEST_SENT.equals(state)){
                tv_hintNoPendingRequests.setText(getResources().getString(R.string.pendingFriends_tv_hintNoRequestsSent));
                tv_hintNoPendingRequests.setVisibility(View.VISIBLE);
            }

            else{
                tv_hintNoPendingRequests.setText("");
                tv_hintNoPendingRequests.setVisibility(View.GONE);
            }

        }else{
            tv_hintNoPendingRequests.setText("");
            tv_hintNoPendingRequests.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDialogClose() {
        Log.d(LOG_TAG, "onDialogClose()");

        listAdapter.reload();
        boolean isEmptyList = listAdapter.getCount() <= 0;

        FriendshipState state = listAdapter.getState();
        refreshButtonUnderscore(state);
        refreshEmptyListHint(state, isEmptyList);
    }
}