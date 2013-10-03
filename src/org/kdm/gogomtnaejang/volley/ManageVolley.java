package org.kdm.gogomtnaejang.volley;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class ManageVolley {
	private static ManageVolley mVolleyManager;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;


    public static ManageVolley getInst(Context context) {
        if(mVolleyManager == null){
        	Log.e("생성","생성");
        	mVolleyManager = new ManageVolley(context);
        }
        
        return mVolleyManager;
    }
    
    private ManageVolley(Context context){
        mRequestQueue = Volley.newRequestQueue(context);

        int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE))
                .getMemoryClass();
        // Use 1/8th of the available memory for this memory cache.
        int cacheSize = 1024 * 1024 * memClass / 8;
        mImageLoader = new ImageLoader(mRequestQueue, new BitmapLruCache(cacheSize));
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            throw new IllegalStateException("RequestQueue not initialized");
        }
    }

    public ImageLoader getImageLoader() {
        if (mImageLoader != null) {
            return mImageLoader;
        } else {
            throw new IllegalStateException("ImageLoader not initialized");
        }
    }
}