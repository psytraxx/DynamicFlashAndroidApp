package de.dynamicflash;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 09/11/13
 * Time: 23:58
 */

import android.app.Application;
import android.content.Context;
import android.net.http.HttpResponseCache;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.dynamicflash.model.Photo;

public class GalleryApplication extends Application {

    /**
     * to avoid loading the same data for the swipe view (is loaded with thumbs for the overview view)
     */
    public static final String TAG = GalleryApplication.class.getName();

    public List<Photo> getCurrentPhotos() {
        return currentPhotos;
    }

    public void setCurrentPhotos(List<Photo> currentPhotos) {
        this.currentPhotos = currentPhotos;
    }

    private List<Photo> currentPhotos;

    @Override
    public void onCreate() {
        super.onCreate();

        Context context = getApplicationContext();
        File cacheDir = StorageUtils.getCacheDirectory(context);

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();

        // Create global configuration and initialize ImageLoader with this configuration
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCacheExtraOptions(480, 800)
                .threadPoolSize(4) // default
                .discCache(new UnlimitedDiscCache(cacheDir))
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

}
