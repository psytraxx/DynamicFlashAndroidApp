package de.dynamicflash.adaptor;

import static de.dynamicflash.helper.DynamicFlashAppGlideModule.EXTRA_IMAGE_URL_PARAMS;
import static de.dynamicflash.helper.DynamicFlashAppGlideModule.IMAGE_BASE_URL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import de.dynamicflash.R;
import de.dynamicflash.model.Photo;

public class PhotoListAdapter extends ArrayAdapter<Photo> {

    private final Context context;
    private final String folder;

    public PhotoListAdapter(Context context, String folder) {

        super(context, R.layout.photo_grid_item);
        this.folder = folder;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_grid_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Check position validity before accessing item
        if (position >= 0 && position < getCount()) {
            Photo item = getItem(position);
            if (item != null) { // Null check for item
                final String uri = String.format("%s/%s/%s?w=1920&h=1280&fit=inside%s", IMAGE_BASE_URL, folder, item.getFilename(), EXTRA_IMAGE_URL_PARAMS);

                Glide.with(context)
                        .load(uri)
                        .into(holder.image);
            }
        }

        return convertView;
    }

    // ViewHolder class with constructor for view initialization
    static class ViewHolder {
        ImageView image;

        ViewHolder(View view) {
            image = view.findViewById(R.id.imageID);
        }
    }
}