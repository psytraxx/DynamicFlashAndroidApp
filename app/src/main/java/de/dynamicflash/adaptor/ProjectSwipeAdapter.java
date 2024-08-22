package de.dynamicflash.adaptor;

import static de.dynamicflash.helper.DynamicFlashAppGlideModule.EXTRA_IMAGE_URL_PARAMS;
import static de.dynamicflash.helper.DynamicFlashAppGlideModule.IMAGE_BASE_URL;

import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.dynamicflash.databinding.ProjectItemBinding;
import de.dynamicflash.model.Page;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 11/16/13
 * Time: 4:00 PM
 */
public class ProjectSwipeAdapter extends PagerAdapter {
    private final ArrayList<Page> pages;
    private final FragmentActivity activity;

    public ProjectSwipeAdapter(FragmentActivity context, ArrayList<Page> pages) {
        this.pages = pages;
        this.activity = context;
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        ProjectItemBinding binding = ProjectItemBinding.inflate(activity.getLayoutInflater(), container, false);

        Page project = pages.get(position);

        if (project.getTitle().isEmpty()) {
            binding.labelID.setVisibility(View.GONE);
        } else {
            binding.labelID.setText(project.getTitle());
        }

        binding.descriptionID.setText(Html.fromHtml(project.getDescription(), Html.FROM_HTML_MODE_COMPACT));

        final String uri = IMAGE_BASE_URL + project.getImage() + "?w=1920&h=1280&fit=inside" + EXTRA_IMAGE_URL_PARAMS;

        Glide.with(activity)
                .load(uri)
                .into(binding.imageID);

        container.addView(binding.getRoot());

        return binding.getRoot();
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
