package de.dynamicflash.adaptor;

import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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
    private final Page[] pages;
    private final LayoutInflater inflater;

    public ProjectSwipeAdapter(LayoutInflater inflater, Page[] pages) {
        this.pages = pages;
       this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return pages.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View viewLayout = inflater.inflate(R.layout.project_item, container,
                false);


        ImageView imgDisplay = viewLayout.findViewById(R.id.imageID);
        TextView label = viewLayout.findViewById(R.id.labelID);
        TextView description = viewLayout.findViewById(R.id.descriptionID);
        final ProgressBar progressBar = viewLayout.findViewById(R.id.progressBarID);

        Page project = pages[position];

        if (project.getTitle().isEmpty()) {
            label.setVisibility(View.GONE);
        } else {
            label.setText(project.getTitle());
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            description.setText(Html.fromHtml(project.getBody(),Html.FROM_HTML_MODE_COMPACT));
        } else {
            description.setText(Html.fromHtml(project.getBody()));
        }

        final String uri = AppConstant.BASE_URL + '/' + project.getImage();

        Picasso.with(container.getContext()).load(uri).into(imgDisplay, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                progressBar.setVisibility(View.GONE);
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
