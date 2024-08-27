package de.dynamicflash.adaptor;

import static de.dynamicflash.helper.DynamicFlashAppGlideModule.EXTRA_IMAGE_URL_PARAMS;
import static de.dynamicflash.helper.DynamicFlashAppGlideModule.IMAGE_BASE_URL;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.dynamicflash.databinding.ImageWithDescriptionBinding;
import de.dynamicflash.model.Page;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 11/16/13
 * Time: 4:00 PM
 */
public class ProjectSwipeAdapter extends RecyclerView.Adapter<ProjectSwipeAdapter.ViewHolder> {
    private final List<Page> pages;

    public ProjectSwipeAdapter(List<Page> pages) {
        this.pages = pages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ImageWithDescriptionBinding binding = ImageWithDescriptionBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Page project = pages.get(position);

        if (project.getTitle().isEmpty()) {
            holder.binding.label.setVisibility(View.GONE);
        } else {
            holder.binding.label.setText(project.getTitle());
        }

        if (project.getDescription().isEmpty()) {
            holder.binding.description.setVisibility(View.GONE);
        } else {
            holder.binding.description.setText(project.getDescription());
        }

        final String uri = String.format("%s/%s?w=1920&h=1280&fit=inside%s", IMAGE_BASE_URL, project.getImage(), EXTRA_IMAGE_URL_PARAMS);

        Glide.with(holder.itemView.getContext())
                .load(uri)
                .into(holder.binding.image);
    }

    @Override
    public int getItemCount() {
        return pages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageWithDescriptionBinding binding;

        public ViewHolder(ImageWithDescriptionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

