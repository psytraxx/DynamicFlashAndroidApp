package de.dynamicflash.adaptor;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 09/11/13
 * Time: 18:43
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import java.util.List;

import de.dynamicflash.GalleryApplication;
import de.dynamicflash.R;
import de.dynamicflash.helper.AppConstant;
import de.dynamicflash.model.Photo;

public class PhotoFullscreenSwipeAdapter extends PagerAdapter {

    private Activity _activity;
    private List<Photo> photos;

    // constructor
    public PhotoFullscreenSwipeAdapter(Activity activity,
                                       List<Photo> photos) {
        this._activity = activity;
        this.photos = photos;
    }

    @Override
    public int getCount() {
        return this.photos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.fullscreen_item, container,
                false);


        ImageView imgDisplay = (ImageView) viewLayout.findViewById(R.id.imageID);
        TextView label = (TextView) viewLayout.findViewById(R.id.labelID);
        final ProgressBar progressBar = (ProgressBar) viewLayout.findViewById(R.id.progressBarID);

        Photo photo = photos.get(position);

        if (photo.getTitle().isEmpty()) {
            label.setVisibility(View.GONE);
        } else {
            label.setText(photo.getTitle());
        }
        final String uri = AppConstant.BASE_URL + String.format(AppConstant.FULL_IMAGE, photo.getFull_path());

        Log.i(GalleryApplication.TAG, "getThumb: " + uri);

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(uri, imgDisplay, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
                progressBar.setVisibility(View.GONE);
            }
        });


        container.addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);

    }

}