package com.joern.guesswhat.activity.navigation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.joern.guesswhat.R;
import com.joern.guesswhat.activity.login.LoginActivity;
import com.joern.guesswhat.common.SessionHelper;
import com.joern.guesswhat.model.User;

/**
 * Activities that inherit from NavigationDrawerActivity have the ability to show and use a NavigationDrawer.
 */
public abstract class NavigationDrawerActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String LOG_TAG = NavigationDrawerActivity.class.getSimpleName();

    private NavigationDrawerAdapter drawerAdapter;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    protected User sessionUser;

    protected abstract int getMainContentLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        sessionUser = SessionHelper.getSessionUser(this);
        if(sessionUser == null) {
            go2Login();
        }else{

            setContentView(R.layout.navigation_activity);
            setMainContentView();

            initNavigationDrawer();
            getSupportActionBar().setTitle(getTitle());

            TextView tv_profile_name = (TextView) findViewById(R.id.tv_profile_name);
            tv_profile_name.setText(sessionUser.getName());

            TextView tv_logout = (TextView) findViewById(R.id.tv_logout);
            tv_logout.setOnClickListener(this);

        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState){
        Log.d(LOG_TAG, "onPostCreate()");
        super.onPostCreate(savedInstanceState);

        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onOptionsItemSelected()");

        int itemId = item.getItemId();

        String s_itemId = getResources().getResourceEntryName(itemId);// to view in debugger
        Log.d(LOG_TAG, "itemId="+s_itemId);

        if(drawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    private void setMainContentView(){
        Log.d(LOG_TAG, "setMainContentView()");

        ViewStub stub = (ViewStub)findViewById(R.id.vs_mainView);
        stub.setLayoutResource(getMainContentLayoutId());
        stub.inflate();
    }

    private void initNavigationDrawer(){
        Log.d(LOG_TAG, "initNavigationDrawer()");

        drawerAdapter = new NavigationDrawerAdapter(this);

        ListView lv_drawer = (ListView) findViewById(R.id.lv_navigationList);
        lv_drawer.setAdapter(drawerAdapter);
        lv_drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(LOG_TAG, "onItemClick()");

                handleNavigationDrawerItemClick(position);
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.dl_navigationDrawer_root);
        drawerToggle = new DrawerToggle( this, drawerLayout, R.string.navigation_openDrawer, R.string.navigation_closeDrawer);
        drawerLayout.setDrawerListener(drawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        boolean isTopLevelActivity = isTopLevelActivity();
        Log.d(LOG_TAG, "isTopLevelActivity="+isTopLevelActivity);
        drawerToggle.setDrawerIndicatorEnabled(isTopLevelActivity);
    }

    private boolean isTopLevelActivity(){
        Log.d(LOG_TAG, "isTopLevelActivity()");
        return NavigationDrawerItem.valueOf(this.getClass()) != null;
    }

    private void handleNavigationDrawerItemClick(int position){
        Log.d(LOG_TAG, "handleNavigationDrawerItemClick()");
        Log.d(LOG_TAG, "position="+position);

        NavigationDrawerItem clickedItem = (NavigationDrawerItem) drawerAdapter.getItem(position);
        Log.d(LOG_TAG, "clickedItem="+clickedItem);

        drawerLayout.closeDrawers();

        if(this.getClass() != clickedItem.getTargetActivityClass()){

            Intent intent = new Intent(this, clickedItem.getTargetActivityClass());
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        Log.d(LOG_TAG, "onClick()");

        if(v.getId() == R.id.tv_logout){
            SessionHelper.stopSession(this);
            go2Login();
        }
    }

    private class DrawerToggle extends ActionBarDrawerToggle{

        public DrawerToggle(Activity activity, DrawerLayout drawerLayout, int openDrawerContentDescRes, int closeDrawerContentDescRes){
            super(activity, drawerLayout, openDrawerContentDescRes, closeDrawerContentDescRes);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            Log.d(LOG_TAG, "onDrawerOpened()");
            super.onDrawerOpened(drawerView);

            if(!isTopLevelActivity()){
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }

            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            Log.d(LOG_TAG, "onDrawerClosed()");
            super.onDrawerClosed(drawerView);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            getSupportActionBar().setTitle(getTitle());
        }
    }

    private void go2Login(){
        Log.d(LOG_TAG, "go2Login()");

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}