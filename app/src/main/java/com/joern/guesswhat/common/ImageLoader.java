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

    public static Bitmap loadFromFile(String filePath, int targetWidth, int targetHeight){
        Log.d(LOG_TAG, "loadFromFile()");
        Log.d(LOG_TAG, "filePath="+filePath);
        Log.d(LOG_TAG, "targetWidth="+targetWidth);
        Log.d(LOG_TAG, "targetHeight="+targetHeight);

        ImageBounds imageBounds = new ImageBounds();
        imageBounds.loadFromFile(filePath);

        // convert bounds to double so that results of calculations with bounds are no integer
        double sourceWidth = imageBounds.getWidth();
        double sourceHeight = imageBounds.getHeight();

        Log.d(LOG_TAG, "sourceWidth="+sourceWidth);
        Log.d(LOG_TAG, "sourceHeight="+sourceHeight);


        double scaleFactor = targetWidth / sourceWidth;
        double scaledWidth;
        double scaledHeight = sourceHeight * scaleFactor;

        // if new height is out of bounds --> rescale to fit height
        if(scaledHeight > targetHeight){
            scaleFactor = targetHeight / sourceHeight;
            Log.d(LOG_TAG, "scaling to fit height");
        }else{
            Log.d(LOG_TAG, "scaling to fit width");
        }

        Bitmap bitmap;

        if(scaleFactor < 1){

            Log.d(LOG_TAG, "scaling down");

            // -----------------------------------------
            // some Info on sampleSize and BitmapFactory:
            //
            // BitmapFactory can load an smaller image to save memory
            // therefore it uses sampleSize like:
            // originalImage / sampleSize = smallerImage
            // or in other words
            // originalImage is x times greater than smallerImage where x = sampleSize
            //
            // per documentation sampleSize must be an Integer AND a power of 2
            //
            // if sampleSize = 1 BitmapFactory loads image with original size
            // -----------------------------------------

            // ----------------------------------------------
            // some Info on ImageLoader loading large Images:
            //
            // if ImageLoader must load an image larger than target size
            // it downscales original image as often as result is still larger than target size
            // rest of downscaling to exact target size will be done by ImageView
            //
            // downscaling below target size is not preferred because this would require
            // an upscale of an small image which gives a blurry result
            //
            // note:
            // ImageLoader can't downscale original image to exact target size
            // because downscaling factor is no exact double value
            // in fact downscaling factor is sampleSize of BitmapFactory
            // so steps of downscaling are rather large (see documentation on sampleSize)
            // ----------------------------------------------


            // 'floor' because sampleSize needs to be an int
            // rather 'floor' than 'ceil' to avoid scaled size is smaller than targetSize
            int sampleSize = (int) (Math.floor(1.0 / scaleFactor));
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
            scaleFactor = Math.ceil(scaleFactor);

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

    public static float pxToDp(float px, Context context){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px, context.getResources().getDisplayMetrics());
    }
}