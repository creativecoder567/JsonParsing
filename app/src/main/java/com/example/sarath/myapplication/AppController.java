package com.example.sarath.myapplication;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.sarath.customlistviewvolley.util.LruBitmapcache;

/**
 * Created by sarath on 18/12/16.
 */

public class AppController extends Application {
    public static final String TAG =AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
    }
    public static synchronized AppController getInstance(){
        return  mInstance;
    }
    public RequestQueue getRequestQueue(){
     if(mRequestQueue==null){
         mRequestQueue = Volley.newRequestQueue(getApplicationContext());
     }
        return mRequestQueue;
    }
    public ImageLoader getImageLoader(){
        getRequestQueue();
        if (mImageLoader==null){
            mImageLoader= new ImageLoader(this.mRequestQueue,new LruBitmapcache());
        }
        return this.mImageLoader;
    }
   public <T> void addToRequestQueue(Request<T> request){
       request.setTag(TAG);
       getRequestQueue().add(request);
   }
   public void cancelPendingRequests(Object tag){
       if(mRequestQueue!=null){
           mRequestQueue.cancelAll(tag);
       }
   }
}
