package de.dynamicflash.adaptor;

import static de.dynamicflash.helper.DynamicFlashAppGlideModule.EXTRA_IMAGE_URL_PARAMS;
import static de.dynamicflash.helper.DynamicFlashAppGlideModule.IMAGE_BASE_URL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.List;

import de.dynamicflash.databinding.FullscreenItemBinding;
import de.dynamicflash.model.Photo;

public class PhotoFullscreenSwipeAdapter extends PagerAdapter {

    private final Context context;
    private final List<Photo> photos;
    private final String folder;

    // constructor
    public PhotoFullscreenSwipeAdapter(Context activity,
                                       List<Photo> photos,
                                       String folder) {
        this.context = activity;
        this.photos = photos;
        this.folder = folder;
    }

    @Override
    public int getCount() {
        return this.photos.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        FullscreenItemBinding binding = FullscreenItemBinding.inflate(inflater, container, false);


        Photo photo = photos.get(position);

        if (photo.getComment().isEmpty()) {
            binding.labelID.setVisibility(View.GONE);
        } else {
            binding.labelID.setText(photo.getComment());
        }

        final String uri = String.format("%s/%s/%s?w=1920&h=1280&fit=inside%s", IMAGE_BASE_URL, folder, photo.getFilename(), EXTRA_IMAGE_URL_PARAMS);

        Glide.with(context)
                .load(uri)
                .into(binding.imageID);

        container.addView(binding.getRoot());

        return binding.getRoot();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}