package de.dynamicflash.adaptor;

import static de.dynamicflash.helper.DynamicFlashAppGlideModule.EXTRA_IMAGE_URL_PARAMS;
import static de.dynamicflash.helper.DynamicFlashAppGlideModule.IMAGE_BASE_URL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import de.dynamicflash.R;
import de.dynamicflash.databinding.ImageThumbnailBinding;
import de.dynamicflash.model.Photo;

public class PhotoGridAdapter extends ArrayAdapter<Photo> {

    private final Context context;
    private final String folder;

    public PhotoGridAdapter(Context context, String folder) {

        super(context, R.layout.image_thumbnail);
        this.folder = folder;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ImageThumbnailBinding binding;

        if (convertView == null) {
            binding = ImageThumbnailBinding.inflate(LayoutInflater.from(context), parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (ImageThumbnailBinding) convertView.getTag();
        }

        // Check position validity before accessing item
        if (position >= 0 && position < getCount()) {
            Photo item = getItem(position);
            if (item != null) { // Null check for item
                final String uri = String.format("%s/%s/%s?w=1920&h=1280&fit=inside%s", IMAGE_BASE_URL, folder, item.getFilename(), EXTRA_IMAGE_URL_PARAMS);

                Glide.with(context)
                        .load(uri)
                        .into(binding.image);
            }
        }

        return convertView;
    }

}