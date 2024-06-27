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
    private final int resource;

    public PhotoListAdapter(Context context) {

        super(context, R.layout.photo_grid_item);
        this.resource = R.layout.photo_grid_item;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder; // No need to initialize here

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext()); // More efficient way to get LayoutInflater
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolder(convertView); // Initialize ViewHolder directly
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Check position validity before accessing item
        if (position >= 0 && position < getCount()) {
            Photo item = getItem(position);
            if (item != null) { // Null check for item
                String uri = IMAGE_BASE_URL + item.getImage() + "?w=320&h=240&dpr=2&fit=cover" + EXTRA_IMAGE_URL_PARAMS;

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