package de.dynamicflash;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 09/11/13
 * Time: 23:58
 */

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.net.http.HttpResponseCache;
import android.os.Build;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.io.IOException;

import de.dynamicflash.model.Photo;

public class GalleryApplication extends Application {

    /**
     * to avoid loading the same data for the swipe view (is loaded with thumbs for the overview view)
     */
    public static final String TAG = GalleryApplication.class.getName();

    public RequestQueue getReqQueue() {
        return reqQueue;
    }

    public Photo[] getCurrentPhotos() {
        return currentPhotos;
    }

    public void setCurrentPhotos(Photo[] currentPhotos) {
        this.currentPhotos = currentPhotos;
    }

    private Photo[] currentPhotos;

    private RequestQueue reqQueue;

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onCreate() {
        super.onCreate();

        //create request queue
        reqQueue = Volley.newRequestQueue(this);
        Context context = getApplicationContext();
        File cacheDir = StorageUtils.getCacheDirectory(context);

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        // Create global configuration and initialize ImageLoader with this configuration
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCacheExtraOptions(480, 800)
                .threadPoolSize(4) // default
                .diskCache(new UnlimitedDiskCache(cacheDir ))
                .build();

        ImageLoader.getInstance().init(config);

         //init the http cache
        try {
            File httpCacheDir = new File(context.getCacheDir(), "http");
            long httpCacheSize = 10 * 1024 * 1024; // 10 MiB
                HttpResponseCache.install(httpCacheDir, httpCacheSize);
        } catch (IOException e) {
                Log.i(TAG, "HTTP response cache installation failed:" + e);

        }


    }


    public static Response.ErrorListener createErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error Response code: " + error.getMessage());
            }
        };
    }

}
