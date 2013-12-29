package de.dynamicflash.adaptor;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 09/11/13
 * Time: 18:28
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import de.dynamicflash.GalleryApplication;
import de.dynamicflash.R;
import de.dynamicflash.helper.AppConstant;
import de.dynamicflash.model.Photo;

public class PhotoListAdapter extends ArrayAdapter<Photo> {

    private Context context;
    private int resource;

    public PhotoListAdapter(Context context, int resource) {

        super(context, resource);
        this.resource = resource;
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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
            ImageLoader imageLoader = ImageLoader.getInstance();
            final String uri =  AppConstant.BASE_URL + String.format(AppConstant.THUMB_IMAGE, item.getFull_path());
            imageLoader.displayImage(uri, holder.image,new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    holder.progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    holder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    holder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });
            Log.i(GalleryApplication.TAG, "getThumb: " + uri);
        }

        return row;
    }

    static class ViewHolder {
        ImageView image;
        ProgressBar progressBar;
    }
}