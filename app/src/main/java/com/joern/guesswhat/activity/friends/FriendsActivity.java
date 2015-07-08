package com.joern.guesswhat.activity.friends;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.joern.guesswhat.R;
import com.joern.guesswhat.activity.navigation.NavigationDrawerActivity;

/**
 * Created by joern on 14.04.2015.
 */
public class FriendsActivity extends NavigationDrawerActivity {

    private static final String LOG_TAG = FriendsActivity.class.getSimpleName();

    private ViewPager vp_friends;
    private FriendsPagerAdapter pagerAdapter;


    @Override
    protected int getMainContentLayoutId() {
        return R.layout.friends_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        vp_friends = (ViewPager) findViewById(R.id.vp_friends);

        pagerAdapter = new FriendsPagerAdapter(getSupportFragmentManager(), this);

        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_friends);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(LOG_TAG, "onCreateOptionsMenu()");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.friends_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.mi_refresh) {

            FriendsTab tab = getCurrentTab();
            tab.reloadList();
        }


        else if (id == R.id.mi_addFriend) {
            DialogFragment newFragment = new AddFriendDialog();
            newFragment.show(getFragmentManager(), AddFriendDialog.class.getSimpleName());
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private FriendsTab getCurrentTab(){

        int position = vp_friends.getCurrentItem();
        FriendsTab tab = (FriendsTab)getSupportFragmentManager().getFragments().get(position);
        return tab;
    }
}