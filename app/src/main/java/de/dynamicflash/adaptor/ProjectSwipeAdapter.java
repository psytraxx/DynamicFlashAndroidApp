package de.dynamicflash.adaptor;

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
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

import de.dynamicflash.GalleryApplication;
import de.dynamicflash.R;
import de.dynamicflash.helper.AppConstant;
import de.dynamicflash.model.Page;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 11/16/13
 * Time: 4:00 PM
 */
public class ProjectSwipeAdapter extends PagerAdapter {
    private List<Page> pages;
    private LayoutInflater inflater;

    public ProjectSwipeAdapter(LayoutInflater inflater, List<Page> pages) {
        this.pages = pages;
       this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View viewLayout = inflater.inflate(R.layout.project_item, container,
                false);


        ImageView imgDisplay = (ImageView) viewLayout.findViewById(R.id.imageID);
        TextView label = (TextView) viewLayout.findViewById(R.id.labelID);
        TextView description = (TextView) viewLayout.findViewById(R.id.descriptionID);
        final ProgressBar progressBar = (ProgressBar) viewLayout.findViewById(R.id.progressBarID);

        Page project = pages.get(position);

        if (project.getTitle().isEmpty()) {
            label.setVisibility(View.GONE);
        } else {
            label.setText(project.getTitle());
        }

        description.setText(project.getBody());

        final String uri = AppConstant.BASE_URL + '/' + project.getImage();

        Log.i(GalleryApplication.TAG, "getImage: " + uri);

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(uri, imgDisplay, new ImageLoadingListener() {

            @Override
            public void onLoadingStarted(String imageUri, View view) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });

        container.addView(viewLayout);

        return viewLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
