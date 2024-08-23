package de.dynamicflash.adaptor;

import static de.dynamicflash.helper.DynamicFlashAppGlideModule.EXTRA_IMAGE_URL_PARAMS;
import static de.dynamicflash.helper.DynamicFlashAppGlideModule.IMAGE_BASE_URL;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
public class ProjectSwipeAdapter extends RecyclerView.Adapter<ProjectSwipeAdapter.ProjectViewHolder> {
    private final ArrayList<Page> pages;
    private final Context context;

    public ProjectSwipeAdapter(Context context, ArrayList<Page> pages) {
        this.pages = pages;
        this.context = context;
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ProjectItemBinding binding = ProjectItemBinding.inflate(inflater, parent, false);
        return new ProjectViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        Page project = pages.get(position);

        if (project.getTitle().isEmpty()) {
            holder.binding.labelID.setVisibility(View.GONE);
        } else {
            holder.binding.labelID.setText(project.getTitle());
        }

        holder.binding.descriptionID.setText(Html.fromHtml(project.getDescription(), Html.FROM_HTML_MODE_COMPACT));

        final String uri = IMAGE_BASE_URL + project.getImage() + "?w=1920&h=1280&fit=inside" + EXTRA_IMAGE_URL_PARAMS;

        Glide.with(context)
                .load(uri)
                .into(holder.binding.image);
    }

    @Override
    public int getItemCount() {
        return pages.size();
    }

    public static class ProjectViewHolder extends RecyclerView.ViewHolder {
        final ProjectItemBinding binding;

        public ProjectViewHolder(ProjectItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

