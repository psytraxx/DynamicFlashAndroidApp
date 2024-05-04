package de.dynamicflash.adaptor;

import static de.dynamicflash.helper.DynamicFlashAppGlideModule.EXTRA_IMAGE_URL_PARAMS;

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
        View row = convertView;
        final ViewHolder holder;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.image = row.findViewById(R.id.imageID);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        if (getCount() > position) {
            Photo item = getItem(position);
            assert item != null;

            Glide.with(context)
                    .load(item.getThumb_path()+EXTRA_IMAGE_URL_PARAMS)
                    .into(holder.image);
        }

        return row;
    }

    static class ViewHolder {
        ImageView image;
    }
}