package de.dynamicflash;

import android.app.Application;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.dynamicflash.helper.LoggingInterceptor;
import de.dynamicflash.model.Photo;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;

public class GalleryApplication extends Application {

    /**
     * to avoid loading the same data for the swipe view (is loaded with thumbs for the overview view)
     */
    public static final String TAG = GalleryApplication.class.getName();

    public Photo[] getCurrentPhotos() {
        return currentPhotos;
    }

    public void setCurrentPhotos(Photo[] currentPhotos) {
        this.currentPhotos = currentPhotos;
    }

    private Photo[] currentPhotos;

    @Override
    public void onCreate() {
        super.onCreate();

        int cacheSize = 30 * 1024 * 1024; // 10 MiB
        Cache responseCache = new Cache(getCacheDir(), cacheSize);

        List<Protocol> protocols = new ArrayList<>();
        protocols.add(Protocol.HTTP_2);
        protocols.add(Protocol.HTTP_1_1);

        OkHttpClient client = new OkHttpClient.Builder()
                .protocols(protocols)
                .cache(responseCache)
                .addInterceptor(new LoggingInterceptor())
                .build();


        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(client));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(false);
        built.setLoggingEnabled(false);
        Picasso.setSingletonInstance(built);
    }
}

