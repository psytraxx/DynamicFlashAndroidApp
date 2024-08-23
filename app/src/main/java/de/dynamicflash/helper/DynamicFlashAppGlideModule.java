package de.dynamicflash.helper;

import static de.dynamicflash.helper.OkHttpClientImpl.instance;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

import java.io.InputStream;

@GlideModule
public class DynamicFlashAppGlideModule extends AppGlideModule {

    public static final String EXTRA_IMAGE_URL_PARAMS = "&fm=avif";

    public static final String IMAGE_BASE_URL = "https://images.dynamicflash.de/";

    private static final int GLIDE_REQUEST_TIMEOUT_MS = 30000;

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30);
        circularProgressDrawable.start();

        int cacheSize = 30 * 1024 * 1024; // 10 MiB

        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, cacheSize));
        builder.setDefaultRequestOptions(new RequestOptions().timeout(GLIDE_REQUEST_TIMEOUT_MS).placeholder(circularProgressDrawable));
        super.applyOptions(context, builder);
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, Registry registry) {
        OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(instance());
        registry.replace(GlideUrl.class, InputStream.class, factory);
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
