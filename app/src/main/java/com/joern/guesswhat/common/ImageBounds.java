package com.joern.guesswhat.common;

import android.graphics.BitmapFactory;

/**
 * Created by joern on 21.04.2015.
 */
public class ImageBounds {

    private int width = 0;
    private int height = 0;

    public void loadFromFile(String filePath){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        width = options.outWidth;
        height = options.outHeight;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}