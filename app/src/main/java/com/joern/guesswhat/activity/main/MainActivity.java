package com.joern.guesswhat.activity.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.joern.guesswhat.R;
import com.joern.guesswhat.activity.friends.FriendsActivity;
import com.joern.guesswhat.activity.login.LoginActivity;
import com.joern.guesswhat.activity.navigation.NavigationDrawerActivity;
import com.joern.guesswhat.common.ImageLoader;
import com.joern.guesswhat.common.SessionHelper;
import com.joern.guesswhat.model.User;

/**
 * should be an overview

 */
public class MainActivity extends NavigationDrawerActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected int getMainContentLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        if(sessionUser != null){

            String userInfo =
                    "User info:" + "\n" +
                            "Id: "+sessionUser.getId() + "\n" +
                            "Alias: "+sessionUser.getStableAlias() + "\n" +
                            "Name: "+sessionUser.getName() + "\n" +
                            "Mail: "+sessionUser.getEmail() + "\n" +
                            "Pswd#: "+sessionUser.getPasswordHash();

            TextView tv_test = (TextView) findViewById(R.id.tv_test);
            tv_test.setText(userInfo);

        }
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(LOG_TAG, "onCreate()");

//        doImageTest();

    }

    private void go2Friends(){

        Intent intent = new Intent(this, FriendsActivity.class);
        startActivity(intent);
    }

    private void doImageTest(){

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl_redBase);

        ImageView iv_test = (ImageView) findViewById(R.id.iv_test);


        String imagePath = "/sdcard/Download/test_pic_large_horizontal.jpg";

        float dp = 120;
        float px = ImageLoader.dpToPx(dp, this);
        Log.d(LOG_TAG, "px="+px);



//            Bitmap bitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(imagePath), (int)px,(int)px);
        Bitmap bitmap = ImageLoader.loadFromFile(imagePath, (int) px, (int) px);


        BitmapDrawable d = new BitmapDrawable(getResources(), bitmap);


            iv_test.setImageDrawable(d);



    }
}