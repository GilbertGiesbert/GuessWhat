package com.joern.guesswhat.activity.friends;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.joern.guesswhat.R;
import com.joern.guesswhat.database.UserDao;
import com.joern.guesswhat.database.UserDaoImpl;
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

    private ArrayAdapter<String> listAdapter;

    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pendingfriends_activity);

        userDao = new UserDaoImpl(this);

        ListView l1 = (ListView) findViewById(R.id.lv_pendingRequsts);

        ArrayList<String> values = new ArrayList<>();
        values.addAll(getNameList("r-"));

        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
        l1.setAdapter(listAdapter);

        bt_received = (Button) findViewById(R.id.bt_received);
        bt_sent = (Button) findViewById(R.id.bt_sent);

        bt_received.setOnClickListener(this);
        bt_sent.setOnClickListener(this);

        v_underscoreReceived = findViewById(R.id.v_underscoreReceived);
        v_underscoreSent = findViewById(R.id.v_underscoreSent);
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
            updateList(getNameList("s-"));

        }else{

            v_underscoreSent.setVisibility(View.GONE);
            v_underscoreReceived.setVisibility(View.VISIBLE);
            updateList(getNameList("r-"));
        }
    }

    private void updateList(ArrayList<String> update){

        listAdapter.clear();
        listAdapter.addAll(update);

    }

    private ArrayList<String> getNameList(String prefix){

        ArrayList<String> nameList = new ArrayList<>();

        List<User> userList = userDao.getAllUsers();

        for(User u: userList){

            nameList.add(prefix + u.getName());
        }

        return nameList;
    }
}