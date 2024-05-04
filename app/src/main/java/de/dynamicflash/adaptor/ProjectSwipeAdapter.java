package de.dynamicflash.adaptor;

import static de.dynamicflash.helper.DynamicFlashAppGlideModule.EXTRA_IMAGE_URL_PARAMS;

import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import de.dynamicflash.R;
import de.dynamicflash.helper.Api;
import de.dynamicflash.model.Page;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 11/16/13
 * Time: 4:00 PM
 */
public class ProjectSwipeAdapter extends PagerAdapter {
    private final Page[] pages;
    private final FragmentActivity activity;

    public ProjectSwipeAdapter(FragmentActivity context, Page[] pages) {
        this.pages = pages;
        this.activity = context;
    }

    @Override
    public int getCount() {
        return pages.length;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View viewLayout = activity.getLayoutInflater().inflate(R.layout.project_item, container,
                false);

        ImageView imgDisplay = viewLayout.findViewById(R.id.imageID);
        TextView label = viewLayout.findViewById(R.id.labelID);
        TextView description = viewLayout.findViewById(R.id.descriptionID);

        Page project = pages[position];

        if (project.getTitle().isEmpty()) {
            label.setVisibility(View.GONE);
        } else {
            label.setText(project.getTitle());
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            description.setText(Html.fromHtml(project.getDescription(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            description.setText(Html.fromHtml(project.getDescription()));
        }

        final String uri = Api.BASE_URL + '/' + project.getImage();

        Glide.with(activity)
                .load(uri+EXTRA_IMAGE_URL_PARAMS)
                .into(imgDisplay);

        container.addView(viewLayout);

        return viewLayout;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
