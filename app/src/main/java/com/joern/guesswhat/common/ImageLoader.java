package com.joern.guesswhat.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.TypedValue;

/**
 * Created by joern on 21.04.2015.
 */
public class ImageLoader {

    private static final String LOG_TAG = ImageLoader.class.getSimpleName();

    public static Bitmap loadFromFile(String filePath, int containerWidth, int containerHeight){
        Log.d(LOG_TAG, "loadFromFile()");
        Log.d(LOG_TAG, "filePath="+filePath);
        Log.d(LOG_TAG, "containerWidth="+containerWidth);
        Log.d(LOG_TAG, "containerHeight="+containerHeight);

        BitmapFactory.Options options = new BitmapFactory.Options();

        // don't load image, just check bounds
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(filePath, options);
        int sourceWidth = options.outWidth;
        int sourceHeight = options.outHeight;
        Log.d(LOG_TAG, "sourceWidth="+sourceWidth);
        Log.d(LOG_TAG, "sourceHeight="+sourceHeight);

        // load real image, not just bounds
        options.inJustDecodeBounds = false;

        if(sourceWidth > containerWidth || sourceHeight > containerHeight){
            options.inSampleSize = calculateInSampleSize(sourceWidth, sourceHeight, containerWidth, containerHeight);
        }

        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

        Log.d(LOG_TAG, "loadedWidth="+bitmap.getWidth());
        Log.d(LOG_TAG, "loadedHeight="+bitmap.getHeight());

        float scaleFactor =(float) containerWidth / (float) bitmap.getWidth();
        int scaledWidth = (int)(bitmap.getWidth() * scaleFactor);
        int scaledHeight = (int)(bitmap.getHeight() * scaleFactor);

        if(scaledHeight > containerHeight){
            scaleFactor = (float)containerHeight / (float)bitmap.getHeight();
            scaledWidth = (int)(bitmap.getWidth() * scaleFactor);
            scaledHeight = (int)(bitmap.getHeight() * scaleFactor);
        }

        // re-use bitmap to save memory
        bitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, false);

        Log.d(LOG_TAG, "scaledWidth="+bitmap.getWidth());
        Log.d(LOG_TAG, "scaledHeight="+bitmap.getHeight());

        return bitmap;
    }

    // BitmapFactory.Options uses inSampleSize as a shrinking factor
    // to load images x times smaller than the original
    // (x = inSampleSize)
    // inSampleSize should be as great as possible to load smallest image size possible
    // and to save most memory possible
    // however resultingLength (=sourceLength / inSampleSize) should always be greater than requestedLength
    private static int calculateInSampleSize(double sourceWidth, double sourceHeight, int requestedWidth, int requestedHeight) {

        int inSampleSizeX = Math.round((float)sourceWidth/ (float)requestedWidth);
        int inSampleSizeY = Math.round((float)sourceHeight/ (float)requestedHeight);

        // smaller inSampleSize makes sure that resultingWidth AND resultingHeight
        // are greater than requestedWidth and requestedHeight
        return inSampleSizeX < inSampleSizeY ? inSampleSizeX : inSampleSizeY;
    }

    public static float dpToPx(float dp, Context context){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}