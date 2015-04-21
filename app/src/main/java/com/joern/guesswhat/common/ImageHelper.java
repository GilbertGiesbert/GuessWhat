package com.joern.guesswhat.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.TypedValue;

/**
 * Created by joern on 21.04.2015.
 */
public class ImageHelper {

    public enum PreferredScalingDeviation{
        smallerThanTarget, greaterThanTarget
    }

    private static final String LOG_TAG = ImageHelper.class.getSimpleName();

    public static Bitmap loadFromFile(String filePath, int targetWidth, int targetHeight, PreferredScalingDeviation preferredScalingDeviation){
        Log.d(LOG_TAG, "loadFromFile()");
        Log.d(LOG_TAG, "filePath="+filePath);
        Log.d(LOG_TAG, "targetWidth="+targetWidth);
        Log.d(LOG_TAG, "targetHeight="+targetHeight);

        ImageBounds imageBounds = new ImageBounds();
        imageBounds.loadFromFile(filePath);

        double sourceWidth = imageBounds.getWidth();
        double sourceHeight = imageBounds.getHeight();

        Log.d(LOG_TAG, "sourceWidth="+sourceWidth);
        Log.d(LOG_TAG, "sourceHeight="+sourceHeight);

        double scaleFactor = targetWidth / sourceWidth;
        double scaledWidth;
        double scaledHeight = sourceHeight * scaleFactor;

        // if new height is out of bounds --> rescale to fit height
        if(scaledHeight > targetHeight){

            Log.d(LOG_TAG, "scaling to target height");
            scaleFactor = targetHeight / sourceHeight;
        }else{
            Log.d(LOG_TAG, "scaling to target width");
        }

        Bitmap bitmap;

        if(scaleFactor < 1){

            Log.d(LOG_TAG, "scaling down");

            // 'floor' because sampleSize needs to be an int
            // rather 'floor' than 'ceil' to avoid scaled size is smaller than targetSize
//            int sampleSize = (int) (Math.floor(1.0 / scaleFactor));

            int sampleSize;

            if(PreferredScalingDeviation.greaterThanTarget.equals(preferredScalingDeviation)){
                sampleSize = (int) (Math.floor(1.0 / scaleFactor));
            }else{
                sampleSize = (int) (Math.ceil(1.0 / scaleFactor));
            }
            Log.d(LOG_TAG, "sampleSize="+sampleSize);


            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = sampleSize;
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(filePath, options);

            // just for logging
            scaleFactor = 1.0 / sampleSize;
            scaledWidth = sourceWidth * scaleFactor;
            scaledHeight = sourceHeight * scaleFactor;

        }else{
            Log.d(LOG_TAG, "scaling up");

            // 'ceil' because scaledWidth and scaledHeight need to be integers
            // rather 'ceil' than 'floor' to avoid scaled size is smaller than targetSize
//            scaleFactor = Math.ceil(scaleFactor);

            if(PreferredScalingDeviation.greaterThanTarget.equals(preferredScalingDeviation)){
                scaleFactor = Math.ceil(scaleFactor);
            }else{
                scaleFactor = Math.floor(scaleFactor);
            }


            scaledWidth = sourceWidth * scaleFactor;
            scaledHeight = sourceHeight * scaleFactor;

            // original bitmap
            bitmap = BitmapFactory.decodeFile(filePath);
            // scaled bitmap
            bitmap = Bitmap.createScaledBitmap(bitmap, (int)scaledWidth, (int)scaledHeight, false);
        }

        Log.d(LOG_TAG, "scaleFactor=" + getReadableDouble(scaleFactor, 2));
        Log.d(LOG_TAG, "scaledWidth="+ getReadableDouble(scaledWidth, 2));
        Log.d(LOG_TAG, "scaledHeight="+ getReadableDouble(scaledHeight, 2));



        return bitmap;
    }

    public static String getReadableDouble(double d, int decimalPlacesCount){
        return String.format("%."+decimalPlacesCount+"f", d);
    }

    public static float dpToPx(float dp, Context context){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static float dpToPx2(float dp, Context context){

        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);

    }

    public static float pxToDp(float px, Context context){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px, context.getResources().getDisplayMetrics());
    }
}