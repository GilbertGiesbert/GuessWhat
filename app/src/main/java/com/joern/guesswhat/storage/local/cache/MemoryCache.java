package com.joern.guesswhat.storage.local.cache;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by joern on 10.07.2015.
 */
public class MemoryCache {

    private static final String TAG = MemoryCache.class.getSimpleName();

    private long size;//current allocated size
    private long limit;//max memory in bytes
    private Map<String, Bitmap> cache;

    private static MemoryCache singletonInstance;

    public static synchronized MemoryCache getInstance(){
        if(MemoryCache.singletonInstance == null){
            MemoryCache.singletonInstance = new MemoryCache();
        }
        return MemoryCache.singletonInstance;
    }

    private MemoryCache(){

        size = 0;

        //use 25% of available heap size
        limit = Runtime.getRuntime().maxMemory() / 4;
        Log.d(TAG, "MemoryCache will use up to " + limit / 1024. / 1024. + "MB");

        cache = Collections.synchronizedMap(
                new LinkedHashMap<String, Bitmap>(10, 1.5f, true));//Last argument true for LRU ordering
    }

    public Bitmap get(String id){

        try{

            if(!cache.containsKey(id)) {
                return null;
            }else {
                //NullPointerException sometimes happen here http://code.google.com/p/osmdroid/issues/detail?id=78
                return cache.get(id);
            }

        }catch(NullPointerException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public void put(String id, Bitmap bitmap){

        try{

            if(cache.containsKey(id)) {
                size -= getSizeInBytes(cache.get(id));
            }
            cache.put(id, bitmap);
            size+=getSizeInBytes(bitmap);
            checkSize();

        }catch(Throwable th){ // catch exceptions AND! errors
            th.printStackTrace();
        }
    }

    private void checkSize() {

        Log.d(TAG, "cache size="+size+" length="+cache.size());

        if(size > limit){

            // least recently accessed item will be the first one iterated
            // thanks to creating map with flag for LRU ordering
            Iterator<Map.Entry<String, Bitmap>> itr = cache.entrySet().iterator();

            while(itr.hasNext()){
                Map.Entry<String, Bitmap> entry=itr.next();
                size-=getSizeInBytes(entry.getValue());
                itr.remove();
                if(size<=limit)
                    break;
            }
            Log.d(TAG, "Cleaned cache. New size "+cache.size());
        }
    }

    public void clear() {

        try{
            //NullPointerException sometimes happen here http://code.google.com/p/osmdroid/issues/detail?id=78
            cache.clear();
            size=0;
        }catch(NullPointerException ex){
            ex.printStackTrace();
        }
    }

    long getSizeInBytes(Bitmap bitmap) {
        if(bitmap==null)
            return 0;
        return bitmap.getRowBytes() * bitmap.getHeight();
    }
}