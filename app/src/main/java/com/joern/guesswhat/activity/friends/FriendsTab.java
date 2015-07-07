package com.joern.guesswhat.activity.friends;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joern.guesswhat.R;

/**
 * Created by joern on 07.07.2015.
 */
public class FriendsTab extends Fragment {

    private String test = "bla";
    public void setTest(String test){
        this.test = test;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the view from fragmenttab1.xml
        View view = inflater.inflate(R.layout.friends_tab, container, false);

        TextView tv = (TextView) view.findViewById(R.id.tv_tab);
        tv.setText("- "+test);

        return view;
    }



}
