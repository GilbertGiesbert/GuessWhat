package com.joern.guesswhat.activity.game;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joern.guesswhat.R;

/**
 * Created by joern on 09.07.2015.
 */
public class GamesTab extends Fragment {

    private static final String INSTANCE_STATE_TYPE = "INSTANCE_STATE_TYPE";

    private GamesTabType tabType;

    public void init(GamesTabType tabType){
        this.tabType = tabType;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(tabType == null){
            tabType = GamesTabType.valueOf(savedInstanceState.getString(INSTANCE_STATE_TYPE));
        }

        View view = inflater.inflate(R.layout.games_tab, container, false);

        TextView tv = (TextView) view.findViewById(R.id.tv_subtitle);
        tv.setText(tabType.getSubtitle(getActivity()));



        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

//        listAdapter.reload();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(INSTANCE_STATE_TYPE, "" + tabType);
    }

}
