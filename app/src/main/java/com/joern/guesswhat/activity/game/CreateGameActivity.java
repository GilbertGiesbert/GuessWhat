package com.joern.guesswhat.activity.game;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.joern.guesswhat.R;
import com.joern.guesswhat.activity.navigation.NavigationDrawerActivity;
import com.joern.guesswhat.common.ImageLoader;

import java.io.File;

/**
 * Created by joern on 27.04.2015.
 */
public class CreateGameActivity extends NavigationDrawerActivity implements View.OnLayoutChangeListener, View.OnClickListener {

    private static final String LOG_TAG = CreateGameActivity.class.getSimpleName();

    // arbitrary
    private static final int PICK_FILE_RESULT_CODE = 333;

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


        ImageView v = (ImageView) findViewById(R.id.iv_gamePicture);
        v.addOnLayoutChangeListener(this);
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

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent,PICK_FILE_RESULT_CODE);
                break;
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.d(LOG_TAG, "onActivityResult()");
//
//        if(requestCode  == PICK_FILE_RESULT_CODE && resultCode == RESULT_OK){
//
//            String imagePath = data.getData().getPath();
//            Log.d(LOG_TAG, "imagePath:"+imagePath);
//            File imageFile = new  File(imagePath);
//
//            if(imageFile.exists()){
//
//                Bitmap bitmap  = ImageLoader.loadFromFile(imagePath, 400, 200);
//                iv_gamePicture.setImageBitmap(bitmap);
//            }
//
//        }else{
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(LOG_TAG, "onActivityResult()");

        if(requestCode  == PICK_FILE_RESULT_CODE && resultCode == RESULT_OK){

            Uri imageUri = data.getData();
            Log.d(LOG_TAG, "imageUri:"+imageUri);

            String imagePath = imageUri.getPath();
            Log.d(LOG_TAG, "imagePath:"+imagePath);

            File imageFile = new  File(imagePath);

            if(imageFile.exists()){

                Bitmap bitmap  = ImageLoader.loadFromFile(imagePath, 400, 200);
                iv_gamePicture.setImageBitmap(bitmap);
            }



        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public static String getPath2(Context context, Uri uri) {

        final String id = DocumentsContract.getDocumentId(uri);
        final Uri contentUri = ContentUris.withAppendedId(
                Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

        return getDataColumn(context, contentUri, null, null);

    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
}
