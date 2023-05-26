package de.dynamicflash.adaptor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import de.dynamicflash.R;
import de.dynamicflash.model.Photo;

public class PhotoFullscreenSwipeAdapter extends PagerAdapter {

    private final Activity _activity;
    private final Photo[] photos;

    // constructor
    public PhotoFullscreenSwipeAdapter(Activity activity,
                                       Photo[] photos) {
        this._activity = activity;
        this.photos = photos;
    }

    @Override
    public int getCount() {
        return this.photos.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View viewLayout = inflater.inflate(R.layout.fullscreen_item, container,
                false);


        ImageView imgDisplay = viewLayout.findViewById(R.id.imageID);
        TextView label = viewLayout.findViewById(R.id.labelID);
        final ProgressBar progressBar = viewLayout.findViewById(R.id.progressBarID);

        Photo photo = photos[position];

        if (photo.getTitle().isEmpty()) {
            label.setVisibility(View.GONE);
        } else {
            label.setText(photo.getTitle());
        }

        Picasso.get().load(photo.getFull_path()).into(imgDisplay, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception ex) {
                progressBar.setVisibility(View.GONE);
            }
        });

        container.addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);

    }

}