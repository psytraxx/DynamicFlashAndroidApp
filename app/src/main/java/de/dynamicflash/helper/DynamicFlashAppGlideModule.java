package de.dynamicflash.helper;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.AppGlideModule;

@GlideModule
public class DynamicFlashAppGlideModule extends AppGlideModule {

    public static final String EXTRA_IMAGE_URL_PARAMS = "&fm=avif";

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30);
        circularProgressDrawable.start();

        int cacheSize = 30 * 1024 * 1024; // 10 MiB

        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, cacheSize));
        super.applyOptions(context, builder);
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
