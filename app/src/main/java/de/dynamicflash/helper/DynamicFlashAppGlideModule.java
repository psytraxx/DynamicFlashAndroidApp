package de.dynamicflash.helper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

@GlideModule
public class DynamicFlashAppGlideModule extends AppGlideModule {

    public static final String EXTRA_IMAGE_URL_PARAMS = "&fm=avif";
    private static final int GLIDE_REQUEST_TIMOUT_MS = 30000;

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30);
        circularProgressDrawable.start();

        int cacheSize = 30 * 1024 * 1024; // 10 MiB

        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, cacheSize));
        builder.setDefaultRequestOptions(new RequestOptions().timeout(GLIDE_REQUEST_TIMOUT_MS).placeholder(circularProgressDrawable));
        super.applyOptions(context, builder);
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
