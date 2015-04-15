package com.joern.guesswhat.activity.friends;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.joern.guesswhat.R;
import com.joern.guesswhat.common.SessionHelper;
import com.joern.guesswhat.database.FriendshipDao;
import com.joern.guesswhat.database.FriendshipDaoImpl;
import com.joern.guesswhat.database.UserDao;
import com.joern.guesswhat.database.UserDaoImpl;
import com.joern.guesswhat.model.Friendship;
import com.joern.guesswhat.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joern on 14.04.2015.
 */
public class PendingFriendsActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String LOG_TAG = PendingFriendsActivity.class.getSimpleName();

    private Button bt_received, bt_sent;
    private View v_underscoreReceived, v_underscoreSent;
    private TextView tv_hintNoPendingRequests;

    private ArrayAdapter<String> listAdapter;

    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pendingfriends_activity);

        userDao = new UserDaoImpl(this);

        ArrayList<String> values = new ArrayList<>();
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);

        ListView l1 = (ListView) findViewById(R.id.lv_pendingRequsts);
        l1.setAdapter(listAdapter);

        bt_received = (Button) findViewById(R.id.bt_received);
        bt_sent = (Button) findViewById(R.id.bt_sent);

        bt_received.setOnClickListener(this);
        bt_sent.setOnClickListener(this);

        v_underscoreReceived = findViewById(R.id.v_underscoreReceived);
        v_underscoreSent = findViewById(R.id.v_underscoreSent);

        tv_hintNoPendingRequests = (TextView) findViewById(R.id.tv_hintNoPendingRequests);

        toggle();
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
            updateList(getAllFriendshipsRequestedByUser());

            if(listAdapter.getCount() > 0){
                tv_hintNoPendingRequests.setVisibility(View.GONE);

            }else{
                tv_hintNoPendingRequests.setVisibility(View.VISIBLE);
                tv_hintNoPendingRequests.setText(getResources().getString(R.string.pendingFriends_tv_hintNoRequestsSent));
            }

        }else{

            v_underscoreSent.setVisibility(View.GONE);
            v_underscoreReceived.setVisibility(View.VISIBLE);
            updateList(getAllFriendshipsRequestedByOthers());

            if(listAdapter.getCount() > 0){
                tv_hintNoPendingRequests.setVisibility(View.GONE);

            }else{
                tv_hintNoPendingRequests.setVisibility(View.VISIBLE);
                tv_hintNoPendingRequests.setText(getResources().getString(R.string.pendingFriends_tv_hintNoRequestsReceived));
            }
        }
    }

    private void updateList(ArrayList<String> update){

        listAdapter.clear();
        listAdapter.addAll(update);
    }

    private ArrayList<String> getAllFriendshipsRequestedByOthers(){

        ArrayList<String> result = new ArrayList<>();

        User sessionUser = SessionHelper.getSessionUser(this);
        FriendshipDao dao = new FriendshipDaoImpl(this);
        List<Friendship> fList = dao.getAllFriendshipsRequestedByOthers(sessionUser);

        for(Friendship f: fList){

            result.add(f.geteMailRequester());
        }

        return result;
    }

    private ArrayList<String> getAllFriendshipsRequestedByUser(){

        ArrayList<String> result = new ArrayList<>();

        User sessionUser = SessionHelper.getSessionUser(this);
        FriendshipDao dao = new FriendshipDaoImpl(this);
        List<Friendship> fList = dao.getAllFriendshipsRequestedByUser(sessionUser);

        for(Friendship f: fList){

            result.add(f.geteMailRequester());
        }

        return result;
    }
}