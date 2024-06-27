package de.dynamicflash.adaptor;

import static de.dynamicflash.helper.DynamicFlashAppGlideModule.EXTRA_IMAGE_URL_PARAMS;
import static de.dynamicflash.helper.DynamicFlashAppGlideModule.IMAGE_BASE_URL;

import android.app.Activity;
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

        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.image = convertView.findViewById(R.id.imageID);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (getCount() > position) {
            Photo item = getItem(position);
            assert item != null;

            final String uri = IMAGE_BASE_URL + item.getImage() + "?w=320&h=240&dpr=2&fit=cover" + EXTRA_IMAGE_URL_PARAMS;

            Glide.with(context)
                    .load(uri)
                    .into(holder.image);
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView image;
    }
}