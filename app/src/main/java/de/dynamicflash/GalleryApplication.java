package de.dynamicflash;

import android.app.Application;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.List;

import coil.ImageLoader;
import coil.ImageLoaderFactory;
import coil.disk.DiskCache;
import coil.memory.MemoryCache;
import de.dynamicflash.helper.OkHttpClientImpl;
import de.dynamicflash.model.Photo;

public class GalleryApplication extends Application implements ImageLoaderFactory {

    public static final String EXTRA_IMAGE_URL_PARAMS = "&fm=avif";

    public static final String IMAGE_BASE_URL = "https://images.dynamicflash.de";

    /**
     * to avoid loading the same data for the swipe view (is loaded with thumbs for the overview view)
     */
    public static final String TAG = GalleryApplication.class.getName();
    private List<Photo> currentPhotos;

    public List<Photo> getCurrentPhotos() {
        return currentPhotos;
    }

    public void setCurrentPhotos(List<Photo> currentPhotos) {
        this.currentPhotos = currentPhotos;
    }

    @NonNull
    @Override
    public ImageLoader newImageLoader() {
        int cacheSize = 30 * 1024 * 1024; // 10 MiB

        MemoryCache memoryCache = new
                MemoryCache.Builder(this).build();
        DiskCache diskCache = new DiskCache.Builder().maxSizeBytes(cacheSize).directory(new File(getCacheDir(), "image_cache")).build();
        return new ImageLoader.Builder(this)
                .okHttpClient(OkHttpClientImpl.instance())
                .crossfade(true)
                .memoryCache(memoryCache)
                .diskCache(diskCache)
                .build();
    }
}

