package com.sarath.customlistviewvolley.util;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by sarath on 18/12/16.
 */

public class LruBitmapcache extends LruCache<String,Bitmap> implements ImageLoader.ImageCache {

    public static int getDefaultLruCacheSize(){
        final  int maxMemory =(int)(Runtime.getRuntime().maxMemory()/1024);
        final int cacheSize= maxMemory/8;
        return cacheSize;
    }
    public LruBitmapcache(){
        this(getDefaultLruCacheSize());
    }
    public LruBitmapcache(int sizeInKiloBytes) {
        super(sizeInKiloBytes);
    }
    protected int sizeOf(String key,Bitmap value){
        return value.getRowBytes()*value.getHeight()/1024;
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
     put(url,bitmap);
    }
}
