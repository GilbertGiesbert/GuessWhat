package com.joern.guesswhat.activity.friends;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.joern.guesswhat.R;
import com.joern.guesswhat.model.Friendship;

/**
 * Created by joern on 07.07.2015.
 */
public class FriendsTab extends Fragment {

    private static final String INSTANCE_STATE_TYPE = "INSTANCE_STATE_TYPE";

    private FriendsTabType tabType;
    private FriendListAdapter listAdapter;


    public void init(FriendsTabType tabType){
        this.tabType = tabType;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(tabType == null){
            tabType = FriendsTabType.valueOf(savedInstanceState.getString(INSTANCE_STATE_TYPE));
        }

        View view = inflater.inflate(R.layout.friends_tab, container, false);

        TextView tv = (TextView) view.findViewById(R.id.tv_subtitle);
        tv.setText(tabType.getSubtitle(getActivity()));

        listAdapter = new FriendListAdapter(getActivity(), tabType);

        ListView lv_friendList = (ListView) view.findViewById(R.id.lv_friendlist);
        lv_friendList.setAdapter(listAdapter);
        lv_friendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Friendship friendship = listAdapter.getItem(position);
                FriendsDialog dialog = new FriendsDialog();
                dialog.init(tabType, friendship);
                dialog.show(getActivity().getFragmentManager(), FriendsDialog.class.getSimpleName());
            }
        });

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
        outState.putString(INSTANCE_STATE_TYPE, "" + tabType);
    }

    public void reloadList() {
        listAdapter.reload();
    }
}