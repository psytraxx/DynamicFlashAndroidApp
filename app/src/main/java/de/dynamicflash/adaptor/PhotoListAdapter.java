package de.dynamicflash.adaptor;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 09/11/13
 * Time: 18:28
 */

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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


    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        final ViewHolder holder;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) row.findViewById(R.id.imageID);
            holder.progressBar = (ProgressBar) row.findViewById(R.id.progressBarID);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        if (getCount() > position) {
            Photo item = getItem(position);
            assert item != null;

            Picasso.with(parent.getContext()).load(item.getThumb_path()).into(holder.image, new Callback() {
                @Override
                public void onSuccess() {
                    holder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                    holder.progressBar.setVisibility(View.GONE);
                }
            });
        }

        return row;
    }

    static class ViewHolder {
        ImageView image;
        ProgressBar progressBar;
    }
}