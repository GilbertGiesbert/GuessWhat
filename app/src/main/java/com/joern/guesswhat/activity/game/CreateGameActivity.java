package com.joern.guesswhat.activity.game;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.joern.guesswhat.R;
import com.joern.guesswhat.activity.navigation.NavigationDrawerActivity;
import com.joern.guesswhat.constants.RequestCode;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by joern on 27.04.2015.
 */
public class CreateGameActivity extends NavigationDrawerActivity implements View.OnLayoutChangeListener, View.OnClickListener {

    private static final String LOG_TAG = CreateGameActivity.class.getSimpleName();

    private ImageView iv_gamePicture;

    @Override
    protected int getMainContentLayoutId() {
        return R.layout.creategame_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        if(sessionUser == null) return;

        Button bt_choosePicture = (Button) findViewById(R.id.bt_choosePicture);
        bt_choosePicture.setOnClickListener(this);


        iv_gamePicture = (ImageView) findViewById(R.id.iv_gamePicture);
        iv_gamePicture.addOnLayoutChangeListener(this);
    }

    private int w = 0;
    private int h = 0;

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        Log.d(LOG_TAG, "onLayoutChange()");
        Log.d(LOG_TAG, "v:"+getResources().getResourceEntryName(v.getId()));

        if(v.getId() == R.id.iv_gamePicture){

            // values are measured in pixels???
            int w = v.getWidth();
            int h = v.getHeight();

            if(this.w != w || this.h != h){

                this.w = w;
                this.h = h;

                Toast.makeText(this, "w:" + w + ",h:" + h, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {

        Log.d(LOG_TAG, "onClick()");
        Log.d(LOG_TAG, "v:"+getResources().getResourceEntryName(v.getId()));

        switch(v.getId()){

            case R.id.bt_choosePicture:

                Intent intent;

                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){

                    intent = new Intent(Intent.ACTION_GET_CONTENT);

                }else{

                    intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

                }

                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                try {
                    startActivityForResult(intent, RequestCode.PICK_IMAGE);
                }catch(ActivityNotFoundException ex){
                    Toast.makeText(this, "please install app that can open images", Toast.LENGTH_SHORT).show();
                }




                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(LOG_TAG, "onActivityResult()");

        if(requestCode  == RequestCode.PICK_IMAGE && resultCode == RESULT_OK && data != null){

            Uri imageUri = data.getData();
            Log.d(LOG_TAG, "imageUri:"+imageUri);

            try {
                ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(imageUri, "r");
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);

                InputStream in = getContentResolver().openInputStream(imageUri);
//                Bitmap bitmap1 = BitmapFactory.decodeStream();

                parcelFileDescriptor.close();

//                iv_gamePicture.setImageBitmap(bitmap1);

            } catch (IOException e) {

                String message = "problems loading selected image";

                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                Log.e(LOG_TAG, message, e);
            }

        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }






}
