package com.joern.guesswhat.persistence.filestorage;


import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileStorage {

    private static final String LOG_TAG = FileStorage.class.getSimpleName();

    public enum StorageType{

        INTERNAL,
        SD_CARD // caution location of sd card folder is not always physically on sd card
    }

    public static <T> boolean writeData(Context context, StorageType storageType, String folderPath, String fileName, T object){
        Log.d(LOG_TAG, "writeFile()");
        Log.d(LOG_TAG, "fileName="+fileName);

        boolean writeSuccessful = false;

        try{

            File folder = new File(getAbsoluteFolderPath(context, storageType, folderPath));
            folder.mkdirs();

            String fullFilePath = folder.getAbsolutePath() + File.separator + fileName;
            Log.d(LOG_TAG, "fullFilePath="+fullFilePath);

            File file = new File(fullFilePath);

            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(object);
            out.close();

            writeSuccessful = true;

        }catch (Exception ex){
            Log.d(LOG_TAG, "problems writing file", ex);
        }

        Log.d(LOG_TAG, "writeSuccessful="+writeSuccessful);
        return writeSuccessful;
    }

    public static <T>  T readFile(Context context, StorageType storageType, String folderPath, String fileName){
        Log.d(LOG_TAG, "readFile()");
        Log.d(LOG_TAG, "fileName="+fileName);

        T data = null;
        boolean readSuccessful = false;

        try{

            File folder = new File(getAbsoluteFolderPath(context, storageType, folderPath));

            String fullFilePath = folder.getAbsolutePath() + File.separator + fileName;
            Log.d(LOG_TAG, "fullFilePath="+fullFilePath);

            File file = new File(fullFilePath);

            ObjectInput in = new ObjectInputStream(new FileInputStream(file));
            data = (T) in.readObject();
            in.close();

            readSuccessful = true;
        }
        catch (Exception ex){
            Log.e(LOG_TAG, "problems reading file", ex);
        }

        Log.d(LOG_TAG, "readSuccessful="+readSuccessful);
        return data;
    }

    private static String getAbsoluteFolderPath(Context context, StorageType storageType, String relativeFolderPath){
        Log.d(LOG_TAG, "getAbsoluteFolderPath()");
        Log.d(LOG_TAG, "storageType="+storageType);

        if(relativeFolderPath == null) return null;

        String basePath;
        if(StorageType.INTERNAL.equals(storageType)){
            basePath = context.getFilesDir().getAbsolutePath();
        }else{
            basePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        }

        Log.d(LOG_TAG, "basePath="+basePath);
        Log.d(LOG_TAG, "relativeFolderPath="+relativeFolderPath);

        relativeFolderPath = relativeFolderPath.trim();
        if(relativeFolderPath.length() > 0){
            return basePath + File.separator + relativeFolderPath;
        }else{
            return basePath;
        }
    }
}