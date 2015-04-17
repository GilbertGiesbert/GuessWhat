package com.joern.guesswhat.activity.friends;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.joern.guesswhat.R;
import com.joern.guesswhat.common.SessionHelper;
import com.joern.guesswhat.database.FriendshipDao;
import com.joern.guesswhat.database.FriendshipDaoImpl;
import com.joern.guesswhat.model.Friendship;
import com.joern.guesswhat.model.FriendshipRequestType;
import com.joern.guesswhat.model.FriendshipState;
import com.joern.guesswhat.model.User;

import java.util.List;

/**
 * Created by joern on 14.04.2015.
 */
public class PendingFriendsActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String LOG_TAG = PendingFriendsActivity.class.getSimpleName();

    private View v_underscoreReceived, v_underscoreSent;
    private TextView tv_hintNoPendingRequests;

    private PendingFriendshipsAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pendingfriends_activity);

        listAdapter = new PendingFriendshipsAdapter(this, FriendshipRequestType.RECEIVED);

        ListView list = (ListView) findViewById(R.id.lv_pendingRequsts);
        list.setAdapter(listAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                EditFriendshipRequestDialog dialog = new EditFriendshipRequestDialog();
                dialog.setFriendshipRequested(listAdapter.getItem(position));
                dialog.setFriendshipRequester(listAdapter.getFriendshipRequestType());
                dialog.show(getFragmentManager(), EditFriendshipRequestDialog.class.getSimpleName());
            }
        });

        Button bt_received = (Button) findViewById(R.id.bt_received);
        Button bt_sent = (Button) findViewById(R.id.bt_sent);

        bt_received.setOnClickListener(this);
        bt_sent.setOnClickListener(this);

        v_underscoreReceived = findViewById(R.id.v_underscoreReceived);
        v_underscoreSent = findViewById(R.id.v_underscoreSent);

        tv_hintNoPendingRequests = (TextView) findViewById(R.id.tv_hintNoPendingRequests);

        toggle(FriendshipRequestType.RECEIVED);
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
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.bt_received:
                toggle(FriendshipRequestType.RECEIVED);
                break;

            case R.id.bt_sent:
                toggle(FriendshipRequestType.SENT);
                break;
        }
    }

    private void toggle(FriendshipRequestType type){

        listAdapter.setFriendshipRequestType(type);

        if(FriendshipRequestType.RECEIVED.equals(type)){

            v_underscoreSent.setVisibility(View.GONE);
            v_underscoreReceived.setVisibility(View.VISIBLE);
            listAdapter.reload();

            if(listAdapter.getCount() > 0){
                tv_hintNoPendingRequests.setVisibility(View.GONE);

            }else{
                tv_hintNoPendingRequests.setVisibility(View.VISIBLE);
                tv_hintNoPendingRequests.setText(getResources().getString(R.string.pendingFriends_tv_hintNoRequestsReceived));
            }

        }else{

            v_underscoreSent.setVisibility(View.VISIBLE);
            v_underscoreReceived.setVisibility(View.GONE);
            listAdapter.reload();

            if(listAdapter.getCount() > 0){
                tv_hintNoPendingRequests.setVisibility(View.GONE);

            }else{
                tv_hintNoPendingRequests.setVisibility(View.VISIBLE);
                tv_hintNoPendingRequests.setText(getResources().getString(R.string.pendingFriends_tv_hintNoRequestsSent));
            }
        }
    }
}