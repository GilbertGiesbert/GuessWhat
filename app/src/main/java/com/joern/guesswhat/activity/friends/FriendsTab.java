package com.joern.guesswhat.activity.friends;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.joern.guesswhat.R;

/**
 * Created by joern on 07.07.2015.
 */
public class FriendsTab extends Fragment {

    private static final String INSTANCE_STATE_TYPE = "INSTANCE_STATE_TYPE";

    private FriendsTabType type;
    private FriendListAdapter listAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(type == null){
            type = FriendsTabType.valueOf(savedInstanceState.getString(INSTANCE_STATE_TYPE));
        }

        View view = inflater.inflate(R.layout.friends_tab, container, false);

        TextView tv = (TextView) view.findViewById(R.id.tv_subheading);
        tv.setText(type.getSubheading(getActivity()));

        listAdapter = new FriendListAdapter(getActivity(), type);

        ListView lv_friendList = (ListView) view.findViewById(R.id.lv_friendlist);
        lv_friendList.setAdapter(listAdapter);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        listAdapter.reload();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(INSTANCE_STATE_TYPE, "" + type);
    }

    public void init(FriendsTabType type){
        this.type = type;
    }

    public void reloadList() {
        listAdapter.reload();
    }

    public FriendsTabType getType(){
        return type;
    }
}