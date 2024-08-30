package de.dynamicflash.adaptor;

import static de.dynamicflash.GalleryApplication.EXTRA_IMAGE_URL_PARAMS;
import static de.dynamicflash.GalleryApplication.IMAGE_BASE_URL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import coil.Coil;
import coil.request.ImageRequest;
import de.dynamicflash.databinding.ImageWithDescriptionBinding;
import de.dynamicflash.model.Page;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 11/16/13
 * Time: 4:00 PM
 */
public class ProjectSwipeAdapter extends RecyclerView.Adapter<ProjectSwipeAdapter.ViewHolder> {
    private final List<Page> pages = new ArrayList<>();

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
        holder.bind(project);
    }

    @Override
    public int getItemCount() {
        return pages.size();
    }

    public void setPages(List<Page> pages) {
        this.pages.addAll(pages);
        notifyItemRangeChanged(0, pages.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageWithDescriptionBinding binding;

        public ViewHolder(ImageWithDescriptionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Page project) {

            if (project.getTitle().isEmpty()) {
                binding.label.setVisibility(View.GONE);
            } else {
                binding.label.setText(project.getTitle());
            }

            if (project.getDescription().isEmpty()) {
                binding.description.setVisibility(View.GONE);
            } else {
                binding.description.setText(project.getDescription());
            }

            final String uri = String.format("%s/%s?w=1920&h=1280&fit=inside%s", IMAGE_BASE_URL, project.getImage(), EXTRA_IMAGE_URL_PARAMS);

            Context context = itemView.getContext();
            ImageRequest request = new ImageRequest.Builder(context)
                    .data(uri)
                    .target(binding.image)
                    .build();

            Coil.imageLoader(context).enqueue(request);
        }
    }
}

