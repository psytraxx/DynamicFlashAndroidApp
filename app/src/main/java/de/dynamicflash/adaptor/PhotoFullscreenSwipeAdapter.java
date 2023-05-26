package de.dynamicflash.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import de.dynamicflash.R;
import de.dynamicflash.model.Photo;

public class PhotoFullscreenSwipeAdapter extends PagerAdapter {

    private final Context context;
    private final Photo[] photos;

    // constructor
    public PhotoFullscreenSwipeAdapter(Context activity,
                                       Photo[] photos) {
        this.context = activity;
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

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        assert inflater != null;
        View viewLayout = inflater.inflate(R.layout.fullscreen_item, container,
                false);


        ImageView imgDisplay = viewLayout.findViewById(R.id.imageID);
        TextView label = viewLayout.findViewById(R.id.labelID);

        Photo photo = photos[position];

        if (photo.getTitle().isEmpty()) {
            label.setVisibility(View.GONE);
        } else {
            label.setText(photo.getTitle());
        }

        Glide.with(context)
                .load(photo.getFull_path())
                .into(imgDisplay);

        container.addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}